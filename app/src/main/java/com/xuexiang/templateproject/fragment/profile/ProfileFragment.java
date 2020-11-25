/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.templateproject.fragment.profile;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.fragment.company.CompanyFragment;
import com.xuexiang.templateproject.fragment.dynamic.FansFragment;
import com.xuexiang.templateproject.fragment.dynamic.FocusFragment;
import com.xuexiang.templateproject.fragment.dynamic.MyDynamicFragment;
import com.xuexiang.templateproject.fragment.roots.RootsFragment;
import com.xuexiang.templateproject.fragment.trending.HarvestFragment;
import com.xuexiang.templateproject.fragment.trending.TrendingFragmentList;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 */
@Page(anim = CoreAnim.none)
public class ProfileFragment extends BaseFragment {

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
        return R.layout.fragment_profile;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        /*
         * 给赋值
         * */
    }


    private static String dianzan = "您一共获得10个赞";

    @SingleClick
    @OnClick({R.id.my_xiangce, R.id.my_qiye, R.id.my_nongye, R.id.my_zan, R.id.my_dynamic, R.id.focus_no, R.id.fans, R.id.roots, R.id.harvest,R.id.head_portrait})
    public void onViewClicked(View view) {
//        相册
        if (view.getId() == R.id.my_xiangce) {
            openNewPage(PhotoAlbumFragment.class);
        }
//        企业
        if (view.getId() == R.id.my_qiye) {
            openNewPage(CompanyFragment.class);
        }
//        智慧农业
        if (view.getId() == R.id.my_nongye) {
            openNewPage(TrendingFragmentList.class);
        }
//        赞
        if (view.getId() == R.id.my_zan) {
            XToastUtils.toast(dianzan);
        }
//        动态
        if (view.getId() == R.id.my_dynamic) {
            openNewPage(MyDynamicFragment.class);
        }
//        我的粉丝
        if (view.getId() == R.id.focus_no) {
            openNewPage(FansFragment.class);

        }
//        我的关注
        if (view.getId() == R.id.fans) {
            openNewPage(FocusFragment.class);
        }
//        溯源二维码
        if (view.getId() == R.id.roots) {
            openNewPage(RootsFragment.class);
        }
//        农产品收获
        if (view.getId() == R.id.harvest) {
            openNewPage(HarvestFragment.class);
        }
//        头像点击
        if (view.getId() == R.id.head_portrait) {
            openNewPage(HeadPortraitFragment.class);
        }



    }


}
