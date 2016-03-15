package com.ypf.kuaicha.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.bean.DetialInfo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class DetialInfoAdapter extends BaseAdapter {
    private ArrayList<DetialInfo> detialInfos;

    public DetialInfoAdapter(ArrayList<DetialInfo> detialInfos) {
        this.detialInfos = detialInfos;
        Collections.reverse(this.detialInfos);
    }

    @Override
    public int getCount() {
        return detialInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return detialInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = TApplication.getInstance().getInflater().inflate(R.layout.item_detial, null);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.detial = (TextView) convertView.findViewById(R.id.detial);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        DetialInfo detialInfo = detialInfos.get(position);
        holder.time.setText(detialInfo.getDatetime());
        holder.detial.setText(detialInfo.getRemark() + detialInfo.getZone());
        return convertView;
    }

    class ViewHolder {
        TextView time;
        TextView detial;
    }
}
