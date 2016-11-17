package com.wanghe.google.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.ui.adapter.MyBaseAdapter;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;


/**
 * 首页
 * 
 */
public class HomeFragment extends BaseFragment {

	private ArrayList<String> data;

	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
//		TextView view = new TextView(UIUtils.getContext());
//		view.setText(getClass().getSimpleName());
		ListView view = new ListView(UIUtils.getContext());
		HomeAdapter adapter = new HomeAdapter(data);
		view.setAdapter(adapter);

		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public LoadingPage.ResultState onLoad() {
		// 请求网络
		data = new ArrayList<>();
		for (int i= 0;i<20;i++){
			data.add("测试数据："+i);
		}
		return LoadingPage.ResultState.STATE_SUCCESS;
	}

	class HomeAdapter extends MyBaseAdapter<String> {


		public HomeAdapter(ArrayList data) {
			super(data);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(UIUtils.getContext(),
						R.layout.list_item_home, null);
				holder = new ViewHolder();
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_list);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvContent.setText(getItem(position));

			return convertView;
		}
	}

	static class ViewHolder {
		public TextView tvContent;
	}


}
