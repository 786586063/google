package com.wanghe.google.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

public class DrawableUtils {
	/**
	 * 生成图像的工具类
	 * @param rgb 颜色
	 * @param radius 圆角半径
     * @return
     */

	public static Drawable getGradientDrawable(int rgb,int radius){
		//初始化对象
		GradientDrawable drawable = new GradientDrawable();
		//矩形类型
		drawable.setGradientType(GradientDrawable.RECTANGLE);
        //设置颜色
        drawable.setColor(rgb);
        //设置圆角半径
        drawable.setCornerRadius(radius);

		return drawable;
	}

	/**
	 * 创建一个图片选择器
	 * @param normalState  普通状态的图片
	 * @param pressedState 按压状态的图片
	 */
	public static Drawable getStateListDrawable(Drawable normalState, Drawable pressedState) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressedState);
		bg.addState(new int[]{android.R.attr.state_enabled}, normalState);
		bg.addState(new int[]{}, normalState);
		return bg;
	}
    public static Drawable getStateListDrawable(int noormalColor,int pressedColor,int radius){
        Drawable normal = getGradientDrawable(noormalColor, radius);
        Drawable pressed = getGradientDrawable(pressedColor, radius);
        return getStateListDrawable(normal,pressed);
    }

	/** 获取图片的大小 */
	public static int getDrawableSize(Drawable drawable) {
		if (drawable == null) {
			return 0;
		}
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		} else {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	}
}
