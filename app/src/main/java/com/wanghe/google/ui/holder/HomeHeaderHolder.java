package com.wanghe.google.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wanghe.google.R;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/21.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private ArrayList<String> mList;
    private LinearLayout mIndicator;
    private ViewPager mViewPager;
    private int mPreviousPos;

    @Override
    public View initView() {
        //头布局的跟布局
        RelativeLayout header = new RelativeLayout(UIUtils.getContext());
        //根布局布局参数
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                UIUtils.getDimens(R.dimen.list_header_height));
        header.setLayoutParams(params);

        //初始化ViewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        mViewPager.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        //将ViewPager添加到header住布局上
        header.addView(mViewPager);

        //页码指示器

        mIndicator = new LinearLayout(UIUtils.getContext());
        int padding =   UIUtils.dip2px(5);
        mIndicator.setPadding(padding, padding, padding, padding);
        //初始化页面指示器布局参数
        RelativeLayout.LayoutParams iParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置当前线性布局相对于父控件的位置
        iParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        iParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mIndicator.setLayoutParams(iParams);
        //将指示器的线性布局添加到跟布局
        header.addView(mIndicator);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % mList.size();
//                将当前圆点设置为选中样式
               ImageView view = (ImageView) mIndicator.getChildAt(pos);
                view.setImageResource(R.drawable.indicator_selected);

                if (pos != mPreviousPos){

                    //将上一圆点设置为默认样式
                    ImageView prView = (ImageView) mIndicator.getChildAt(mPreviousPos);
                    prView.setImageResource(R.drawable.indicator_normal);
                }

                mPreviousPos = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return header;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        mList = data;
        mViewPager.setAdapter(new MyPagerAdapter());
        mIndicator.removeAllViews();//保险起见，先清除所有的子view；

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mList.size(); i++) {
            ImageView view = new ImageView(UIUtils.getContext());
            if (i==0){
                view.setImageResource(R.drawable.indicator_selected);
            }else{
                view.setImageResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dip2px(3);//设置圆点间距
            }
            mIndicator.addView(view,params);
        }
        // 设置viewpager滑动的初始位置
        mViewPager.setCurrentItem(mList.size() * 1000);

        // 开启自动轮播效果
        new RunnableTask().start();



    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % mList.size();
            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+mList.get(pos)).into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    class RunnableTask implements Runnable {

        public void start() {
            // 移除之前遗留的任务(handler只有一个,但HomeFragment有可能多次被创建,
            // 从而导致消息被重复发送,所以需要先把之前的消息移除掉)
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            // 发送延时2秒的任务
            UIUtils.getHandler().postDelayed(this, 2000);
        }

        @Override
        public void run() {
            // 跳到viewpager下一个页面
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            // 继续发送延时两秒的任务, 形成闭环, 达到循环执行的效果
            UIUtils.getHandler().postDelayed(this, 2000);
        }

    }
}
