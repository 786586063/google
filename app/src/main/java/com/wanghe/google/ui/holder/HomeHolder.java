package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.utils.UIUtils;

/**
 * Created by 两三人 on 2016/11/17.
 */

public class HomeHolder extends BaseHolder<AppInfo> {
    private TextView tvContent;
    @Override
    public View initView() {
        //加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //初始化控件
        tvContent = (TextView) view.findViewById(R.id.tv_list);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        tvContent.setText(data.des);
    }
}
