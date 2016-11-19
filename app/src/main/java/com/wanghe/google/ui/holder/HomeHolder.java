package com.wanghe.google.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/17.
 */

public class HomeHolder extends BaseHolder<AppInfo> {

    private ImageView ivIcon;
    private TextView tvName;
    private RatingBar rbStar;
    private TextView tvSize;
    private TextView tvDesc;
    @Override
    public View initView() {
        //加载布局
        View view = View.inflate(UIUtils.getContext(),R.layout.list_item_home1,null);
        //初始化控件
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvDesc.setText(data.des);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        float stars = (float) data.stars;
        rbStar.setRating(stars);
        Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+data.iconUrl).into(ivIcon);



    }
}
