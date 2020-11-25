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

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.FansAdapter;
import com.xuexiang.templateproject.adapter.entity.Fans;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;

import java.util.List;

import butterknife.BindView;

/*
 * 粉丝关注.进来的页面
 * */
@Page(name = "我的粉丝")
public class FansFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private FansAdapter mAdapter;


    private int mPage = -1;

    private GridLayoutManager mGridLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    protected void initViews() {
        mRecyclerView.setLayoutManager(mGridLayoutManager = new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new FansAdapter());
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

    }

    private List<Fans> getMediaRes() {
        return DemoDataProvider.getFans();
    }

    @Override
    protected void initListeners() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(final @NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPage < getMediaRes().size()) {
                            mAdapter.loadMore(getMediaRes());
                            refreshLayout.finishLoadMore();
                        } else {
                            XToastUtils.toast("数据全部加载完毕");
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        }
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

        mAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener<Fans>() {
            @Override
            public void onItemClick(View itemView, Fans item, int position) {
                XToastUtils.info(item.getName()+"点击了");
            }
        });
    }
}
