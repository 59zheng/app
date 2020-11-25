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

package com.xuexiang.templateproject.fragment.company;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.activity.PermissionsActivity;
import com.xuexiang.templateproject.adapter.ImageSelectGridAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.fragment.dynamic.FansFragment;
import com.xuexiang.templateproject.fragment.dynamic.FocusFragment;
import com.xuexiang.templateproject.fragment.dynamic.MyDynamicFragment;
import com.xuexiang.templateproject.fragment.profile.PhotoAlbumFragment;
import com.xuexiang.templateproject.fragment.trending.TrendingFragmentList;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

@Page(name="我的企业")
public class CompanyFragment extends BaseFragment {
    /*
     *
     *图片资源*/
    private List<LocalMedia> mSelectList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fargment_company;
    }

    @Override
    protected void initViews() {
    }



    private ImageView picture;
    private ImageView picture1;
    private ImageView picture2;

    @SingleClick
    @OnClick({R.id.photo_business, R.id.photo_product, R.id.photo_license, R.id.trending_ok})
    public void onViewClicked(View view) {
        //        我的关注
        if (view.getId() == R.id.photo_business) {
            /*
             * 上传后的数据流集合
             * */
            picture = view.findViewById(R.id.add_business);

            Utils.getPictureSelectorOne(this)
                    .selectionMedia(mSelectList)
                    .forResult(101);
        }
        if (view.getId() == R.id.photo_product) {
            /*
             * 上传后的数据流集合
             * */
            picture1 = view.findViewById(R.id.product_add);

            Utils.getPictureSelectorOne(this)
                    .selectionMedia(mSelectList)
                    .forResult(102);
        }
        if (view.getId() == R.id.photo_license) {
            /*
             * 上传后的数据流集合
             * */
            picture2 = view.findViewById(R.id.license_add);

            Utils.getPictureSelectorOne(this)
                    .selectionMedia(mSelectList)
                    .forResult(103);
        }
        if (view.getId() == R.id.trending_ok) {
            /*
             * 上传后的数据流集合
             * */
            getActivity().finish();
        }


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
                case 102:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    replacePhoto(mSelectList, picture1);
                    mSelectList.clear();
                    break;
                case 103:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    replacePhoto(mSelectList, picture2);
                    mSelectList.clear();
                    break;
                default:
                    break;
            }
        }
    }
}
