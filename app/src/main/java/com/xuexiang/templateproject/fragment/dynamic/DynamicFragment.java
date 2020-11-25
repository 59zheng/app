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

package com.xuexiang.templateproject.fragment.dynamic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.PermissionsActivity;
import com.xuexiang.templateproject.adapter.ImageSelectGridAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


/*
*
* 添加动态页面
* */

@Page(anim = CoreAnim.none)
public class DynamicFragment extends BaseFragment implements ImageSelectGridAdapter.OnAddPicClickListener {


    //显示图片
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    /*
     *
     * 图片选择适配器
     * */
    private ImageSelectGridAdapter mAdapter;

    /*
     * 上传后的数据流集合
     * */
    private List<LocalMedia> mSelectList = new ArrayList<>();

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {

        return null;
    }


    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new ImageSelectGridAdapter(getActivity(), this));
        mAdapter.setSelectList(mSelectList);
        mAdapter.setSelectMax(8);
        mAdapter.setOnItemClickListener(new ImageSelectGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create(DynamicFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList);
            }
        });

    }

    @OnClick({R.id.button, R.id.button_paizhao, R.id.button_biaoqian, R.id.button_quanxian, R.id.button_weizhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                Utils.getPictureSelector(this)
                        .selectionMedia(mSelectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
        }
        switch (view.getId()) {
            case R.id.button_paizhao:
                Utils.getPictureSelectorpaizhao(this)
                        .selectionMedia(mSelectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
        }
        switch (view.getId()) {
            case R.id.button_biaoqian:
                openNewPage(TopicFragment.class);
                break;
        }
        switch (view.getId()) {
            case R.id.button_weizhi:
                this.startActivityForResult(new Intent(getActivity(), PermissionsActivity.class), 500);
                break;
        }
        switch (view.getId()) {
            case R.id.button_quanxian:
                showMultiChoiceDialog();
                break;
        }





}
    /**
     * 带多选项的Dialog
     */
    private void showMultiChoiceDialog() {
        new MaterialDialog.Builder(getContext())
                .title("可见范围")
                .items(R.array.router_choice_entry)
                .itemsCallbackMultiChoice(
                        new Integer[]{0, 1},
                        new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                StringBuilder sb = new StringBuilder("选中：\n");
                                for (int i = 0; i < which.length; i ++){
                                    sb.append(which[i]).append(":").append(text[i]).append("\n");
                                }
                                XToastUtils.toast(sb.toString());
                                return true;
                            }
                        })
                .positiveText(R.string.lab_choice)
                .negativeText(R.string.lab_cancel)
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    mAdapter.setSelectList(mSelectList);
                    mAdapter.setSelectMax(9);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            if (requestCode == 500) {
                if (resultCode == RESULT_OK) {
                    //获取返回信息
                    String name = data.getExtras().getString("name");
                    double lat = data.getExtras().getDouble("lat");
                    double lon = data.getExtras().getDouble("lon");
                    XToastUtils.toast("名字" + name + "经度" + lat +"纬度"+lon);

                }

            }
        }
    }

    @Override
    public void onAddPicClick() {
        Utils.getPictureSelector(this)
                .selectionMedia(mSelectList)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
