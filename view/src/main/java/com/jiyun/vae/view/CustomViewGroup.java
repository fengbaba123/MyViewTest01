package com.jiyun.vae.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 冯玉苗 on 2017/9/12.
 */

public class CustomViewGroup extends ViewGroup {

    private int childHorizontalSpace;
    private int childVerticalSpace;

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup);
        if(array != null){
            childHorizontalSpace = array.getDimensionPixelSize(R.styleable.CustomViewGroup_tagHorizontalSpace, 0);
            childVerticalSpace = array.getDimensionPixelSize(R.styleable.CustomViewGroup_tagVerticalSpace, 0);
            array.recycle();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量得到模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        //字View的数量、距左边、上边的内边距
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        //遍历子View
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == GONE)
                continue;
            //测量child的宽和高
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec);
            //得到child的lp
            LayoutParams lp = childAt.getLayoutParams();
            //子空件的宽
            int childWidth = childAt.getMeasuredWidth() + childHorizontalSpace;
            //子控件的高
            int childHeight = childAt.getMeasuredHeight() + childVerticalSpace;

            if (lp != null && lp instanceof MarginLayoutParams){
                MarginLayoutParams params = (MarginLayoutParams) lp;
                childWidth += params.leftMargin + params.rightMargin;
                childHeight += params.topMargin + params.bottomMargin;
            }

            //判断是否超出最大宽度
            if(lineWidth + childWidth >sizeWidth-getPaddingLeft()-getPaddingRight()){
                width = Math.max(lineWidth, childWidth);//取最大值
                lineWidth = childWidth;//开始下一行

                height += lineHeight;//叠加高度
                lineHeight = childHeight;
                childAt.setTag(new Location(paddingLeft, paddingTop + height, childWidth + paddingLeft - childHorizontalSpace, height + childAt.getMeasuredHeight() + paddingTop));
            }else {

                childAt.setTag(new Location(lineWidth + paddingLeft, paddingTop + height, lineWidth + childWidth - childHorizontalSpace + paddingLeft, height + childAt.getMeasuredHeight() + paddingTop));
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }

        width = Math.max(width,lineWidth) + getPaddingLeft() + getPaddingRight();
        height += lineHeight;
        sizeHeight += getPaddingTop() + getPaddingBottom();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    public class Location {
        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int left;
        public int top;
        public int right;
        public int bottom;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            Location location = (Location) child.getTag();
            child.layout(location.left,location.top,location.right,location.bottom);
        }
    }
}

