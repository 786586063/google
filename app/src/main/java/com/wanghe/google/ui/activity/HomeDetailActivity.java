package com.wanghe.google.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.protocol.HomeDetailProtocol;
import com.wanghe.google.ui.holder.DetailAppDesHolder;
import com.wanghe.google.ui.holder.DetailAppInfoHolder;
import com.wanghe.google.ui.holder.DetailAppPicsHolder;
import com.wanghe.google.ui.holder.DetailAppSafeHolder;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.utils.LogUtils;
import com.wanghe.google.utils.UIUtils;


public class HomeDetailActivity extends BaseActivity {

    private String mPackageName;
    private AppInfo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingPage loadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };
        setContentView(loadingPage);
        mPackageName = getIntent().getStringExtra("package");
        LogUtils.e(mPackageName);
        //开始加载数据
        loadingPage.loadData();
    }

    private LoadingPage.ResultState onLoad() {
        HomeDetailProtocol protocol = new HomeDetailProtocol(mPackageName);
        data = protocol.getData(0);

        if (data != null){
            return LoadingPage.ResultState.STATE_SUCCESS;
        }else {
            return LoadingPage.ResultState.STATE_ERROR;
        }

    }

    private View onCreateSuccessView() {
        View view = UIUtils.inflate(R.layout.layout_home_detail);
        FrameLayout ll_detail = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
        //应用详情页
        DetailAppInfoHolder appInfo = new DetailAppInfoHolder();
        appInfo.setData(data);
        ll_detail.addView(appInfo.getmRootView());
        //安全详情页
        FrameLayout safe_detail = (FrameLayout) view.findViewById(R.id.fl_detail_safeinfo);
        DetailAppSafeHolder safeHolder = new DetailAppSafeHolder();
        safeHolder.setData(data);
        safe_detail.addView(safeHolder.getmRootView());
        //截图详情页
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pics);
        DetailAppPicsHolder picsHolder = new DetailAppPicsHolder();
        horizontalScrollView.addView(picsHolder.getmRootView());
        picsHolder.setData(data);
        //描述详情页
        FrameLayout desDetail = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        DetailAppDesHolder desHolder = new DetailAppDesHolder();
        desDetail.addView(desHolder.getmRootView());
        desHolder.setData(data);


        return view;
    }
}
