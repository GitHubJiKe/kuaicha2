package com.ypf.kuaicha.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.adapter.DetialInfoAdapter;
import com.ypf.kuaicha.bean.DetialInfo;
import com.ypf.kuaicha.util.FrescoUtil;
import com.ypf.kuaicha.util.StringUtil;

import java.io.InputStream;
import java.util.ArrayList;

public class DetialActivity extends AppCompatActivity {
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
    private ImageButton iv_back;
    private Button btn_sure;
    private TextView title;
    private SimpleDraweeView company;
    private TextView txt_company;
    private TextView txt_number;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);
        TApplication.activities.add(this);
        initViews();
    }

    private void initViews() {
        String no = getIntent().getStringExtra("no");
        String com = getIntent().getStringExtra("com");
        String companyname = getIntent().getStringExtra("company");
        ArrayList<DetialInfo> resultlists = (ArrayList<DetialInfo>) getIntent().getSerializableExtra("list");
        iv_back = (ImageButton) findViewById(R.id.ib_back);
        iv_back.setVisibility(View.VISIBLE);
        InputStream is = getResources().openRawResource(R.raw.chat_back);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        iv_back.setImageBitmap(mBitmap);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setVisibility(View.VISIBLE);
        btn_sure.setText(StringUtil.getString(R.string.noon));
        title = (TextView) findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.detial));
        company = (SimpleDraweeView) findViewById(R.id.company);
        FrescoUtil.showPicFromNet(company, getUrl(com));
        txt_company = (TextView) findViewById(R.id.txt_company);
        txt_company.setText(companyname + ":");
        txt_number = (TextView) findViewById(R.id.txt_number);
        txt_number.setText(no);
        listView = (ListView) findViewById(R.id.listview);
        DetialInfoAdapter adapter = new DetialInfoAdapter(resultlists);
        listView.setAdapter(adapter);
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
}
