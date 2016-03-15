package com.ypf.kuaicha.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.bean.NetWorkState;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/4.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    private static final String TAG = ConnectionChangeReceiver.class.getSimpleName();
    private NetWorkState netWorkState;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "网络状态改变");

        boolean success = false;

        //获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // State state = connManager.getActiveNetworkInfo().getState();
        // 获取WIFI网络连接状态
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        // 判断是否正在使用WIFI网络
        if (NetworkInfo.State.CONNECTED == state) {
            success = true;
            netWorkState = new NetWorkState();
            netWorkState.setNetWorkOK(true);
            EventBus.getDefault().post(netWorkState);
        }
        // 获取GPRS网络连接状态
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        // 判断是否正在使用GPRS网络
        if (NetworkInfo.State.CONNECTED == state) {
            success = true;
            netWorkState = new NetWorkState();
            netWorkState.setNetWorkOK(true);
            EventBus.getDefault().post(netWorkState);
        }

        if (!success) {
            netWorkState = new NetWorkState();
            netWorkState.setNetWorkOK(false);
            EventBus.getDefault().post(netWorkState);
            ToastUtil.showToast(R.string.checknet);
        }
    }
}
