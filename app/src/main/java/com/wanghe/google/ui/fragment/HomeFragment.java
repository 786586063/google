package com.wanghe.google.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.utils.UIUtils;


/**
 * 首页
 * 
 */
public class HomeFragment extends BaseFragment {

	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		TextView view = new TextView(UIUtils.getContext());
		view.setText(getClass().getSimpleName());
		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public LoadingPage.ResultState onLoad() {
		// 请求网络
		return LoadingPage.ResultState.STATE_SUCCESS;
	}

}
