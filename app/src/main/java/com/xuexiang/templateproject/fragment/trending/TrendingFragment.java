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

package com.xuexiang.templateproject.fragment.trending;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.WeatherVo;
import com.xuexiang.templateproject.adapter.TrendingListAdapter;
import com.xuexiang.templateproject.adapter.entity.Trend;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.List;

import butterknife.BindView;

/**
 * 农田页面
 */
@Page(anim = CoreAnim.none)
public class TrendingFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private TrendingListAdapter mAdapter;


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
        return R.layout.fragment_trending;
    }
    private int mPage = -1;
/*
* 列表信息
* */
    private List<Trend.Land> landList;

    private   List<BannerItem> bannerList;
    private   WeatherVo weatherVo;


    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

        /*
         * 初始化数据三部分
         * 轮播   天气   列表
         * */
//        列表数据
        Trend  trend = DemoDataProvider.getThread();
        landList = trend.getLand();
//        轮播
       bannerList = DemoDataProvider.getBannerList();
//       天气预报
         weatherVo = DemoDataProvider.getWeatherVo();

        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new TrendingListAdapter(weatherVo, bannerList,recyclerView,landList));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果


    }

    @Override
    protected void initListeners() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(final @NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            XToastUtils.toast("数据全部加载完毕");
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(final @NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 0;
                        mAdapter.refresh(getMediaRes());
                        refreshLayout.finishRefresh();
                    }
                }, 1000);

            }
        });

    }
    private List<Trend.Land> getMediaRes() {
        return landList;
    }
}
