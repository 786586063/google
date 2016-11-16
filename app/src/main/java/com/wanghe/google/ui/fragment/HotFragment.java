package com.wanghe.google.ui.fragment;


import android.view.View;

import com.wanghe.google.ui.view.LoadingPage;

/**
 * 排行
 * @author Kevin
 * @date 2015-10-27
 */
public class HotFragment extends BaseFragment {

	@Override
	public View onCreateSuccessView() {
		return null;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		return LoadingPage.ResultState.STATE_SUCCESS;
	}

}
