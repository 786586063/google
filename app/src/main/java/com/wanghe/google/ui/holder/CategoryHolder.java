package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanghe.google.R;
import com.wanghe.google.domain.CategortInfo;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/21.
 */

public class CategoryHolder extends BaseHolder<CategortInfo> implements View.OnClickListener {

    private ImageView ivIcon1,ivIcon2,ivIcon3;
    private TextView tvName1,tvName2,tvName3;
    private LinearLayout llGrid1,llGrid2,llGrid3;

    @Override
    public View initView() {

        View view = UIUtils.inflate(R.layout.list_item_category);
        ivIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        ivIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        ivIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

        tvName1 = (TextView) view.findViewById(R.id.tv_name1);
        tvName2 = (TextView) view.findViewById(R.id.tv_name2);
        tvName3 = (TextView) view.findViewById(R.id.tv_name3);

        llGrid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        llGrid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        llGrid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);

        llGrid1.setOnClickListener(this);
        llGrid2.setOnClickListener(this);
        llGrid3.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshView(CategortInfo data) {
        if (data != null){
            tvName1.setText(data.name1);
            tvName2.setText(data.name2);
            tvName3.setText(data.name3);
            Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+data.url1).into(ivIcon1);
            Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+data.url2).into(ivIcon2);
            Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+data.url3).into(ivIcon3);
        }

    }

    @Override
    public void onClick(View v) {
        CategortInfo data = getData();
        switch (v.getId()){
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(),data.name1,Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(),data.name2,Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(),data.name3,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
