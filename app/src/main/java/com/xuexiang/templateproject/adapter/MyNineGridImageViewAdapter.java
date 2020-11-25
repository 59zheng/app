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

import android.content.Context;
import android.widget.ImageView;

import com.xuexiang.xui.widget.imageview.nine.GridImageView;

import java.util.List;
/*图片九宫格适配*/
public abstract class MyNineGridImageViewAdapter<T> {
    /**
     * 图片加载
     *
     * @param context
     * @param imageView
     * @param t         图片信息
     */
    public abstract void onDisplayImage(Context context, ImageView imageView, T t);

    /**
     * 图片点击
     *
     * @param context
     * @param imageView
     * @param index     索引
     * @param list      数据集合
     */
    public void onItemImageClick(Context context, ImageView imageView, int index, List<T> list) {
    }

    /**
     * 图片长按
     *
     * @param context
     * @param imageView
     * @param index     索引
     * @param list      数据集合
     * @return
     */
    public boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<T> list) {
        return false;
    }

    /**
     * 构建图片
     *
     * @param context
     * @return
     */
    public ImageView generateImageView(Context context) {
        GridImageView imageView = new GridImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
