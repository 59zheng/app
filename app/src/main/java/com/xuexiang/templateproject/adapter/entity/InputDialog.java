package com.xuexiang.templateproject.adapter.entity;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;

/**

 */
public class InputDialog extends Dialog {
    public InputDialog(@NonNull Context context) {
        super(context);
//        super(context, R.style.CustomDialog);
        init(context);
    }

    public Context mContext;
    public View mRootView;

    public void init(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        setContentView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }

}