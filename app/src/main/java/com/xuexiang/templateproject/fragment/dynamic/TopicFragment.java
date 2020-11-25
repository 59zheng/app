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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.ControlAdapter;
import com.xuexiang.templateproject.adapter.TopicAdapter;
import com.xuexiang.templateproject.adapter.TrendingFragmentAddAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;

import butterknife.BindView;

//话题
@Page(name = "选择话题")
public class TopicFragment  extends BaseFragment {
    @BindView(R.id.recyclerView_choose)
    RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void initViews() {
        //        按钮集合添加适配
        //设置LayoutManager为LinearLayoutManager

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        TopicAdapter controlAdapter = new TopicAdapter();
        controlAdapter.refresh(DemoDataProvider.getTopic());
        recyclerView.setAdapter(controlAdapter);

    }



}
