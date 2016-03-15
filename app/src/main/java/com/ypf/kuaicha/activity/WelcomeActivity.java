package com.ypf.kuaicha.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.util.FrescoUtil;
import com.ypf.kuaicha.util.GotoActivityUtil;
import com.ypf.kuaicha.util.StorageConfig;

public class WelcomeActivity extends AppCompatActivity {
    private SimpleDraweeView mSimpleDraweeView;
    Uri uri = Uri.parse(FrescoUtil.FrescoLocalDrawableURI + R.mipmap.welcome);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StorageConfig.mkDirs();
        TApplication.activities.add(this);
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.mSimpleDraweeView);
        FrescoUtil.showGif(uri, mSimpleDraweeView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //进入主页面
                GotoActivityUtil.gotoLoginActivity(WelcomeActivity.this);
                finish();
            }
        }, 3000);
    }
}
