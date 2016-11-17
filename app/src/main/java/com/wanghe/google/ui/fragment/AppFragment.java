package com.wanghe.google.ui.fragment;

import com.wanghe.google.ui.view.LoadingPage;

import android.view.View;

/**
 * 应用
 */
public class AppFragment extends BaseFragment {

	//只有成功才走此方法
	@Override
	public View onCreateSuccessView() {
		return null;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		return LoadingPage.ResultState.STATE_ERROR;
	}

}
