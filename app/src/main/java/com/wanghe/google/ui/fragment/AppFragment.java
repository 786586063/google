package com.wanghe.google.ui.fragment;

import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.protocol.AppProcotol;
import com.wanghe.google.ui.adapter.MyBaseAdapter;
import com.wanghe.google.ui.holder.AppHolder;
import com.wanghe.google.ui.holder.BaseHolder;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.ui.view.MylistView;
import com.wanghe.google.utils.UIUtils;

import android.view.View;

import java.util.ArrayList;

/**
 * 应用
 */
public class AppFragment extends BaseFragment {

	ArrayList<AppInfo> data;


	//只有成功才走此方法
	@Override
	public View onCreateSuccessView() {
		MylistView view = new MylistView(UIUtils.getContext());
		view.setAdapter(new AppAdapter(data));
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {

		AppProcotol procotol = new AppProcotol();

		data = procotol.getData(0);
		return check(data);

	}


	class AppAdapter extends MyBaseAdapter<AppInfo> {

		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder() {
			return new AppHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {

			AppProcotol procotol = new AppProcotol();

			ArrayList<AppInfo> moreData = procotol.getData(getListSize());

			return moreData;
		}


	}
}
