package com.ypf.kuaicha.http;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.ypf.kuaicha.TApplication;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class HttpHelper {
    private static HttpHelper httpHelper;
    private static final String comUrl = "http://v.juhe.cn/exp/com";
    private static final String infoUrl = "http://v.juhe.cn/exp/index";

    private HttpHelper() {
    }

    public static HttpHelper getHttpHelper() {
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        return httpHelper;
    }

    public void getCompanys(DataCallBack dataCallBack) {
        JuheData.executeWithAPI(TApplication.getInstance(), 43, comUrl, JuheData.GET, null, dataCallBack);
    }

    public void getInfo(DataCallBack dataCallBack, String no, String number) {
        Parameters param = new Parameters();
        param.add("com", no);
        param.add("no", number);
        JuheData.executeWithAPI(TApplication.getInstance(), 43, infoUrl, JuheData.GET, param, dataCallBack);
    }
}
