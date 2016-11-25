package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/24.
 */

public class DetailAppPicsHolder extends BaseHolder<AppInfo> {
    private ImageView[] mImageView;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        mImageView = new ImageView[5];
        mImageView[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        mImageView[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        mImageView[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        mImageView[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        mImageView[4] = (ImageView) view.findViewById(R.id.iv_pic5);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
            if (data!= null){
                ArrayList<String> screen = data.screen;
                for (int i = 0; i < 5; i++) {
                    if (i<screen.size()){
                        mImageView[i].setVisibility(View.VISIBLE);
                        Picasso.with(UIUtils.getContext())
                                .load(OkhttpManger.url+ "image?name=" + screen.get(i))
                                .into(mImageView[i]);
                    }else{
                        mImageView[i].setVisibility(View.GONE);
                    }
                }
            }

    }
}
