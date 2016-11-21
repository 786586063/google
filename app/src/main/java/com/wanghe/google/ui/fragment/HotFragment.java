package com.wanghe.google.ui.fragment;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wanghe.google.http.protocol.HotProcotol;
import com.wanghe.google.ui.view.FlowLayout;
import com.wanghe.google.ui.view.LoadingPage;
import com.wanghe.google.utils.DrawableUtils;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 排行
 */
public class HotFragment extends BaseFragment {

	ArrayList<String> data;
	@Override
	public View onCreateSuccessView() {
		int padding = UIUtils.dip2px(10);
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		scrollView.setPadding(padding,padding,padding,padding);
		//初始化自定义控件
		FlowLayout flow = new FlowLayout(UIUtils.getContext());
		//水平间距
		flow.setHorizontalSpacing(UIUtils.dip2px(6));
		//竖直间距
		flow.setVerticalSpacing(UIUtils.dip2px(8));
		//根据接口返回的数据个数，动态添加TextView
		for (final String str:data) {
			TextView view = new TextView(UIUtils.getContext());
			view.setText(str);
			view.setTextColor(Color.WHITE);
			view.setGravity(Gravity.CENTER);
			view.setPadding(padding,padding,padding,padding);
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			//设置随机文字的颜色
			Random random = new Random();
			int r = 30 + random.nextInt(210);
			int g = 30 + random.nextInt(210);
			int b = 30 + random.nextInt(210);

			int color = 0xffcecece ;//按下后的背景色
			//根据默认颜色和按下的颜色，生成圆角矩形的状态选择器
			Drawable selector = DrawableUtils.getStateListDrawable(Color.rgb(r, g, b), color, UIUtils.dip2px(6));
			view.setBackgroundDrawable(selector);
			//必须设置点击事件，TextView按下后的颜色才会变化
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(),str,Toast.LENGTH_SHORT).show();
				}
			});
			//给自定义控件添加view对象
			flow.addView(view);
		}
		scrollView.addView(flow);

		return scrollView;
	}

	@Override
	public LoadingPage.ResultState onLoad() {

		HotProcotol procotol = new HotProcotol();
		data = procotol.getData(0);
		return check(data);
	}

}
