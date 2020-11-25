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

package com.xuexiang.templateproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xuexiang.templateproject.R;

import java.util.ArrayList;
import java.util.List;

/*
 *  描述： 引导页
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();//放3张图片的list
    private View view1, view2, view3;//三个视图
    //小圆点
    private ImageView point1, point2, point3;
    //跳过
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    //初始化View
    private void initView() {

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        //设置默认图片
        setPointImg(true, false, false);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true, false, false);
                        btn_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false, true, false);
                        btn_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false, false,true);
                        btn_back.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

//    点击立即体验之后到登录页面

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
            case R.id.btn_back:
                //不带参跳转activity，从引导页跳到登录页
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }

    //设置小圆点的选中效果
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if (isCheck1) {
            point1.setBackgroundResource(R.mipmap.point_on);
        } else {
            point1.setBackgroundResource(R.mipmap.point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.mipmap.point_on);
        } else {
            point2.setBackgroundResource(R.mipmap.point_off);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.mipmap.point_on);
        } else {
            point3.setBackgroundResource(R.mipmap.point_off);
        }


    }
}

