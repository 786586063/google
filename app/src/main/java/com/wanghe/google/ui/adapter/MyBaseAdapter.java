package com.wanghe.google.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.wanghe.google.ui.holder.BaseHolder;
import com.wanghe.google.ui.holder.MoreHolder;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 两三人 on 2016/11/17.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    //必须重0开始
    private static    final int TYPE_NORMAL = 0; //正常布局类型
    private static final int TYPE_MORE = 1; //加载更多的布局类型

    private ArrayList<T> data;
   public MyBaseAdapter(ArrayList<T> data){
        this.data = data;

   }
    @Override
    public int getCount() {
        return data.size()+1; //增加加载更多的布局
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回布局类型的个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    //返回当前安位置应该展现哪种布局类型
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1){//最后一个
            return TYPE_MORE;
        }else {
            return getInnerType();
        }

    }
    //预留接口，以便子类可以通过预留接口改变布局
    public int getInnerType(){
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null){
            //1.加载布局文件
            //2.初始化控件findViewById
            //3.打一个标记
            //判断是否是加载更多
            if (getItemViewType(position) == TYPE_MORE){
            //加载更多布局
            holder= new MoreHolder(hasMore());

            }else{
                holder = getHolder();
            }


        }else{
            holder = (BaseHolder) convertView.getTag();
        }
        //4.根据数据刷新界面
        if (getItemViewType(position)!= TYPE_MORE){
            holder.setData(getItem(position));

        }else {
            //加载更多布局
            //一旦加载更多布局显示出来，就开始加载更多
            //只有在有更多数据的情况下才加载更多

            MoreHolder moreHolder = (MoreHolder) holder;
            if (moreHolder.getData() == MoreHolder.STATE_LOAD_MORE){
                loadMore(moreHolder);

            }
        }
        return holder.getmRootView();
    }
    public boolean hasMore(){
        return true;//默认都是有数据的
    }
    //返回当前的对象的holder，必须子类实现
    public abstract BaseHolder<T> getHolder();

    private boolean isLoadMore = false; //标记是否加载更多，防止重负加载
    //加载更多数据
    public void loadMore(final MoreHolder holder){
        if (!isLoadMore){
            isLoadMore = true;
            new Thread(){
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null){
                                //每页20条数据，如果返回的数据小于20，就认为是最后一页了
                                if (moreData.size() <20){
                                    holder.setData(MoreHolder.STATE_LOAD_NONE);
                                    Toast.makeText(UIUtils.getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                                }else{
                                    //还有更多数据
                                    holder.setData(MoreHolder.STATE_LOAD_MORE);
                                }
                                //将更多数据追加到当前集合中
                                data.addAll(moreData);
                                //刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();

                            }else{
                                //加载更多失败
                                holder.setData(MoreHolder.STATE_LOAD_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });


                }
            }.start();
        }
    }
    //加载更多数据，必须让子类实现
    public abstract ArrayList<T> onLoadMore();
    public int getListSize(){
        return data.size();
    }
}
