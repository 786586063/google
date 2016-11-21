package com.wanghe.google.ui.fragment;


import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.wanghe.google.http.protocol.RecommenfProcotol;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.ui.view.fly.StellarMap;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 推荐
 */
public class RecommendFragment extends BaseFragment {

	ArrayList<String> data;
	@Override
	public View onCreateSuccessView() {
		StellarMap stellar = new StellarMap(UIUtils.getContext());
		// 设置内部文字距边缘边距为10dip
		int padding = UIUtils.dip2px(10);
		stellar.setInnerPadding(padding, padding, padding, padding);
		// 设置数据源
		stellar.setAdapter(new RecommentAdapter());
		// 设定展示规则,9行6列(具体以随机结果为准)
		stellar.setRegularity(6, 9);
		// 设置默认组为第0组
		stellar.setGroup(0, true);
		return stellar;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		RecommenfProcotol procotol = new RecommenfProcotol();

		data = procotol.getData(0);
		return check(data);
	}
	class RecommentAdapter implements StellarMap.Adapter{

		//返回组的个数
		@Override
		public int getGroupCount() {
			return 2;
		}

		@Override
		public int getCount(int group) {
			int count = data.size()/getGroupCount();
			if (group == getGroupCount() - 1){
				count += data.size()%getGroupCount();
			}
			return count;
		}

		@Override
		public View getView(int group, int position, View convertView) {
			if (group > 0){ //如果发现不是第一组，需要跟新position
				position = position + getCount(group - 1 ) * group;
			}
			TextView view = new TextView(UIUtils.getContext());
			view.setText(data.get(position));
			// 设置随机文字大小
			Random random = new Random();
			int size = 16 + random.nextInt(10);// 产生16-25的随机数
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);// 以sp为单位设置文字大小

			// 设置随机文字颜色
			int r = 30 + random.nextInt(210);// 产生30-239的随机颜色, 绕过0-29,
			// 240-255的值,避免颜色过暗或者过亮
			int g = 30 + random.nextInt(210);
			int b = 30 + random.nextInt(210);
			view.setTextColor(Color.rgb(r, g, b));

			return view;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			if (!isZoomIn) {
				// 下一组
				if (group < getGroupCount() - 1) {
					return ++group;
				} else {
					return 0;// 如果没有下一页了,就跳到第一组
				}
			} else {
				// 上一组
				if (group > 0) {
					return --group;
				} else {
					return getGroupCount() - 1;// 如果没有上一页了,就跳到最后一组
				}
			}

		}
	}

}
