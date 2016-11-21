package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.domain.CategortInfo;
import com.wanghe.google.utils.UIUtils;

/**
 * Created by 两三人 on 2016/11/21.
 */

public class TitleHolder extends BaseHolder<CategortInfo> {
    private TextView tvTitle;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategortInfo data) {

        tvTitle.setText(data.title);
    }
}
