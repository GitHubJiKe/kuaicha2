package com.ypf.kuaicha.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class ToastUtil {
    public static void showToast(int id) {
        String content = StringUtil.getString(id);
        Toast.makeText(TApplication.instance, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 将字符串添加到剪贴板
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void addToClipboardWithoutToast(Context context, String msg) {
        ClipData clip = ClipData.newPlainText("simple text", msg);
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(clip);
        showToast(R.string.copy);

    }
}
