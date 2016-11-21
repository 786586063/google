package com.wanghe.google.ui.fragment;

import com.wanghe.google.domain.CategortInfo;
import com.wanghe.google.http.protocol.CategoryProtocol;
import com.wanghe.google.ui.adapter.MyBaseAdapter;
import com.wanghe.google.ui.holder.BaseHolder;
import com.wanghe.google.ui.holder.CategoryHolder;
import com.wanghe.google.ui.holder.TitleHolder;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.ui.view.MylistView;
import com.wanghe.google.utils.UIUtils;

import android.view.View;

import java.util.ArrayList;

/**
 * 首页
 */
public class CategoryFragment extends BaseFragment {


	ArrayList<CategortInfo> data;
	@Override
	public View onCreateSuccessView() {
		MylistView view = new MylistView(UIUtils.getContext());
		view.setAdapter(new CategoryAdapter(data));
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		CategoryProtocol protocol = new CategoryProtocol();

		data = protocol.getData(0);
		return check(data);
	}
	class CategoryAdapter extends MyBaseAdapter<CategortInfo>{

		public CategoryAdapter(ArrayList<CategortInfo> data) {
			super(data);
		}

		//根据当前位置，返回相应的Holder对象
		@Override
		public BaseHolder<CategortInfo> getHolder(int position) {
			CategortInfo info = getItem(position);
			if (info.isTitle){
				return new TitleHolder();
			}
			return new CategoryHolder();
		}

		@Override
		public ArrayList<CategortInfo> onLoadMore() {
			return null;
		}

		@Override
		public boolean hasMore() {
			return false;
		}

		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount()+1;//在原来的基础上，新增标题栏类型
		}

		//根据位置判断当前的item的类型
		@Override
		public int getInnerType(int position) {
			CategortInfo info = getItem(position);
			if (info.isTitle){
				return super.getInnerType(position)+1;
			}else {
				return super.getInnerType(position);

			}
		}
	}


}
