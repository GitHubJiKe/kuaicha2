package com.ypf.kuaicha.util;

import android.widget.Toast;

import com.ypf.kuaicha.TApplication;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class ToastUtil {
    public static void showToast(int id) {
        String content = StringUtil.getString(id);
        Toast.makeText(TApplication.instance, content, Toast.LENGTH_SHORT).show();
    }
}
