package com.wanghe.google.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/23.
 */

public class DetailAppInfoHolder extends BaseHolder<AppInfo>{

    private TextView tvName;
    private TextView tvDownloadNum;
    private TextView tvSize;
    private TextView tvDate;
    private TextView tvVersion;
    private ImageView ivIcon;
    private RatingBar rbStar;
    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(),
                R.layout.layout_detail_appinfo, null);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvDownloadNum = (TextView) view.findViewById(R.id.tv_download_num);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        if (data != null) {
            tvName.setText(data.name);
            tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),
                    data.size));
            tvDownloadNum.setText("下载量:" + data.downloadNum);
            tvDate.setText(data.date);
            tvVersion.setText("版本:" + data.version);
            rbStar.setRating((float) data.stars);

            Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+data.iconUrl).into(ivIcon);
        }

    }
}
