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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.Hot;
import com.xuexiang.templateproject.adapter.entity.ImageViewInfo;
import com.xuexiang.templateproject.adapter.entity.NewInfo;
import com.xuexiang.templateproject.adapter.entity.NineGridInfo;
import com.xuexiang.templateproject.fragment.news.NewsFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.SettingSPUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.nine.ItemImageClickListener;
import com.xuexiang.xui.widget.imageview.nine.NineGridImageView;
import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;


import java.util.ArrayList;
import java.util.List;


/**
 * 首页动态
 *
 * @since 2019/5/9 10:41
 */
/*新闻适配*/
public class NewsCardViewListAdapter extends BaseRecyclerAdapter<NewInfo> {

    private static final int TYPE_BANNER_HEAD = 0;
    private static final int TYPE_COMMON = 1;
    //轮播
    private List<BannerItem> mData;
    //
    private NineGridImageView<ImageViewInfo> mNglContent;


    /**
     * @param bannerData 轮播条的内容
     */
    public NewsCardViewListAdapter(List<BannerItem> bannerData) {
        super();
        mData = bannerData;
    }


    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_BANNER_HEAD) {
//            轮播
            return R.layout.include_head_view_banner;
        } else {
//            循环动态
            return R.layout.adapter_news_card_view_list_item;
        }
    }

    /*
     *
     *
     * */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER_HEAD;
        } else {
            return TYPE_COMMON;
        }
    }

    /*\
     *
     * 占位    加载的时候先进行占位，等图片异步加载完成之后替换占位的空白，防止下拉加载，
     * 上拉刷新的时候记载资源过多造成卡顿
     *
     * */
    public XRecyclerAdapter refresh(List<NewInfo> data) {
        List<NewInfo> list = new ArrayList<>(data);
        //用于占位
        list.add(0, new NewInfo());
        return super.refresh(list);
    }

    /*
     *
     * 数据绑定,点击处理
     *
     * */
    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, int position, NewInfo model) {
        if (model == null) {
            return;
        }
        if (getItemViewType(position) == TYPE_BANNER_HEAD) {
//          轮播图片点击
            fillTilePicture(holder, model);
//          关注和推荐切换点击
            fillTileSelect(holder, model);

        } else {
//           列表内容填充
            fillContent(holder, model);
//           列表点击事件
            onclickContent(holder, model);
        }
    }

    /*
     *
     * 列表内容填充
     *
     * */
    public void fillContent(@NonNull RecyclerViewHolder holder, NewInfo model) {
        //            内容添加开始
//            昵称
        holder.text(R.id.tv_user_name, model.getUser().getNickname());
//              点赞数
        holder.text(R.id.home_good, model.getUp_num() + "");
//            评论数
        holder.text(R.id.tv_comment, model.getComment_num() + "");
        //    转发数量
        holder.text(R.id.tv_praise, model.getTurn_num() + "");

//            发表时间
        holder.text(R.id.tv_user_login_time, model.getAddtime());
//            头像信息
        holder.image(R.id.iv_avatar, model.getUser().getImage());
        List<Hot> hot = model.getHot();
//            条目信息
        holder.text(R.id.Tag, hot.get(0).getName());
//            正文
        holder.text(R.id.dynamic_text, model.getContent());
//            定位信息
        holder.text(R.id.positioning, model.getAddress());
//媒体内容填充处理
        SendPictureFill(holder, model);


//内容添加完成
    }

    //    媒体内容填充处理
    private void SendPictureFill(RecyclerViewHolder holder, NewInfo model) {

        //            图片信息
        String media = model.getMedia();
        List<NineGridInfo> nineGridInfos = JSON.parseArray(media, NineGridInfo.class);

        NineGridHolder nineGridHolder = new NineGridHolder(holder.findViewById(R.id.ngl_images));
        nineGridHolder.bind(nineGridInfos.get(mSelectPosition + 1));
//        设置点击
    }


    /*
     *
     * 列表点击事件
     *
     * */
    public void onclickContent(@NonNull RecyclerViewHolder holder, NewInfo model) {



        /*列表中的点击项*/
//关注
        onclickFocusOn(holder, model);
//        点赞
        onclickGiveLike(holder, model);
//转发
        onclickForward(holder, model);
//        评论
        onclickComment(holder, model);
    }


    /*
     * 轮播信息 (填充和点击)
     *
     * */
    public void fillTilePicture(@NonNull RecyclerViewHolder holder, NewInfo model) {
        //            放轮播的图片信息
        SimpleImageBanner headBanner = holder.findViewById(R.id.sib_simple_usage);
        headBanner.setSource(mData)
                .setOnItemClickListener(new BaseBanner.OnItemClickListener<BannerItem>() {
                    @Override
                    public void onItemClick(View view, BannerItem item, int position) {
                        XToastUtils.toast("点击轮播条--->" + position);
                    }
                }).startScroll();
    }

    /*
     * 推荐和关注(点击和效果添加)
     *
     * */
    public void fillTileSelect(@NonNull RecyclerViewHolder holder, NewInfo model) {

        //            关注或者推荐的按钮点击
        TextView viewById = holder.findViewById(R.id.btn_label);
        TextView viewById2 = holder.findViewById(R.id.btn_label2);
        ImageButton ib = holder.findViewById(R.id.message);
        ImageButton ib2 = holder.findViewById(R.id.message2);


        viewById.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                            改变颜色
                        viewById2.setTextColor(Color.parseColor("#999999"));
//                            改变颜色
                        viewById.setTextColor(Color.parseColor("#70cd86"));
//                          设置透明
                        ib2.setVisibility(View.GONE);
//                            显示
                        ib.setVisibility(View.VISIBLE);
                    }
                });
        viewById2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                            改变颜色
                        viewById2.setTextColor(Color.parseColor("#70cd86"));
                        //                            改变颜色
                        viewById.setTextColor(Color.parseColor("#999999"));
