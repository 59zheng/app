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

package com.xuexiang.templateproject.fragment.trending;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.PermissionsActivity;
import com.xuexiang.templateproject.adapter.TrendingFragmentAddAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.fragment.profile.EnterpriseFragment;
import com.xuexiang.templateproject.fragment.profile.ProfileFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xutil.app.ActivityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
*
* 智慧农业
* */
@Page(name = "智慧农业")
public class TrendingFragmentList extends BaseFragment {


    @BindView(R.id.recyclerView_list1)
    RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trending_list;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        TrendingFragmentAddAdapter adapter = new TrendingFragmentAddAdapter();
        List<String> trendingFragmentAdd = DemoDataProvider.getTrendingFragmentAdd();
        adapter.refresh(trendingFragmentAdd);
        adapter.setOnItemClickListener(new TrendingFragmentAddAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String note, int position) {
                XToastUtils.toast("position"+"点击了");
            }
        });

        recyclerView.setAdapter(adapter);
    }


    @SingleClick
    @OnClick({R.id.trending_add, R.id.trending_ok})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.trending_add) {
            openNewPage(TrendingFragmentAdd.class);
        }
        if (view.getId() == R.id.trending_ok) {
            getActivity().finish();

        }


    }


}
