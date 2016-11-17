package com.wanghe.google.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wanghe.google.R;
import com.wanghe.google.ui.fragment.BaseFragment;
import com.wanghe.google.ui.fragment.FragmentFactory;
import com.wanghe.google.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private TabLayout mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerTab = (TabLayout) findViewById(R.id.tab_pager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mPagerTab.setupWithViewPager(mViewPager);
        mPagerTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        mPagerTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    /**
     * FragmentPagerAdapter是PagerAdapter的子类, 如果viewpager的页面是fragment的话,就继承此类
     */
    class MyAdapter extends FragmentPagerAdapter {

        private String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页面标题数组
        }

        // 返回页签标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        // 返回当前页面位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        // fragment数量
        @Override
        public int getCount() {
            return mTabNames.length;
        }

    }
}
