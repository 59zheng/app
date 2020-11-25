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

import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.MyPhoto;
import com.xuexiang.templateproject.adapter.entity.NewInfo;
import com.xuexiang.templateproject.adapter.entity.NineGridInfo;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.ViewHolder;

import java.util.List;

public class PhotoAlbumAdapter  extends BaseRecyclerAdapter<MyPhoto> {

    //我的相册内容
    private List<MyPhoto> mData;

    /**
     * @param bannerData 我的相册内容
     */
    public PhotoAlbumAdapter(List<MyPhoto> bannerData) {
        super();
        mData = bannerData;
    }


    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_photoalbum;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MyPhoto item) {
        View viewById = holder.findViewById(R.id.tvAcceptTime);
        holder.text(R.id.tvAcceptTime, item.getNowTime());
//媒体内容填充处理
        SendPictureFill(holder, item);


    }
    //    媒体内容填充处理
    private void SendPictureFill(RecyclerViewHolder holder, MyPhoto model) {

        //            图片信息
        String media = model.getMedia();
        List<NineGridInfo> nineGridInfos = JSON.parseArray(media, NineGridInfo.class);
        View viewById = holder.findViewById(R.id.ngl_images);
        NineGridHolder nineGridHolder = new NineGridHolder(viewById);
        nineGridHolder.bind(nineGridInfos.get(mSelectPosition + 1));
//        设置点击
    }
}
