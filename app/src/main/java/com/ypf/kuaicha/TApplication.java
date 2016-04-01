package com.ypf.kuaicha;

import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.thinkland.sdk.android.JuheSDKInitializer;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class TApplication extends Application {
    public static TApplication instance;
    public static ArrayList<Activity> activities = new ArrayList<>();
    private static final String APP_ID = "wx98e44cf5e92d6983";
    public static IWXAPI mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        mApi.registerApp(APP_ID);
        Fresco.initialize(getApplicationContext());
        //SDKInitializer.initialize(getApplicationContext());
        JuheSDKInitializer.initialize(getApplicationContext());
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }

    public static TApplication getInstance() {
        return instance;
    }

    public LayoutInflater getInflater() {
        return LayoutInflater.from(instance);
    }
}
