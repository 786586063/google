package com.wanghe.google.ui.fragment;


import android.view.View;

import com.wanghe.google.domain.SubjectInfo;
import com.wanghe.google.http.protocol.SubjectProcotol;
import com.wanghe.google.ui.adapter.MyBaseAdapter;
import com.wanghe.google.ui.holder.BaseHolder;
import com.wanghe.google.ui.holder.SubjecctHolder;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.ui.view.MylistView;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;

/**
 * 专题
 */
public class SubjectFragment extends BaseFragment {

	ArrayList<SubjectInfo> data;
	@Override
	public View onCreateSuccessView() {
		MylistView view = new MylistView(UIUtils.getContext());
		view.setAdapter(new SubjectAdapter(data));
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		SubjectProcotol procotol = new SubjectProcotol();

		data = procotol.getData(0);
		return check(data);
	}
	class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder(int position) {
			return new SubjecctHolder();
		}

		@Override
		public ArrayList<SubjectInfo> onLoadMore() {
			SubjectProcotol procotol = new SubjectProcotol();
			ArrayList<SubjectInfo> moreData = procotol.getData(getListSize());
			return moreData;
		}
	}

}
