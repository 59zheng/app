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

package com.xuexiang.templateproject.fragment.news;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.MyNineGridImageViewAdapter;
import com.xuexiang.xui.widget.imageview.nine.ItemImageClickListener;
import com.xuexiang.xui.widget.imageview.nine.ItemImageLongClickListener;
import com.xuexiang.xui.widget.imageview.nine.NineGridImageView;

import java.util.ArrayList;
import java.util.List;

public class MyNineGridImageView<T> extends ViewGroup {
    public final static int STYLE_GRID = 0;     // 宫格布局
    public final static int STYLE_FILL = 1;     // 全填充布局(自适应填充满，推荐使用）
    ///////////////////////////////////////////////////////////////////////////
    // 跨行跨列的类型
    ///////////////////////////////////////////////////////////////////////////
    public final static int NOSPAN = 0;         // 不跨行也不跨列(推荐使用)
    public final static int TOPCOLSPAN = 2;     // 首行跨列
    public final static int BOTTOMCOLSPAN = 3;  // 末行跨列
    public final static int LEFTROWSPAN = 4;    // 首列跨行

    private int mRowCount;                      // 行数
    private int mColumnCount;                   // 列数

    private int mMaxSize;                       // 最大图片数
    private int mShowStyle;                     // 显示风格
    private int mGap;                           // 宫格间距
    private int mSingleImgSize;                 // 单张图片时的尺寸
    private int mGridSize;                      // 宫格大小,即图片大小
    private int mSpanType;                      // 跨行跨列的类型
    private int width;                      // 控件的宽度
    private int height;                      // 控件的宽度
    private int totalWidth;                      // 实际控件的宽度去除左右padding

    private List<ImageView> mImageViewList = new ArrayList<>();
    private List<T> mImgDataList;

    private MyNineGridImageViewAdapter<T> mAdapter;
    private ItemImageClickListener<T> mItemImageClickListener;
    private ItemImageLongClickListener<T> mItemImageLongClickListener;

    public MyNineGridImageView(Context context) {
        this(context, null);
    }

