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

package com.xuexiang.templateproject.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.utils.ShareUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.xui.utils.KeyboardUtils;

/**
 * 启动页【无需适配屏幕大小】
 *
 * @author xuexiang
 * @since 2019-06-30 17:32
 */

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/*
 *  描述： 启动页
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.Activity全屏主题
     */

    //闪屏业延时
    private static final int HANDLER_SPLASH = 1001;
    //判断程序是否是第一次运行
    private static final String SHARE_IS_FIRST = "isFirst";
//    判断是否有token是否已经登录


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        if (TokenUtils.hasToken()){
//                        包含token已经登录
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }else{
                            //                        不包含token已经登录
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initView();
    }

    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH, 2000);
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, SHARE_IS_FIRST, false);
            //是第一次运行
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }

}

