/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.templateproject.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.fragment.LoginFragment;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.templateproject.utils.map.AroundPoiAdapter;
import com.xuexiang.templateproject.utils.map.BaiduMapUtilByRacer;
import com.xuexiang.templateproject.utils.map.LocationBean;
import com.xuexiang.templateproject.utils.map.SearchPoiAdapter;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.textview.autofit.AutoFitTextView;

import java.util.ArrayList;
import java.util.List;


/*
包含地图定位获取的activity
* */
public class PermissionsActivity extends BaseActivity {
    private static Context mContext;
    private LocationBean mLocationBean;
    // 定位poi地名信息数据源
    private List<PoiInfo> aroundPoiList;
    private AroundPoiAdapter mAroundPoiAdapter;
    // 搜索模块，也可去掉地图模块独立使用
    private Marker mMarker = null;
    // 搜索当前城市poi数据源
    private static List<LocationBean> searchPoiList;
    private SearchPoiAdapter mSearchPoiAdapter;
    private BaiduMap mBaiduMap;
    // 控件
    private MapView mMapView;
    private TextView etMLCityPoi;
    private View etMLCityPoiView;
    private EditText etMLCityPoiText;
    private TextView tvShowLocation;
    private LinearLayout llMLMain;
    private ListView lvAroundPoi, lvSearchPoi;
    private ImageView ivMLPLoading;
//    private Button btMapZoomIn, btMapZoomOut;
    private ImageButton ibMLLocate;

    private AutoFitTextView close;
    private AutoFitTextView ok;
    // 標識
    public static final int SHOW_MAP = 0;
    private static final int SHOW_SEARCH_RESULT = 1;
    // 延时多少秒diss掉dialog
    private static final int DELAY_DISMISS = 1000 * 30;

    /*
    *
    * 返回信息*/
    private  String name;
    private  double lon;
    private  double lat;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        //设置沉浸式状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtils.translucent(this);
        StatusBarUtils.setStatusBarLightMode(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        this.mContext = this;
        ibMLLocate = (ImageButton) findViewById(R.id.ibMLLocate);
        etMLCityPoi = (TextView) findViewById(R.id.etMLCityPoi);
        tvShowLocation = (TextView) findViewById(R.id.tvShowLocation);
        lvAroundPoi = (ListView) findViewById(R.id.lvPoiList);
        etMLCityPoiView =findViewById(R.id.etMLCityPoiView);
        close =findViewById(R.id.close);
        ok =findViewById(R.id.ok);
//        lvSearchPoi = (ListView) findViewById(R.id.lvMLCityPoi);
        ivMLPLoading = (ImageView) findViewById(R.id.ivMLPLoading);
//        btMapZoomIn = (Button) findViewById(R.id.btMapZoomIn);
//        btMapZoomOut = (Button) findViewById(R.id.btMapZoomOut);
        llMLMain = (LinearLayout) findViewById(R.id.llMLMain);
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.mMapView);
        BaiduMapUtilByRacer.goneMapViewChild(mMapView, true, true);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
        mBaiduMap.setOnMapStatusChangeListener(mapStatusChangeListener);
        mBaiduMap.setOnMapClickListener(mapOnClickListener);
        //关闭缩放按钮
        mMapView.showZoomControls(false);
        mBaiduMap.getUiSettings().setZoomGesturesEnabled(false);// 缩放手势
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        locate();
        iniEvent();

