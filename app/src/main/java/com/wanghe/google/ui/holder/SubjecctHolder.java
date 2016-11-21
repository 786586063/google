package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.domain.SubjectInfo;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/19.
 */

public class SubjecctHolder extends BaseHolder<SubjectInfo> {

    ImageView ivIcon;
    TextView tvDesc;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);

        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);

        tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        tvDesc.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+data.url).into(ivIcon);

    }
}
