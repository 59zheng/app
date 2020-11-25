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

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.fragment.dynamic.DynamicFragment;
import com.xuexiang.templateproject.fragment.message.MessageFragment;
import com.xuexiang.templateproject.fragment.news.NewsFragment;
import com.xuexiang.templateproject.fragment.profile.ProfileFragment;
import com.xuexiang.templateproject.fragment.trending.TrendingFragment;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.banner.recycler.layout.BannerLayoutManager;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;

import butterknife.BindView;

/**
 * 程序主页面,
 */
public class MainActivity extends BaseActivity implements OnTabSelectListener, ClickUtils.OnClick2ExitListener {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    /**
     * 底部导航栏
     */

    @BindView(R.id.tabbar)
    JPTabBar mTabbar;
    /**
     * 侧边栏
     */

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    //==============需要注意的是，由于JPTabBar反射获取注解的是context，也就是容器Activity，因此需要将注解写在容器Activity内======================//
    @Titles
    public static final int[] mTitles = {R.string.menu_news, R.string.menu_farmland, R.string.menu_server, R.string.menu_profile};

    @SeleIcons
    public static final int[] mSelectIcons = {R.drawable.shouye1, R.drawable.nongtiian1, R.drawable.message1, R.drawable.wode};

    @NorIcons
    public static final int[] mNormalIcons = {R.drawable.shouye2, R.drawable.nongtiian2, R.drawable.message, R.drawable.wode2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //设置沉浸式状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtils.translucent(this);
        StatusBarUtils.setStatusBarLightMode(this);
        super.onCreate(savedInstanceState);

        initListeners();

        initView();
    }

    protected void initListeners() {
        mTabbar.setTabListener(this);
    }


    private void initView() {


        //主页内容填充
        BaseFragment[] fragments = new BaseFragment[]{
//                三种菜单页码
//                首页
                new NewsFragment(),
//                农田
                new TrendingFragment(),

//                消息
                new MessageFragment(),
//我的
                new ProfileFragment(),
//                添加动态
                new DynamicFragment(),


        };
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(mTitles.length - 1);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3, false);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //页面可以滑动
        mTabbar.setGradientEnable(true);
        mTabbar.setPageAnimateEnable(true);
        mTabbar.setTabTypeFace(XUI.getDefaultTypeface());

        mTabbar.setContainer(viewPager);

        if (mTabbar.getMiddleView() != null) {
            mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(4, false);
//                    switchPage(DynamicFragment.class);
//                    openPage(DynamicFragment.class);
                    XToastUtils.toast("中间点击");
                }
            });
        }

        mTabbar.showBadge(2, "", true);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    /**
     * 用户每次点击不同的Tab将会触发这个方法
     *
     * @param index 当前选择的TAB的索引值
     */
    @Override
    public void onTabSelect(int index) {
        if (index == 2) {
//            中间 固定的
            mTabbar.hideBadge(2);
        }
        viewPager.setCurrentItem(index, false);
        XToastUtils.toast("点击了" + getString(mTitles[index]));

    }

    /**
     * 这个方法主要用来拦截Tab选中的事件
     * 返回true,tab将不会被选中,onTabSelect也不会被回调
     * 默认返回false
     *
     * @param index 点击选中的tab下标
     * @return 布尔值
     */
    @Override
    public boolean onInterruptSelect(int index) {
        return false;
    }


    /*
     * 菜单键返回键响应
     *
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click(2000, this);
        }
        return true;
    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }

    @Override
    public void onExit() {
        XUtil.get().exitApp();
    }


}

