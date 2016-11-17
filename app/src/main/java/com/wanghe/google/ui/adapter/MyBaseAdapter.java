package com.wanghe.google.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by 两三人 on 2016/11/17.
 */

public class MyBaseAdapter<T> extends BaseAdapter {

    private ArrayList<T> data;
   public MyBaseAdapter(ArrayList<T> data){
        this.data = data;

   }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
