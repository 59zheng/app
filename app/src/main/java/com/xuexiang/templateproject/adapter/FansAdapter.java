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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.Fans;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class FansAdapter extends BaseRecyclerAdapter<Fans> {

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_fans;
    }


    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, Fans item) {
        if (item != null) {
            holder.image(R.id.headPortrait, item.getHeadPortrait());
            holder.text(R.id.my_name, item.getName());
            holder.text(R.id.my_signature, item.getSignature());
            /*
            * 点击事件*/
            View viewById = holder.findViewById(R.id.trending_ok);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XToastUtils.info(item.getName()+"取消关注了");
                }

            });
        }

    }
}
