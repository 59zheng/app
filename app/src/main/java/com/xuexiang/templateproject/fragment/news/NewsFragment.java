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

package com.xuexiang.templateproject.fragment.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.NewsCardViewListAdapter;
import com.xuexiang.templateproject.adapter.SimpleRecyclerAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.Collection;

import butterknife.BindView;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.none)
public class NewsFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private NewsCardViewListAdapter mAdapter;


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
        return R.layout.fragment_news;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {//初始化控件
        WidgetUtils.initRecyclerView(recyclerView, 0);
        //加载评论的适配器
        recyclerView.setAdapter(mAdapter = new NewsCardViewListAdapter(DemoDataProvider.getBannerList()));

    }

    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {//下拉刷新触发此方法
                mAdapter.refresh(DemoDataProvider.getDemoNewInfos());
                refreshLayout.finishRefresh();
            }, 1000);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
             //下拉加载触发此方法
            refreshLayout.getLayout().postDelayed(() -> {
                mAdapter.loadMore(DemoDataProvider.getDemoNewInfos());
                refreshLayout.finishLoadMore();
            }, 1000);
        });
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

    }

    /*
     * 替换为评论页面
     * */
    public void jumpComment(Context context) {
        showInputTextDialog(true, context);
    }


    /*弹出评论页面
     * */
    private void showInputTextDialog(boolean isList, Context context) {
//    Context context = getContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_text, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_list);
        EditText inputText = view.findViewById(R.id.et_input_message);
        ImageView imageView = view.findViewById(R.id.iv_confirm);
        ImageView imageViewClose = view.findViewById(R.id.dialog_bottomsheet_iv_close);
//        评论数据
        initDialogList(recyclerView);
//        输入点击
        initDialogInputText(inputText);
//        发送按钮点击
        initDialogSendButtn(imageView);
//关闭按钮
        initDialogCloseImage(imageViewClose, dialog);

        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    //点击输入框
    private void initDialogInputText(EditText inputText) {

    }

    //    点击关闭
    private void initDialogCloseImage(ImageView inputText, BottomSheetDialog dialog) {
        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    //点击发送按钮
    private void initDialogSendButtn(ImageView inputText) {


    }

    //   评论 填数据
    private void initDialogList(RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        SimpleRecyclerAdapter adapter = new SimpleRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        Collection<String> demoData = DemoDataProvider.getDemoData();
        adapter.refresh(DemoDataProvider.getDemoData());
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
