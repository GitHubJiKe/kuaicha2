package com.ypf.kuaicha.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thinkland.sdk.android.DataCallBack;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.adapter.CompanyAdapter;
import com.ypf.kuaicha.bean.Company;
import com.ypf.kuaicha.bean.CompanyChoose;
import com.ypf.kuaicha.bean.CompanyRoot;
import com.ypf.kuaicha.http.GsonTools;
import com.ypf.kuaicha.http.HttpHelper;
import com.ypf.kuaicha.util.StringUtil;
import com.ypf.kuaicha.util.ToastUtil;

import org.xutils.common.util.LogUtil;

import java.io.InputStream;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class CompanyActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title;
    private GridView gridView;
    private ImageButton iv_back;
    private Button btn_sure;
    private static final int COM = 1102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        TApplication.activities.add(this);
        initViews();
        getCompanies();
    }

    private void initViews() {
        title = (TextView) findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.postcompany));
        gridView = (GridView) findViewById(R.id.gridview);
        iv_back = (ImageButton) findViewById(R.id.ib_back);
        InputStream is = getResources().openRawResource(R.raw.chat_back);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        iv_back.setImageBitmap(mBitmap);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setVisibility(View.VISIBLE);
        btn_sure.setText(StringUtil.getString(R.string.noon));
    }

    public void getCompanies() {
        HttpHelper.getHttpHelper().getCompanys(new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                CompanyRoot companyRoot = GsonTools.changeGsonToBean(s, CompanyRoot.class);
                if (companyRoot == null) {
                    return;
                }
                if (companyRoot.getResultcode().compareTo(StringUtil.getString(R.string.errorcode)) == 0) {
                    final ArrayList<Company> companies = (ArrayList<Company>) companyRoot.getResult();
                    CompanyAdapter adapter = new CompanyAdapter(companies);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String no = companies.get(position).getNo();
                            String name = companies.get(position).getCom();
                            CompanyChoose companyChoose = new CompanyChoose();
                            companyChoose.setNo(no);
                            companyChoose.setName(name);
                            LogUtil.d("no = " + no);
                            LogUtil.d("name = " + name);
                            EventBus.getDefault().post(companyChoose);
                            finish();
                        }
                    });
                } else {
                    ToastUtil.showToast(R.string.sorry);
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                LogUtil.d("s= " + s + "th = " + throwable);
                ToastUtil.showToast(R.string.sorry2);
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
