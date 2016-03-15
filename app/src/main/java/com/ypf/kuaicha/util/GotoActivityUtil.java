package com.ypf.kuaicha.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ypf.kuaicha.activity.CaptureActivity;
import com.ypf.kuaicha.activity.CompanyActivity;
import com.ypf.kuaicha.activity.DetialActivity;
import com.ypf.kuaicha.activity.FindPwdActivity;
import com.ypf.kuaicha.activity.LoginActivity;
import com.ypf.kuaicha.activity.MainActivity;
import com.ypf.kuaicha.activity.RegisterActivity;
import com.ypf.kuaicha.bean.DetialInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class GotoActivityUtil {

    public static void gotoLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void gotoRegisterActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public static void gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void gotoFindPwdActivity(Activity context) {
        Intent intent = new Intent(context, FindPwdActivity.class);
        context.startActivityForResult(intent, FindPwdActivity.FIND);
    }

    public static void gotoCompanyActivity(Context context) {
        Intent intent = new Intent(context, CompanyActivity.class);
        context.startActivity(intent);
    }

    public static void gotoDetialActivity(Context context, String no, String com, ArrayList<DetialInfo> list, String company) {
        Intent intent = new Intent(context, DetialActivity.class);
        intent.putExtra("no", no);
        intent.putExtra("com", com);
        intent.putExtra("list", list);
        intent.putExtra("company", company);
        context.startActivity(intent);
    }

    public static void gotoCaptureActivity(Context context) {
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivity(intent);
    }
}
