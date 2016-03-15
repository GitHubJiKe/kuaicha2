package com.ypf.kuaicha.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.bean.Company;
import com.ypf.kuaicha.util.FrescoUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class CompanyAdapter extends BaseAdapter {
    String SF = "http://static.wolongge.com/uploadfiles/company/8a0b0a107a1d3543fd22e9591ba4601f.jpg";
    String STO = "http://img2.imgtn.bdimg.com/it/u=2121941983,3581550185&fm=21&gp=0.jpg";
    String YTO = "http://img0.imgtn.bdimg.com/it/u=3895602202,994549110&fm=21&gp=0.jpg";
    String YD = "http://img2.imgtn.bdimg.com/it/u=2217172167,2886716349&fm=21&gp=0.jpg";
    String TT = "http://img2.imgtn.bdimg.com/it/u=1972996361,419396913&fm=21&gp=0.jpg";
    String EMS = "http://img4.imgtn.bdimg.com/it/u=3751288478,3144734532&fm=21&gp=0.jpg";
    String ZTO = "http://img1.imgtn.bdimg.com/it/u=1491977664,1320203113&fm=21&gp=0.jpg";
    String HTO = "http://pic39.nipic.com/20140315/15510303_162332818000_2.jpg";
    String QF = "http://img5.imgtn.bdimg.com/it/u=2932339834,1907842643&fm=21&gp=0.jpg";
    String DB = "http://img0.imgtn.bdimg.com/it/u=499471599,4084598263&fm=21&gp=0.jpg";
    private ArrayList<Company> companies = new ArrayList<>();

    public CompanyAdapter(ArrayList<Company> companies) {
        this.companies = companies;
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Object getItem(int position) {
        return companies.get(position);
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
            convertView = TApplication.getInstance().getInflater().inflate(R.layout.item_company, null);
            holder.company = (SimpleDraweeView) convertView.findViewById(R.id.company);
            holder.companyname = (TextView) convertView.findViewById(R.id.txt_company);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        FrescoUtil.showPicFromNet(holder.company, getUrl(companies.get(position).getNo()));
        holder.companyname.setText(companies.get(position).getCom().toString());
        return convertView;
    }

    private String getUrl(String no) {
        String url = "";
        switch (no) {
            case "sf":
                url = SF;
                break;
            case "sto":
                url = STO;
                break;
            case "yt":
                url = YTO;
                break;
            case "yd":
                url = YD;
                break;
            case "tt":
                url = TT;
                break;
            case "ems":
                url = EMS;
                break;
            case "zto":
                url = ZTO;
                break;
            case "ht":
                url = HTO;
                break;
            case "qf":
                url = QF;
                break;
            case "db":
                url = DB;
                break;
        }
        return url;
    }

    class ViewHolder {
        SimpleDraweeView company;
        TextView companyname;
    }
}
