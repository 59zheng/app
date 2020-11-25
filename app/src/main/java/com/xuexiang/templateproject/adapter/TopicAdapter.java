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

package com.xuexiang.templateproject.adapter;

import android.view.View;

import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;
import com.xuexiang.templateproject.R;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.Collection;

public class TopicAdapter extends SmartRecyclerAdapter<String> {
    public TopicAdapter() {
        super(R.layout.simple_list_item_4);
    }

    public TopicAdapter(Collection<String> data) {
        super(data,R.layout.simple_list_item_3);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
        holder.text(R.id.topic_name,model);
        holder.click(R.id.topic_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toast(model+"点击了");
            }
        });

    }
}
