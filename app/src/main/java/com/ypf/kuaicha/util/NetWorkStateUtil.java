package com.ypf.kuaicha.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ypf.kuaicha.TApplication;

/**
 * Created by Administrator on 2016/3/4.
 */
public class NetWorkStateUtil {
    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetConnected() {
        ConnectivityManager cm = (ConnectivityManager) TApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测wifi是否连接
     *
     * @return
     */
    public static boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) TApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测3G是否连接
     *
     * @return
     */
    public static boolean is3gConnected() {
        ConnectivityManager cm = (ConnectivityManager) TApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }
}
