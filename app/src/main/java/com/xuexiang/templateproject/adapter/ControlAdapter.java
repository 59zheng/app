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

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.Fans;
import com.xuexiang.templateproject.adapter.entity.Trend;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.List;

/*
 * 操作列表
 * */
public class ControlAdapter extends BaseRecyclerAdapter<Trend.Control> {

    private List<Trend.Control> controlList;

    public ControlAdapter(List<Trend.Control> control) {
        controlList = control;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_tred_list_button;
    }


    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, Trend.Control item) {
        if (holder == null) {
            return;
        }

        holder.text(R.id.top_name, item.getAction_name());
        RecyclerView viewById = holder.findViewById(R.id.recyclerView_choose);
        //设置LayoutManager为LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.getContext());
        viewById.setHasFixedSize(true);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        ContentAdapter contentAdapter = new ContentAdapter(item.getContent());
        viewById.setLayoutManager(linearLayoutManager);

        contentAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener<Trend.Content>() {
            @Override
            public void onItemClick(View itemView, Trend.Content item, int position) {
                ToastUtils.toast(item.getName() + "" + position + "操作成功");
            }
        });
        contentAdapter.refresh(item.getContent());
        viewById.setAdapter(contentAdapter);
    }


    /*
     * 操作列表
     * */
    public class ContentAdapter extends BaseRecyclerAdapter<Trend.Content> {

        private List<Trend.Content> controlList;

        public ContentAdapter(List<Trend.Content> control) {
            controlList = control;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.adapter_tred_one_button;
        }


        @Override
        protected void bindData(@NonNull RecyclerViewHolder holder, int position, Trend.Content item) {
            holder.text(R.id.icon_name, item.getName());
            holder.image(R.id.icon, item.getIcon());

        }
    }


    /*
     * 溯源列表
     * */
    public static class SourceAdapter extends BaseRecyclerAdapter<Trend.Source> {


        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.adapter_tred_no_list_button;
        }

        @Override
        protected void bindData(@NonNull RecyclerViewHolder holder, int position, Trend.Source item) {
//            赋值
            holder.text(R.id.root_name, item.getAction_name());
//            设置点击
            holder.click(R.id.clock_in, new View.OnClickListener() {
                @SingleClick
                @Override
                public void onClick(View v) {
                    ToastUtils.toast(item.getAction_name() + "" + position + "打卡成功");

                }
            });


        }
    }
}
