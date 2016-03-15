package com.ypf.kuaicha.dialog;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.ypf.kuaicha.R;


/**
 * @Description 等待提示Dialog
 */
public class LoadingDialog extends CommonDialog {
    private TextView subtitle;//等待提示语

    private Handler mhandler = null;

    public LoadingDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
        subtitle = (TextView) findViewById(R.id.subtitle);
        mhandler = new Handler();
    }

    /**
     * 设置等待提示语
     */
    public void setContent(String content) {
        subtitle.setText(content);
    }

    /**
     * 一定时间以后取消Dialog
     */
    public void setDelayedDismiss(long time) {
        mhandler.postDelayed(new Runnable() {
            public void run() {
                if (isShowing())
                    dismiss();
            }
        }, time);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) {
            listener.onDismiss();
        }
    }

    private OnCustomDismissListener listener;

    public void setCustomDismissListener(OnCustomDismissListener listener) {
        this.listener = listener;
    }

    public interface OnCustomDismissListener {
        void onDismiss();
    }
}