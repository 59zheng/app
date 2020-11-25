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

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.MyDynamicAdapter;
import com.xuexiang.templateproject.adapter.NewsCardViewListAdapter;
import com.xuexiang.templateproject.adapter.PhotoAlbumAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.fragment.profile.ProfileFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.common.ClickUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

@Page
public class MyDynamicFragment extends BaseFragment {

    private MyDynamicAdapter mAdapter;



    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.close)
    ImageView close;

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //设置沉浸式状态栏
        super.onCreate(savedInstanceState);
        StatusBarUtils.translucent(getActivity());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_dynamic;
    }

    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new MyDynamicAdapter());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }


    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
                mAdapter.refresh(DemoDataProvider.getDemoNewInfos());
                refreshLayout.finishRefresh();
            }, 1000);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
                mAdapter.loadMore(DemoDataProvider.getDemoNewInfos());
                refreshLayout.finishLoadMore();
            }, 1000);
        });
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

    }


}
