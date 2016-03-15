package com.ypf.kuaicha.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.bean.Result;
import com.ypf.kuaicha.util.StringUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class RecordAdapter extends BaseAdapter {
    private List<Result> results;

    public RecordAdapter(List<Result> results) {
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
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
            convertView = TApplication.getInstance().getInflater().inflate(R.layout.item_record, null);
            holder.txt_company = (TextView) convertView.findViewById(R.id.txt_company);
            holder.txt_number = (TextView) convertView.findViewById(R.id.txt_number);
            holder.txt_finish = (TextView) convertView.findViewById(R.id.txt_finish);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Result result = results.get(position);
        holder.txt_company.setText(result.getCompany());
        holder.txt_number.setText(result.getNo());
        if (result.getStatus() != 1) {
            holder.txt_finish.setText(StringUtil.getString(R.string.unfinish));
            holder.txt_finish.setTextColor(Color.RED);
        } else {
            holder.txt_finish.setText(StringUtil.getString(R.string.finish));
        }
        return convertView;
    }

    public void refreshData(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView txt_company;
        TextView txt_number;
        TextView txt_finish;
    }
}
