package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.utils.UIUtils;

/**
 * Created by 两三人 on 2016/11/17.
 */

public class MoreHolder extends BaseHolder<Integer> {
    //加载更多的几种状态
    //1.可以加载更多
    //2.加载更多失败
    //3.没有更多数据
    //用变量变量表示 , 前缀要像是，容易识别
    public static final int STATE_LOAD_MORE =1;
    public static final int STATE_LOAD_ERROR =2;
    public static final int STATE_LOAD_NONE =3;

    private LinearLayout ll_load_more;
    private TextView tv_error;

    public MoreHolder(boolean hasMore) {
        //如果有更多数据
        if (hasMore){
            setData(STATE_LOAD_MORE);
        }else{
            setData(STATE_LOAD_NONE);
        }
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        ll_load_more = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tv_error = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {

        switch (data){
            case STATE_LOAD_MORE:
                //显示加载更多
                ll_load_more.setVisibility(View.VISIBLE);
                tv_error.setVisibility(View.GONE);
                break;
            case STATE_LOAD_NONE:
                //隐藏加载更多
                ll_load_more.setVisibility(View.GONE);
                tv_error.setVisibility(View.GONE);
                break;
            case STATE_LOAD_ERROR:
                ll_load_more.setVisibility(View.GONE);
                tv_error.setVisibility(View.VISIBLE);
                break;
        }
    }


}
