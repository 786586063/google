package com.wanghe.google.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.wanghe.google.R;

/**
 * Created by 两三人 on 2016/11/19.
 */

public class RatioLayout extends FrameLayout {
    float ratio;
    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性值
//        attrs.getAttributeFloatValue("",ratio,-1);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
       //id自动生成
        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();//回收typedArray,提高性能
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.获取到宽度
        //2.根据宽度和比例，计算控件的高度
        //3.重新测量控件
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度值
        int widtMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度模式
        int height = MeasureSpec.getSize(heightMeasureSpec);//获取高度值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度的模式
        //宽度确定，高度不确定，ratio合法，才计算高度值
        if (widtMode == MeasureSpec.EXACTLY&&heightMode!= MeasureSpec.EXACTLY&&ratio>0){
            //图片宽度 = 控件宽度 - 左侧内边距 - 右侧内边距
            int imageWidth = width - getPaddingLeft() - getPaddingRight();
            //图片高度 = 图片宽度/宽高比例
            int imageHeight = (int) (imageWidth/ratio + 0.5f);
            //控件高度 = 图片高度 + 上下内边距
            height = imageHeight + getPaddingBottom() + getPaddingTop();
            heightMeasureSpec  = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
