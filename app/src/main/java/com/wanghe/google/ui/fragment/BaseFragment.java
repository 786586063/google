package com.wanghe.google.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.utils.UIUtils;

import java.util.List;


public abstract class BaseFragment extends Fragment {

	private LoadingPage mLoadingPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 使用textview显示当前类的类名
		// TextView view = new TextView(UIUtils.getContext());
		// view.setText(getClass().getSimpleName());
		mLoadingPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View onCreateSuccessView() {
				// 注意:此处一定要调用BaseFragment的onCreateSuccessView, 否则栈溢出
				return BaseFragment.this.onCreateSuccessView();
			}

			@Override
			public ResultState onLoad() {
				return BaseFragment.this.onLoad();
			}

		};

		return mLoadingPage;
	}

	// 加载成功的布局, 必须由子类来实现
	public abstract View onCreateSuccessView();

	// 加载网络数据, 必须由子类来实现
	public abstract LoadingPage.ResultState onLoad();

	// 开始加载数据
	public void loadData() {
		if (mLoadingPage != null) {
			mLoadingPage.loadData();
		}
	}
	/**
	 * 校验数据的合法性,返回相应的状态
	 * @param data
	 * @return
	 */
	public LoadingPage.ResultState check(Object data) {
		if (data != null) {
			if (data instanceof List) {//判断当前对象是否是一个集合
				List list = (List) data;
				if (!list.isEmpty()) {//数据不为空,访问成功
					return LoadingPage.ResultState.STATE_SUCCESS;
				} else {//空集合
					return LoadingPage.ResultState.STATE_EMPTY;
				}
			}
		}

		return LoadingPage.ResultState.STATE_ERROR;
	}
}
