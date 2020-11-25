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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.ImageViewInfo;
import com.xuexiang.templateproject.adapter.entity.NineGridInfo;
import com.xuexiang.templateproject.fragment.news.MyNineGridImageView;
import com.xuexiang.templateproject.utils.SettingSPUtils;
import com.xuexiang.xui.widget.imageview.nine.ItemImageClickListener;
import com.xuexiang.xui.widget.imageview.nine.NineGridImageView;
import com.xuexiang.xui.widget.imageview.nine.NineGridImageViewAdapter;
import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;
import com.xuexiang.xui.widget.imageview.preview.loader.GlideMediaLoader;

import java.util.List;

public class NineGridHolder extends RecyclerView.ViewHolder {
    private NineGridImageView<ImageViewInfo> mNglContent;
    private NineGridImageViewAdapter<ImageViewInfo> mAdapter = new NineGridImageViewAdapter<ImageViewInfo>() {
        /**
         * 图片加载
         *
         * @param context
         * @param imageView
         * @param imageViewInfo 图片信息
         */
        @Override
        public void onDisplayImage(Context context, ImageView imageView, ImageViewInfo imageViewInfo) {
            Glide.with(imageView.getContext())
                    .load(imageViewInfo.getUrl())
                    .apply(GlideMediaLoader.getRequestOptions())
                    .into(imageView);
        }

        @Override
        public ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }
    };
    //长按后显示的 Item
    final String[] items = new String[] { "保存图片"};
    //图片转成Bitmap数组
    final Bitmap[] bitmap = new Bitmap[1];


    public NineGridHolder(View itemView) {

        super(itemView);
        mNglContent = itemView.findViewById(R.id.ngl_images);
        mNglContent.setAdapter(mAdapter);
        mNglContent.setItemImageClickListener(new ItemImageClickListener<ImageViewInfo>() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List<ImageViewInfo> list) {
                computeBoundsBackward(list);//组成数据
                PreviewBuilder.from((Activity) context)
                        .setImgs(list)
                        .setCurrentIndex(index)
                        .setSingleFling(true)
                        .setProgressColor(SettingSPUtils.getInstance().isUseCustomTheme() ? R.color.custom_color_main_theme : R.color.xui_config_color_main_theme)
                        .setType(PreviewBuilder.IndicatorType.Dot)
                        .start();//启动
            }
        });

    }



    /**
     * 查找信息
     * @param list 图片集合
     */
    private void computeBoundsBackward(List<ImageViewInfo> list) {
        for (int i = 0;i < mNglContent.getChildCount(); i++) {
            View itemView = mNglContent.getChildAt(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView;
                thumbView.getGlobalVisibleRect(bounds);
            }
            list.get(i).setBounds(bounds);
            list.get(i).setUrl(list.get(i).getUrl());
        }

    }

    public void bind(NineGridInfo gridInfo) {
        mNglContent.setImagesData(gridInfo.getImgUrlList(), gridInfo.getSpanType());
    }
}