        /*
        * 获取跳转过来的请求码
        * 监听完成的点击
        * */



    }

    // 显示地图界面亦或搜索结果界面
    private void showMapOrSearch(int index) {
        if (index == SHOW_SEARCH_RESULT) {
            llMLMain.setVisibility(View.GONE);
            lvSearchPoi.setVisibility(View.VISIBLE);
        } else {
            lvSearchPoi.setVisibility(View.GONE);
            llMLMain.setVisibility(View.VISIBLE);
            if (searchPoiList != null) {
                searchPoiList.clear();
            }
        }
    }

    public void locate() {
        BaiduMapUtilByRacer.locateByBaiduMap(mContext, 2000,
                new BaiduMapUtilByRacer.LocateListener() {

                    @Override
                    public void onLocateSucceed(LocationBean locationBean) {
                        mLocationBean = locationBean;
                        if (mMarker != null) {
                            mMarker.remove();
                        } else {
                            mBaiduMap.clear();
                        }
                        mMarker = BaiduMapUtilByRacer.showMarkerByResource(
                                locationBean.getLatitude(),
                                locationBean.getLongitude(), R.drawable.point,
                                mBaiduMap, 0, true);
                    }

                    @Override
                    public void onLocateFiled() {

                    }

                    @Override
                    public void onLocating() {

                    }
                });
    }

    public void getPoiByPoiSearch() {
        BaiduMapUtilByRacer.getPoiByPoiSearch(mLocationBean.getCity(),
                etMLCityPoiText.getText().toString().trim(), 0,
                new BaiduMapUtilByRacer.PoiSearchListener() {

                    @Override
                    public void onGetSucceed(List<LocationBean> locationList,
                                             PoiResult res) {
                        if (etMLCityPoiText.getText().toString().trim().length() > 0) {
                            if (searchPoiList == null) {
                                searchPoiList = new ArrayList<LocationBean>();
                            }
                            searchPoiList.clear();
                            searchPoiList.addAll(locationList);
                            updateCityPoiListAdapter();
                        }
                    }

                    @Override
                    public void onGetFailed() {
                        Toast.makeText(mContext, "抱歉，未能找到结果",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void reverseGeoCode(LatLng ll, final boolean isShowTextView) {
        BaiduMapUtilByRacer.getPoisByGeoCode(ll.latitude, ll.longitude,
                new BaiduMapUtilByRacer.GeoCodePoiListener() {

                    @Override
                    public void onGetSucceed(LocationBean locationBean,
                                             List<PoiInfo> poiList) {
                        mLocationBean = (LocationBean) locationBean.clone();
                        Toast.makeText(
                                mContext,
                                mLocationBean.getProvince() + "-"
                                        + mLocationBean.getCity() + "-"
                                        + mLocationBean.getDistrict() + "-"
                                        + mLocationBean.getStreet() + "-"
                                        + mLocationBean.getStreetNum(),
                                Toast.LENGTH_SHORT).show();
                        name=mLocationBean.getProvince() + "-"
                                + mLocationBean.getCity() + "-"
                                + mLocationBean.getDistrict() + "-"
                                + mLocationBean.getStreet() + "-"
                                + mLocationBean.getStreetNum();
                        lon=mLocationBean.getLongitude();
                        lat=mLocationBean.getLatitude();
                        // mBaiduMap.setMapStatus(MapStatusUpdateFactory
                        // .newLatLng(new LatLng(locationBean
                        // .getLatitude(), locationBean
                        // .getLongitude())));
                        if (isShowTextView) {
                            tvShowLocation.setText(locationBean.getLocName());
                        }
                        if (aroundPoiList == null) {
                            aroundPoiList = new ArrayList<PoiInfo>();
                        }
                        aroundPoiList.clear();
                        if (poiList != null) {
                            aroundPoiList.addAll(poiList);
                        } else {
                            Toast.makeText(mContext, "该周边沒有热点",
                                    Toast.LENGTH_SHORT).show();
                        }
                        updatePoiListAdapter(aroundPoiList, -1);
                    }

                    @Override
                    public void onGetFailed() {
                        Toast.makeText(mContext, "抱歉，未能找到结果",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void iniEvent() {
        etMLCityPoiView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showInputTextDialog(PermissionsActivity.this);
//                if (etMLCityPoi.getText().toString().trim().length() > 0) {
////                    getPoiByPoiSearch();
//                    showInputTextDialog(getBaseContext());
//
//                }
            }
        });

        ibMLLocate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                locate();
            }
        });
//        btMapZoomIn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                isCanUpdateMap = false;
//                BaiduMapUtilByRacer.zoomInMapView(mMapView);
//            }
//        });
//        btMapZoomOut.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                isCanUpdateMap = false;
//                BaiduMapUtilByRacer.zoomOutMapView(mMapView);
//            }
//        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭当前activity
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    //存储返回数据   也要用intent
                    Bundle bundle =new Bundle();
                    bundle.putString("name",name);
                    bundle.putDouble("lon",lon);
                    bundle.putDouble("lat",lat);
                    //设置返回数据
                    // 先设置ReaultCode,再设置存储数据的意图
                    setResult(RESULT_OK,new Intent().putExtras(bundle));
                    //关闭当前activity
                    finish();
                }
                //关闭当前activity
                finish();
            }
        });
        lvAroundPoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                isCanUpdateMap = false;
                BaiduMapUtilByRacer.moveToTarget(
                        aroundPoiList.get(arg2).location.latitude,
                        aroundPoiList.get(arg2).location.longitude, mBaiduMap);
                tvShowLocation.setText(aroundPoiList.get(arg2).name);

                name=mLocationBean.getProvince() + "-"
                        + mLocationBean.getCity() + "-"
                        + mLocationBean.getDistrict() + "-"
                        + mLocationBean.getStreet() + "-"
                        + mLocationBean.getStreetNum();
                lon=mLocationBean.getLongitude();
                lat=mLocationBean.getLatitude();
            }
        });





    }
    /*弹出评论页面
     * */
    private void showInputTextDialog(Context context) {
//    Context context = getContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_map, null);
//        赋值
        lvSearchPoi = view.findViewById(R.id.lvMLCityPoi);

        etMLCityPoiText = view.findViewById(R.id.etMLCityPoiText);
        AutoFitTextView imageViewClose = view.findViewById(R.id.close);

        onclickSearch();
        onclickSearchList();
        initDialogCloseImage(imageViewClose, dialog);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    //    点击关闭
    private void initDialogCloseImage(AutoFitTextView inputText, BottomSheetDialog dialog) {
        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (etMLCityPoiText != null) {
                    etMLCityPoiText.setBackgroundResource(0);
                    etMLCityPoiText = null;
                    mSearchPoiAdapter = null;
                }
            }
        });

    }
    public  void onclickSearch(){
        etMLCityPoiText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start, int before,
                                      int count) {
                if (cs.toString().trim().length() > 0) {
                    getPoiByPoiSearch();
                } else {
                    if (searchPoiList != null) {
                        searchPoiList.clear();
                    }
//                    showMapOrSearch(SHOW_MAP);
                    hideSoftinput(mContext);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public  void onclickSearchList(){
        lvSearchPoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // Geo搜索
                // mGeoCoder.geocode(new GeoCodeOption().city(
                // searchPoiList.get(arg2).getCity()).address(
                // searchPoiList.get(arg2).getLocName()));
                hideSoftinput(mContext);
                isCanUpdateMap = false;
                BaiduMapUtilByRacer.moveToTarget(searchPoiList.get(arg2)
                                .getLatitude(), searchPoiList.get(arg2).getLongitude(),
                        mBaiduMap);
                tvShowLocation.setText(searchPoiList.get(arg2).getLocName());
                // 反Geo搜索
                reverseGeoCode(new LatLng(
                        searchPoiList.get(arg2).getLatitude(), searchPoiList
                        .get(arg2).getLongitude()), false);
                if (ivMLPLoading != null && ivMLPLoading.getVisibility() == View.GONE) {
                    loadingHandler.sendEmptyMessageDelayed(1, 0);
                }
                showMapOrSearch(SHOW_MAP);
            }
        });
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (llMLMain.getVisibility() == View.GONE) {
            showMapOrSearch(SHOW_MAP);
        } else {
            this.finish();
        }
    }

    ;

    BaiduMap.OnMapClickListener mapOnClickListener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point
         *            点击的地理坐标
         */
        public void onMapClick(LatLng point) {
            hideSoftinput(mContext);
        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param poi
         *            点击的 poi 信息
         */
        public void onMapPoiClick(MapPoi poi) {
//            name=poi.getName();
//            name=poi.getPosition()+"";
//            XToastUtils.toast("名字" + poi.getName() + "经纬度" + poi.getPosition());
        }
    };
    private boolean isCanUpdateMap = true;
    BaiduMap.OnMapStatusChangeListener mapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         *
         * @param status
         *            地图状态改变开始时的地图状态
         */
        public void onMapStatusChangeStart(MapStatus status) {
        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

        }

        /**
         * 地图状态变化中
         *
         * @param status
         *            当前地图状态
         */
        public void onMapStatusChange(MapStatus status) {
        }

        /**
         * 地图状态改变结束
         *
         * @param status
         *            地图状态改变结束后的地图状态
         */
        public void onMapStatusChangeFinish(MapStatus status) {
            if (isCanUpdateMap) {
                LatLng ptCenter = new LatLng(status.target.latitude,
                        status.target.longitude);
                // 反Geo搜索
                reverseGeoCode(ptCenter, true);
                if (ivMLPLoading != null
                        && ivMLPLoading.getVisibility() == View.GONE) {
                    loadingHandler.sendEmptyMessageDelayed(1, 0);
                }
            } else {
                isCanUpdateMap = true;
            }
        }
    };
    private static Animation hyperspaceJumpAnimation = null;
    Handler loadingHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0: {
                    // if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    // mLoadingDialog.dismiss();
                    // // showToast(mActivity.getString(R.string.map_locate_fault),
                    // // DialogType.LOAD_FAILURE);
                    // }
                    if (ivMLPLoading != null) {
                        ivMLPLoading.clearAnimation();
                        ivMLPLoading.setVisibility(View.GONE);
                    }
                    break;
                }
                case 1: {
                    // 加载动画
                    hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                            mContext, R.anim.dialog_loading_animation);
                    lvAroundPoi.setVisibility(View.GONE);
                    ivMLPLoading.setVisibility(View.VISIBLE);
                    // 使用ImageView显示动画
                    ivMLPLoading.startAnimation(hyperspaceJumpAnimation);
                    if (ivMLPLoading != null
                            && ivMLPLoading.getVisibility() == View.VISIBLE) {
                        loadingHandler.sendEmptyMessageDelayed(0, DELAY_DISMISS);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 隐藏软键盘
     */
    private void hideSoftinput(Context mContext) {
        InputMethodManager manager = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            manager.hideSoftInputFromWindow(etMLCityPoiText.getWindowToken(), 0);
        }
    }

    // 刷新热门地名列表界面的adapter
    private void updatePoiListAdapter(List<PoiInfo> list, int index) {
        ivMLPLoading.clearAnimation();
        ivMLPLoading.setVisibility(View.GONE);
        lvAroundPoi.setVisibility(View.VISIBLE);
        if (mAroundPoiAdapter == null) {
            mAroundPoiAdapter = new AroundPoiAdapter(mContext, list);
            lvAroundPoi.setAdapter(mAroundPoiAdapter);
        } else {
            mAroundPoiAdapter.setNewList(list, index);
        }
    }

    // 刷新当前城市兴趣地点列表界面的adapter
    private void updateCityPoiListAdapter()
    {
        if (mSearchPoiAdapter == null) {
            mSearchPoiAdapter = new SearchPoiAdapter(mContext, searchPoiList);
            lvSearchPoi.setAdapter(mSearchPoiAdapter);
        } else {
            mSearchPoiAdapter.notifyDataSetChanged();
        }
        showMapOrSearch(SHOW_SEARCH_RESULT);
    }

    @Override
    protected void onDestroy() {
        mLocationBean = null;
        lvAroundPoi = null;
        lvSearchPoi = null;
//        btMapZoomIn.setBackgroundResource(0);
//        btMapZoomIn = null;
//        btMapZoomOut.setBackgroundResource(0);
//        btMapZoomOut = null;
        ibMLLocate.setImageBitmap(null);
        ibMLLocate.setImageResource(0);
        ibMLLocate = null;
        if (aroundPoiList != null) {
            aroundPoiList.clear();
            aroundPoiList = null;
        }
        mAroundPoiAdapter = null;
        if (searchPoiList != null) {
            searchPoiList.clear();
            searchPoiList = null;
        }
        mSearchPoiAdapter = null;
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);// 关闭定位图层
            mBaiduMap = null;
        }
        if (mMapView != null) {
            mMapView.destroyDrawingCache();
            mMapView.onDestroy();
            mMapView = null;
        }
        if (etMLCityPoi != null) {
            etMLCityPoi.setBackgroundResource(0);
            etMLCityPoi = null;
        }
        mMarker = null;
        super.onDestroy();
        System.gc();
    }
}
