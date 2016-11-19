package com.wanghe.google.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 两三人 on 2016/11/19.
 */

public class MylistView extends ListView{

    public MylistView(Context context) {
        super(context);
        initView();
    }



    public MylistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MylistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setSelector(new ColorDrawable());//设置默认选择器为全透明
        this.setDivider(null); //去掉分割线
        this.setCacheColorHint(Color.TRANSPARENT);//有时候滑动listView背景会变成黑色，此方法将背景变为全透明
    }

}
