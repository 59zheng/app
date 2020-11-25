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

package com.xuexiang.templateproject.adapter;

import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.WeatherVo;
import com.xuexiang.templateproject.adapter.entity.Trend;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

import java.util.List;

public class TrendingListAdapter extends BaseRecyclerAdapter<Trend.Land> {

    private static final int TYPE_BANNER_HEAD = 0;
    private static final int TYPE_COMMON = 1;

    //    是否溯源
    private static int TYPE_root_is = 0;


    //      天气情况
    private WeatherVo weatherVos;
    //    轮播情况
    private List<BannerItem> bannerLists;

    private RecyclerView recyclerView;
    private List<Trend.Land> landLists;

    public TrendingListAdapter(WeatherVo weatherVo, List<BannerItem> bannerList, RecyclerView recyclerViews, List<Trend.Land> landList) {
        weatherVos = weatherVo;
        bannerLists = bannerList;
        recyclerView = recyclerViews;
        landLists=landList;
    }


    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_BANNER_HEAD) {
//            轮播
            return R.layout.include_head_trend_banner;
        } else {
//            循环动态
            return R.layout.adapter_trnd_view_list_item;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER_HEAD;
        } else {
            return TYPE_COMMON;
        }
    }

    /*
     * 列表填充
     * */

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, Trend.Land item) {
//        为空不添加添加底色
        if (item == null) {

            return;
        }
//根据状态不同添加不同的页面
        if (getItemViewType(position) == TYPE_BANNER_HEAD) {
//          轮播图片点击
            fillTilePicture(holder, item);
//         天气添加
            fillTileWeatherVo(holder, item);

        } else {
//           列表内容填充
            fillContent(holder, item);
//           列表点击事件
            onclickContent(holder, item, position);
        }


    }

    //点击活动布局
    private void onclickContent(RecyclerViewHolder holder, Trend.Land item, int position) {

        ExpandableLayout expandableLayout = holder.findViewById(R.id.expandable_layout);
        AppCompatImageView ivIndicator = holder.findViewById(R.id.iv_indicator);
        expandableLayout.setInterpolator(new OvershootInterpolator());
        expandableLayout.setOnExpansionChangedListener(new ExpandableLayout.OnExpansionChangedListener() {
            @Override
            public void onExpansionChanged(float expansion, int state) {
                if (recyclerView != null && state == ExpandableLayout.State.EXPANDING) {
                    recyclerView.smoothScrollToPosition(position);
                }
                if (ivIndicator != null) {
                    ivIndicator.setRotation(expansion * 90);
                }
            }
        });

        boolean isSelected = position == mSelectPosition;
        expandableLayout.setExpanded(isSelected, false);
        holder.select(R.id.onclik, isSelected);
        holder.click(R.id.onclik, new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                onClickItem(v, expandableLayout, position, item, holder);

            }
        });
    }

    //加载二级RecyclerView
    private void onClickItem(View view, final ExpandableLayout expandableLayout, final int position, Trend.Land item, RecyclerViewHolder holder2) {
        RecyclerViewHolder holder = (RecyclerViewHolder) recyclerView.findViewHolderForAdapterPosition(mSelectPosition);
        if (holder != null) {
            holder.select(R.id.onclik, false);
            ((ExpandableLayout) holder.findViewById(R.id.expandable_layout)).collapse();
        }

        if (position == mSelectPosition) {
            mSelectPosition = -1;
        } else {
//            正在点击
            view.setSelected(true);
            expandableLayout.expand();

            //            添加适配器
            if (item.getIs_equipment()==1){
//                操作页面
            initOk(holder2, item);
            }else{
                initNo(holder2, item);
            }

//     监听关闭
            holder2.click(R.id.onclik, new View.OnClickListener() {
                @SingleClick
                @Override
                public void onClick(View v) {
                    holder2.select(R.id.onclik, false);
                    ((ExpandableLayout) holder2.findViewById(R.id.expandable_layout)).collapse();
                    onclickContent(holder2, item, position);
                }
            });
        }

    }

    private void initNo(RecyclerViewHolder holder2, Trend.Land item) {
        /*
         * 显示溯源页面   隐藏操作页面   给溯源页面添加适配器
         *
         * */
        //        按钮集合添加适配
        RecyclerView viewById = holder2.findViewById(R.id.recyclerView_chooseno);
        //设置LayoutManager为LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder2.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        viewById.setLayoutManager(linearLayoutManager);
        ControlAdapter.SourceAdapter controlAdapter = new ControlAdapter.SourceAdapter();
        controlAdapter.refresh(item.getSource());
        viewById.setAdapter(controlAdapter);
    }

    private void initOk(RecyclerViewHolder holder2, Trend.Land item) {
        /*
         * 隐藏溯源页面   显示操作页面   给操作页面赋值  给按钮集合赋值适配器
         * */
// 赋值
//        温度
        holder2.text(R.id.temperature, item.getEquipment().getTemperature());
//        湿度
        holder2.text(R.id.humidity, item.getEquipment().getTemperature());
//      光照
        holder2.text(R.id.light, item.getEquipment().getLight());
//        按钮集合添加适配
        RecyclerView viewById = holder2.findViewById(R.id.recyclerView_choose);
        //设置LayoutManager为LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder2.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        viewById.setLayoutManager(linearLayoutManager);
        ControlAdapter controlAdapter = new ControlAdapter(item.getControl());
        controlAdapter.refresh(item.getControl());
        viewById.setAdapter(controlAdapter);

    }

    /*
     * 轮播信息 (填充和点击)
     *
     * */
    public void fillTilePicture(@NonNull RecyclerViewHolder holder, Trend.Land model) {
        //            放轮播的图片信息
        SimpleImageBanner headBanner = holder.findViewById(R.id.sib_simple_usage);
        headBanner.setSource(bannerLists)
                .setOnItemClickListener(new BaseBanner.OnItemClickListener<BannerItem>() {
                    @Override
                    public void onItemClick(View view, BannerItem item, int position) {
                        XToastUtils.toast("点击轮播条--->" + position);
                    }
                }).startScroll();
    }

    public void fillTileWeatherVo(@NonNull RecyclerViewHolder holder, Trend.Land model) {
        //       天气状态添加
//        温度
        holder.text(R.id.tem, weatherVos.getTem());
//        湿度
        holder.text(R.id.humidity, weatherVos.getHumidity() + "");
//        风向
        List<String> win = weatherVos.getResult().getWin();
        String s = win.get(0);
        s = s + weatherVos.getWin_speed();
        holder.text(R.id.win, s);
//        定位
        holder.text(R.id.posit, weatherVos.getCity());


    }

    public void fillContent(@NonNull RecyclerViewHolder holder, Trend.Land model) {

//       列表内容添加
//        土地名称
        holder.text(R.id.crop_name, model.getCrop_name());
//        没有绑定设备
        if (model.getIs_equipment() == 0) {
            //隐藏时间布局
            TextView viewById = holder.findViewById(R.id.time_type);
            viewById.setVisibility(View.GONE);
//            隐藏图片加时间布局
            LinearLayout viewById1 = holder.findViewById(R.id.root_no);
            viewById1.setVisibility(View.GONE);
//            显示
            LinearLayout viewById3 = holder.findViewById(R.id.root_type_ok);
            viewById3.setVisibility(View.VISIBLE);
//            显示溯源页面
            LinearLayout viewById4 = holder.findViewById(R.id.root_no_view);
            viewById4.setVisibility(View.VISIBLE);
//            隐藏操作页面
            LinearLayout viewById5 = holder.findViewById(R.id.root_yes_view);
            viewById5.setVisibility(View.GONE);
//           设置为溯源
            TYPE_root_is = 1;
        } else {
            //        运行天数  拼接
            String time = "已运行" + model.getEquipment().getAlready() + "天/" + "剩余" + model.getEquipment().getHumidity() + "天";
            holder.text(R.id.time_type, time);
//            判断是否在线
            String is_longin = model.getEquipment().getIs_longin();
            if (is_longin.equals("1")) {
//                在线
                holder.image(R.id.click_drop_down, R.mipmap.yes);
//                文字说明
                holder.text(R.id.type, "在线");

            } else {
                //               不在线
                holder.image(R.id.click_drop_down, R.mipmap.yichang);
//                文字说明
                holder.text(R.id.type, "设备异常");
            }

        }


    }
}
