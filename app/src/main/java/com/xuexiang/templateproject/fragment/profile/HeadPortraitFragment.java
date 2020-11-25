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

package com.xuexiang.templateproject.fragment.profile;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectChangeListener;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.OnClick;

@Page(name = "个人资料")
public class HeadPortraitFragment extends BaseFragment {

    private TimePickerView mDatePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_head_portrait;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.btn_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                showDatePicker();
                break;
            default:
                break;
        }
    }

    private void showDatePicker() {
        if (mDatePicker == null) {
            mDatePicker = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelected(Date date, View v) {
                    XToastUtils.toast(DateUtils.date2String(date, DateUtils.yyyyMMdd.get()));

                }
                     })
                    .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                        @Override
                        public void onTimeSelectChanged(Date date) {
                            Log.i("pvTime", "onTimeSelectChanged");
                        }
                    })
                    .setTitleText("日期选择")
                    .setLineSpacingMultiplier(2.0F)
                    .build();
        }
        mDatePicker.show();
    }


}
