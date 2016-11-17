package com.wanghe.google.ui.holder;

import android.view.View;

/**
 * Created by 两三人 on 2016/11/17.
 */

public abstract class BaseHolder<T> {

    private T data;
    private View mRootView; //一个item的根布局

   public BaseHolder(){

       mRootView = initView();
       //打一个tag
       mRootView.setTag(this);
   }

    public abstract  View initView();

    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    public T getData() {
        return data;
    }

    public View getmRootView() {
        return mRootView;
    }
    //根据数据刷新界面
    public abstract void refreshView(T data);
}
