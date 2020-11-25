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

import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.Fans;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.Collection;

public class TrendingFragmentAddAdapter  extends SmartRecyclerAdapter<String> {



    public TrendingFragmentAddAdapter() {
        super(R.layout.simple_list_item_3);
    }

    public TrendingFragmentAddAdapter(Collection<String> data) {
        super(data,R.layout.simple_list_item_3);
    }
    @Override
    protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
        holder.text(R.id.text_name, model);
        //实现点击效果
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, model, position);
                }
            }
        });
    }




    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, String model, int position);
    }




}