//                          设置透明
                        ib.setVisibility(View.GONE);
//                            显示
                        ib2.setVisibility(View.VISIBLE);

                    }
                });

    }


    /*
     *
     * 关注按钮点击 以及不同状态样式显示
     *
     * */
    public void onclickFocusOn(@NonNull RecyclerViewHolder holder, NewInfo model) {
//            关注
//            判断是否关注给不同的样式
        TextView viewById = holder.findViewById(R.id.home_page_guangzhu);
        int is_fans = model.getUser().getIs_fans();
        if (is_fans == 0) {
//                未关注
            viewById.setText("+关注");
            viewById.setTextColor(Color.parseColor("#000000"));
            setBgColorForQMUIRB(viewById, "#70cd86");
        } else if (is_fans == 1) {
            //                已关注
            viewById.setText("你关注的人");
            viewById.setTextColor(Color.parseColor("#FFFFFF"));
            setBgColorForQMUIRB(viewById, "#999999");
        }

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewById.setText("你关注的人");
                viewById.setTextColor(Color.parseColor("#FFFFFF"));
                setBgColorForQMUIRB(viewById, "#999999");
            }

        });
    }


    /*
     *静态变量  是否点击
     *
     * */
    private static int is_click = 0;

    /*
     *
     *点赞按钮的 效果添加和 点击事件
     *
     * */

    public void onclickGiveLike(@NonNull RecyclerViewHolder holder, NewInfo model) {
        ImageView viewById = holder.findViewById(R.id.home_good_picture);

        if (model.getUp() == 1) {
//            已经点赞
            viewById.setImageResource(R.drawable.ic_good_checked);
            is_click = 0;
        } else if (model.getUp() == 0) {
            viewById.setImageResource(R.drawable.ic_good);
            is_click = 1;
        }

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_click == 0) {
                    viewById.setImageResource(R.drawable.ic_good_checked);
                    is_click = 1;
                } else if (is_click == 1) {
                    viewById.setImageResource(R.drawable.ic_good);
                    is_click = 0;
                }

            }

        });

    }
    /*
     *
     * 转发按钮的弹框 点击事件
     *
     * */

    public void onclickForward(@NonNull RecyclerViewHolder holder, NewInfo model) {
        ImageView viewById = holder.findViewById(R.id.news_forward);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(holder.getContext())
                        .content("是否确认转发")
                        .positiveText(R.string.lab_yes)
                        .negativeText(R.string.lab_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                Toast.makeText(holder.getContext(), "点击了确认按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                Toast.makeText(holder.getContext(), "点击了取消按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

    }

    /*
     * 评论按钮的点击事件
     *
     * */
    public void onclickComment(@NonNull RecyclerViewHolder holder, NewInfo model) {
        View viewById = holder.findViewById(R.id.home_comment);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调NewsFragment的指定方法
                NewsFragment newsFragment = new NewsFragment();
                newsFragment.jumpComment(holder.getContext());


            }
        });
    }

    /*
     *
     * 图片加载
     *
     * */
    private void setBgColorForQMUIRB(TextView qmuiRoundButton, String color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);//形状
        gradientDrawable.setCornerRadius(1000);//设置圆角Radius
        gradientDrawable.setColor(Color.parseColor(color));//颜色

        qmuiRoundButton.setBackground(gradientDrawable);//设置为background

    }

}
