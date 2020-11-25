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

package com.xuexiang.templateproject.fragment.trending;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.PermissionsActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleUtils;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

@Page(name = "农产品收获")
public class HarvestFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_harves_list;
    }

    @Override
    protected void initViews() {


    }
    private  View mRootView ;

    private ImageView picture;
     /*
     * 图片资源
     * */
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRootView=view;
        super.onViewCreated(view, savedInstanceState);
        //当android系统小于5.0的时候直接定位显示，不用动态申请权限

        mRootView.findViewById(R.id.btn_land).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new BottomSheet.BottomListSheetBuilder(getActivity())
                            .addItem("大地")
                            .addItem("中地")
                            .addItem("小地")
                            .setIsCenter(true)
                            .setOnSheetItemClickListener(new BottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                                @Override
                                public void onClick(BottomSheet dialog, View itemView, int position, String tag) {
                                    dialog.dismiss();
                                    XToastUtils.toast("Item " + (position + 1));
                                    EditText viewById1 = mRootView.findViewById(R.id.btn_land_text_no);
                                    viewById1.setText(tag);
                                }
                            })
                            .build()
                            .show();
                }
        });
        mRootView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new BottomSheet.BottomListSheetBuilder(getActivity())
                            .addItem("是")
                            .addItem("否")
                            .setIsCenter(true)
                            .setOnSheetItemClickListener(new BottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                                @Override
                                public void onClick(BottomSheet dialog, View itemView, int position, String tag) {
                                    dialog.dismiss();
                                    XToastUtils.toast("Item " + (position + 1));
                                    EditText viewById1 = mRootView.findViewById(R.id.btn_boolean_text);
                                    viewById1.setText(tag);
                                }
                            })
                            .build()
                            .show();
            }
        });
        mRootView.findViewById(R.id.trending_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mRootView.findViewById(R.id.photo_business).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = view.findViewById(R.id.add_business);
                Utils.getPictureSelectorOne(HarvestFragment.this)
                        .selectionMedia(mSelectList)
                        .forResult(101);
            }
        });


    }
    /*
     * 上传完成后统一处理 图片
     * */
    public void replacePhoto(List<LocalMedia> mSelectList, ImageView imageView1) {
        imageView1.setImageURI(Uri.parse(mSelectList.get(0).getPath()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    replacePhoto(mSelectList, picture);
                    mSelectList.clear();
                    break;
                default:
                    break;
            }
        }
    }






    }


