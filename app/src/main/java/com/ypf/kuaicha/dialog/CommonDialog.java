package com.ypf.kuaicha.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.ypf.kuaicha.R;


/**
 * @Description 通用Dialog的继承类
 */
public abstract class CommonDialog extends Dialog implements OnClickListener {
    public CommonDialog(Context context) {
        super(context, R.style.CommonDialog);
         
        initDialogAttrs(context);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
        
        initDialogAttrs(context);
    }
    /**
     * 初始化Dialog的属性
     */
    protected void initDialogAttrs(Context context){
        setCanceledOnTouchOutside(true);
        getWindow().getAttributes().width = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().y = 0;
        getWindow().setGravity(Gravity.CENTER_VERTICAL);
        getWindow().setAttributes(getWindow().getAttributes());
        if (context instanceof Activity){
            setOwnerActivity((Activity) context);
        }
    }
    
    @Override
    public void onClick(View v) {}
}
