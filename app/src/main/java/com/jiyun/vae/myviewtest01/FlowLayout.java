package com.jiyun.vae.myviewtest01;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 冯玉苗 on 2017/9/12.
 */

public class FlowLayout extends ViewGroup{


    private int mVerticalSpacing;//每个item纵向间距
    private int mHorizontalSpacing;  //每个item横向间距
    private FlowLayoutAdapter adapter;
    private MainActivity.TagCloudLayoutItemOnClick itemClickListener;


    //把super改成this,一个参数的构造方法和两个参数的构造方法
    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;//高
        int lineHeight = 0;//每一行的高度，累加至height
        int childLeft = getPaddingLeft();//在warp_content情况下，记录当前childView的左边的一个位置
        int childTop = getPaddingTop();// 在warp_content情况下，记录当前childView的上边的一个位置
        for (int i = 0; i < getChildCount(); i++) {
            //拿到index上的子view
            View childView = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //当前子空间实际占据的高度
            int childHeight = childView.getMeasuredHeight();
            //当前子空间实际占据的宽度
            int childWidth = childView.getMeasuredWidth();
            lineHeight = Math.max(childHeight, lineHeight);// 取最大值
            //如果加入当前childView，超出最大宽度，则将目前最大宽度给width，类加height 然后开启新行
            if (childWidth + childLeft + getPaddingRight() > widthSize) {
                childLeft = getPaddingLeft();// 重新开启新行，开始记录childLeft
                childTop += mVerticalSpacing + childHeight;// 叠加当前的高度
                lineHeight = childHeight;// 开启记录下一行的高度
            }else{
                //否则累加当前childView的宽度
                childLeft += childWidth + mHorizontalSpacing;
            }
        }
        height += childTop + lineHeight + getPaddingBottom();
        setMeasuredDimension(widthSize, heighMode == MeasureSpec.EXACTLY ? heighSize : height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //Layout摆放一个View的位置
        int width = r - l;
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;
        //遍历所有childView根据其宽和高，计算子控件应该出现的位置
        for (int i = 0; i < getChildCount(); i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);
            // 如果已经需要换行
            if (childLeft + childWidth + getPaddingRight() > width) {
                childLeft = getPaddingLeft();
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }

    }

    public void setAdapter(FlowLayoutAdapter adapter) {
        this.adapter = adapter;
    }

    public void setItemClickListener(MainActivity.TagCloudLayoutItemOnClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface TagItemClickListener {
        void itemClick(int position);
    }
}
