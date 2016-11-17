package com.wanghe.google.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
/**
 * android-support-v7-appcompat
 * 
 * 支持ActionBar效果, 可以兼容2.x版本, 让2.x也能够展示Actionbar效果
 * 
 * 1. 引入appcompat
 * 2. 解决support-v4冲突问题
 * 3. 继承ActionBarActivity
 * 
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    //获取到前台进程的activity
    private static Activity mForegroundActivity = null;
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        this.mForegroundActivity = this;

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.mForegroundActivity = null;
    }

    public static Activity getForegroundActivity(){
        return mForegroundActivity;
    }

}
