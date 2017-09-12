package com.jiyun.vae.myviewtest01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by 冯玉苗 on 2017/9/12.
 */

public class FlowLayoutAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public FlowLayoutAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tag, null);
            holder = new ViewHolder();
            holder.mBtnTag = (Button) convertView.findViewById(R.id.btn_tag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mBtnTag.setText(getItem(position));
        return convertView;
    }

    static class ViewHolder {
        Button mBtnTag;
    }
}