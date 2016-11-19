package com.wanghe.google.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.protocol.HomeProtocol;
import com.wanghe.google.ui.adapter.MyBaseAdapter;
import com.wanghe.google.ui.holder.BaseHolder;
import com.wanghe.google.ui.holder.HomeHolder;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.ui.view.MylistView;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;


/**
 * 首页
 * 
 */
public class HomeFragment extends BaseFragment {

	public ArrayList<AppInfo> data;

	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		MylistView view = new MylistView(UIUtils.getContext());
		view.setSelector(new ColorDrawable());//设置默认选择器为全透明
		view.setDivider(null); //去掉分割线
		view.setCacheColorHint(Color.TRANSPARENT);//有时候滑动listView背景会变成黑色，此方法将背景变为全透明

		HomeAdapter adapter = new HomeAdapter(data);
		view.setAdapter(adapter);

		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public LoadingPage.ResultState onLoad() {
		HomeProtocol homeProtocol = new HomeProtocol();

		data = homeProtocol.getData(0);

		return check(data);
	}

	class HomeAdapter extends MyBaseAdapter<AppInfo> {


		public HomeAdapter(ArrayList data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder() {
			return new HomeHolder();
		}

		//此方法在子线程调用
		@Override
		public ArrayList<AppInfo> onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();

			ArrayList<AppInfo> moreData = protocol.getData(getListSize());

			return moreData;
		}


	}




}