    public MyNineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridImageView);
        mGap = (int) typedArray.getDimension(R.styleable.NineGridImageView_ngiv_imgGap, 0);
        mSingleImgSize = typedArray.getDimensionPixelSize(R.styleable.NineGridImageView_ngiv_singleImgSize, -1);
        mShowStyle = typedArray.getInt(R.styleable.NineGridImageView_ngiv_showStyle, STYLE_GRID);
        mMaxSize = typedArray.getInt(R.styleable.NineGridImageView_ngiv_maxSize, 9);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImgDataList != null && mImgDataList.size() > 0) {
            if (mImgDataList.size() == 1 && mSingleImgSize != -1) {
                mGridSize = mSingleImgSize > totalWidth ? totalWidth : mSingleImgSize;
            } else {
                mImageViewList.get(0).setScaleType(ImageView.ScaleType.CENTER_CROP);
                mGridSize = (totalWidth - mGap * (mColumnCount - 1)) / mColumnCount;
            }
            height = mGridSize * mRowCount + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenView();
    }

    /**
     * 根据照片数量和span类型来对子视图进行动态排版布局
     */
    private void layoutChildrenView() {
        if (mImgDataList == null) return;
        int showChildrenCount = getNeedShowCount(mImgDataList.size());
        //对不跨行不跨列的进行排版布局,单张进行普通排版
        if (showChildrenCount == 1) {
            layoutForNoSpanChildrenView(showChildrenCount);
            return;
        }
        switch (showChildrenCount) {
            case 2:
                layoutForTwoChildrenView(showChildrenCount);
                break;
            case 3:
                layoutForThreeChildrenView(showChildrenCount);
                break;
            default:
                layoutForThreeChildrenView(showChildrenCount);
                break;
        }
    }

    private void layoutForNoSpanChildrenView(int childrenCount) {
//        没有资源退出
        if (childrenCount <= 0) return;
        int row, column, left, top, right, bottom;
        ImageView childrenView = (ImageView) getChildAt(0);
        row = 0 / mColumnCount;
        column = 0 % mColumnCount;
        left = (mGap) * column + getPaddingLeft();
        top = (mGap) * row + getPaddingTop();
        right = left + mSingleImgSize;
        bottom = top + mSingleImgSize;
        childrenView.layout(left, top, right, bottom);
        if (mAdapter != null) {
            mAdapter.onDisplayImage(getContext(), childrenView, mImgDataList.get(0));
        }
    }

    private void layoutForTwoChildrenView(int childrenCount) {
        //        没有资源退出
        if (childrenCount <= 0) return;
        int row, column, left, top, right, bottom;
//            两张图片的时候第一张的布局
        ImageView childrenView = (ImageView) getChildAt(0);
//            左
        left =  getPaddingLeft();
//            上
        top =  getPaddingTop();
//            右
        right = left + mSingleImgSize;
//            底部
        bottom = getPaddingBottom();
        childrenView.layout(left, top, right, bottom);
        if (mAdapter != null) {
            mAdapter.onDisplayImage(getContext(), childrenView, mImgDataList.get(0));
        }

        //            两张图片的时候第二张的布局
        ImageView childrenView2 = (ImageView) getChildAt(1);
//            左
        left = (mSingleImgSize +  mGap) + getPaddingLeft();
//            上
        top =  getPaddingTop();
//            右
        right =getPaddingRight();
//            底部
        bottom = getPaddingBottom() ;
        childrenView2.layout(left, top, right, bottom);
        if (mAdapter != null) {
            mAdapter.onDisplayImage(getContext(), childrenView2, mImgDataList.get(1));
        }
    }

    private void layoutForThreeChildrenView(int childrenCount) {
        //        没有资源退出
        if (childrenCount <= 0) return;
        int left, top, right, bottom;
        for (int i = 0; i < childrenCount; i++) {
            if (i==0){

//            三张图片的时候第一张的布局
                ImageView childrenView = (ImageView) getChildAt(0);
//            左
                left =  getPaddingLeft();
//            上
                top = (mGap)  + getPaddingTop();
//            右
                right = left + mSingleImgSize;
//            底部
                bottom = mGap +getPaddingBottom();
                childrenView.layout(left, top, right, bottom);
                if (mAdapter != null) {
                    mAdapter.onDisplayImage(getContext(), childrenView, mImgDataList.get(0));
                }
            }else if (i==1){
                //            三张图片的时候第er张的布局
                ImageView childrenView2 = (ImageView) getChildAt(1);
//            左
                left = (mSingleImgSize +  mGap)  + getPaddingLeft();
//            上
                top = (mGap) + getPaddingTop();
//            右
                right = getPaddingRight();
//            底部
                bottom =getPaddingBottom()+mGap+mSingleImgSize/2;
                childrenView2.layout(left, top, right, bottom);
                if (mAdapter != null) {
                    mAdapter.onDisplayImage(getContext(), childrenView2, mImgDataList.get(1));
                }
            }else if (i==2){
                //            三张图片的时候第三张的布局
                ImageView childrenView3 = (ImageView) getChildAt(2);
//            左
                left = (mSingleImgSize +  mGap) + getPaddingLeft();
//            上
                top = ( mGap + (mSingleImgSize) / 2) + getPaddingTop();
//            右
                right =getPaddingRight();
//            底部
                bottom = mGap;
                childrenView3.layout(left, top, right, bottom);
                if (mAdapter != null) {
                    mAdapter.onDisplayImage(getContext(), childrenView3, mImgDataList.get(2));
                }
            }
        }





    }


    /**
     * 根据跨行跨列的类型，以及图片数量，来确定单元格的行数和列数
     *
     * @param imagesSize 图片数量
     * @param gridParam  单元格的行数和列数
     */
    private void generatUnitRowAndColumnForSpanType(int imagesSize, int[] gridParam) {
        if (imagesSize == 1) {
            switch (mSpanType) {
                case NOSPAN:
                    gridParam[0] = 1;
                    gridParam[1] = imagesSize;
            }
        } else if (imagesSize == 2) {
            switch (mSpanType) {
                case NOSPAN:   //不跨行不跨列
                    gridParam[0] = 1;
                    gridParam[1] = 2;
                    break;
            }
        } else if (imagesSize >= 3) {
            switch (mSpanType) {
                case NOSPAN:   //3行3列,首列跨行
                    gridParam[0] = 1;
                    gridParam[1] = 2;
                    break;
            }
        }
    }

    public void setImagesData(List<T> lists) {
        setImagesData(lists, NOSPAN);
    }

    /**
     * 设置图片数据
     *
     * @param lists    图片数据集合
     * @param spanType 跨行跨列排版类型
     */
    public void setImagesData(List<T> lists, int spanType) {
        if (lists == null || lists.isEmpty()) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }
        mSpanType = spanType;
        int newShowCount = getNeedShowCount(lists.size());

        int[] gridParam = calculateGridParam(newShowCount, mShowStyle);
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];
        if (mImgDataList == null) {
            int i = 0;
            while (i < newShowCount) {
                ImageView iv = getImageView(i);
                if (iv == null) {
                    return;
                }
                addView(iv, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldShowCount = getNeedShowCount(mImgDataList.size());
            if (oldShowCount > newShowCount) {
                removeViews(newShowCount, oldShowCount - newShowCount);
            } else if (oldShowCount < newShowCount) {
                for (int i = oldShowCount; i < newShowCount; i++) {
                    ImageView iv = getImageView(i);
                    if (iv == null) {
                        return;
                    }
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
        mImgDataList = lists;
        requestLayout();
    }

    private int getNeedShowCount(int size) {
        if (mMaxSize > 0 && size > mMaxSize) {
            return mMaxSize;
        } else {
            return size;
        }
    }

    /**
     * 获得 ImageView
     * 保证了 ImageView 的重用
     *
     * @param position 位置
     */
    private ImageView getImageView(final int position) {
        if (position < mImageViewList.size()) {
            return mImageViewList.get(position);
        } else {
            if (mAdapter != null) {
                ImageView imageView = mAdapter.generateImageView(getContext());
                mImageViewList.add(imageView);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.onItemImageClick(getContext(), (ImageView) v, position, mImgDataList);
                        if (mItemImageClickListener != null) {
                            mItemImageClickListener.onItemImageClick(getContext(), (ImageView) v, position, mImgDataList);
                        }
                    }
                });
                imageView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        boolean consumedEvent = mAdapter.onItemImageLongClick(getContext(), (ImageView) v, position, mImgDataList);
                        if (mItemImageLongClickListener != null) {
                            consumedEvent = mItemImageLongClickListener.onItemImageLongClick(getContext(), (ImageView) v, position, mImgDataList) || consumedEvent;
                        }
                        return consumedEvent;
                    }
                });
                return imageView;
            } else {
                Log.e("NineGirdImageView", "Your must set a MyNineGridImageViewAdapter for NineGirdImageView");
                return null;
            }
        }
    }

    /**
     * 设置 宫格参数
     *
     * @param imagesSize 图片数量
     * @param showStyle  显示风格
     * @return 宫格参数 gridParam[0] 宫格行数 gridParam[1] 宫格列数
     */
    protected int[] calculateGridParam(int imagesSize, int showStyle) {
        int[] gridParam = new int[2];
        switch (showStyle) {
            case STYLE_FILL:
                generatUnitRowAndColumnForSpanType(imagesSize, gridParam);
                break;
            default:
            case STYLE_GRID:
                gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                gridParam[1] = 3;
        }
        return gridParam;
    }

    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(MyNineGridImageViewAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * 设置宫格间距
     *
     * @param gap 宫格间距 px
     */
    public void setGap(int gap) {
        mGap = gap;
    }

    /**
     * 设置显示风格
     *
     * @param showStyle 显示风格
     */
    public void setShowStyle(int showStyle) {
        mShowStyle = showStyle;
    }

    /**
     * 设置只有一张图片时的尺寸大小
     *
     * @param singleImgSize 单张图片的尺寸大小
     */
    public void setSingleImgSize(int singleImgSize) {
        mSingleImgSize = singleImgSize;
    }

    /**
     * 设置最大图片数
     *
     * @param maxSize 最大图片数
     */
    public void setMaxSize(int maxSize) {
        mMaxSize = maxSize;
    }

    public void setItemImageClickListener(ItemImageClickListener<T> itemImageViewClickListener) {
        mItemImageClickListener = itemImageViewClickListener;
    }

    public void setItemImageLongClickListener(ItemImageLongClickListener<T> itemImageViewLongClickListener) {
        mItemImageLongClickListener = itemImageViewLongClickListener;
    }

    public T getImgDataItem(int position) {
        return mImgDataList != null && position < mImgDataList.size() ? mImgDataList.get(position) : null;
    }

}
