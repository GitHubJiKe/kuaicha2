package com.ypf.kuaicha.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.bean.User;
import com.ypf.kuaicha.bean.UserDao;
import com.ypf.kuaicha.util.GotoActivityUtil;
import com.ypf.kuaicha.util.PreferenceHelper;
import com.ypf.kuaicha.util.StringUtil;
import com.ypf.kuaicha.util.ToastUtil;

import org.xutils.common.util.LogUtil;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements TextWatcher, CheckInput, View.OnClickListener {

    private EditText edit_name;
    private EditText edit_pwd;
    private EditText edit_yanzhengma;
    private TextView txt_yanzhengma;
    private TextView title;
    private Button btn_login;
    private Button btn_register;
    private ImageButton iv_back;
    private Button btn_findpwd;
    public static int name;
    private boolean isshowToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        TApplication.activities.add(this);
        initViews();
        Log.d("TAG","onCreate");
    }

    @Override
    protected void onStart() {
        Log.d("TAG","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("TAG","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("TAG","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("TAG","onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d("TAG","onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d("TAG","onDestroy");
        super.onDestroy();
    }
    private void initViews() {
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_name.addTextChangedListener(this);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        edit_pwd.addTextChangedListener(this);
        edit_yanzhengma = (EditText) findViewById(R.id.edit_yanzhengma);
        edit_yanzhengma.addTextChangedListener(this);
        txt_yanzhengma = (TextView) findViewById(R.id.txt_yanzhengma);
        txt_yanzhengma.setText(getRandomNumber());
        txt_yanzhengma.setBackgroundColor(getRandomColor());
        txt_yanzhengma.setOnClickListener(this);
        title = (TextView) findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.login));
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        iv_back = (ImageButton) findViewById(R.id.ib_back);
        iv_back.setVisibility(View.VISIBLE);
        btn_findpwd = (Button) findViewById(R.id.btn_sure);
        btn_findpwd.setText(StringUtil.getString(R.string.findpwd));
        btn_findpwd.setVisibility(View.VISIBLE);
        btn_findpwd.setOnClickListener(this);
        name = PreferenceHelper.ins().getIntShareData("name", -1);
        String password = PreferenceHelper.ins().getStringShareData("password", "");
        if (name != -1) {
            edit_name.setText(String.valueOf(name));
        }
        edit_pwd.setText(password);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null) {
            return;
        }
        btn_login.setEnabled(checkInput());
    }

    @Override
    public boolean checkInput() {
        boolean isOK = false;
        if (TextUtils.isEmpty(edit_name.getText()) || TextUtils.isEmpty(edit_pwd.getText()) || TextUtils.isEmpty(edit_yanzhengma.getText())) {
            return isOK;
        } else {
            try {
                User user = UserDao.findUserByName(edit_name.getText().toString());
                if (user != null) {
                    isOK = true;
                } else {
                    isshowToast = true;
                    if (isshowToast) {
                        ToastUtil.showToast(R.string.nouser);
                        isshowToast = false;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isOK;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //登录
                if (checkYanZhengMa()) {
                    PreferenceHelper.ins().storeIntShareData("name", Integer.valueOf(edit_name.getText().toString()));
                    PreferenceHelper.ins().storeShareStringData("password", edit_pwd.getText().toString());
                    PreferenceHelper.ins().commit();
                    name = Integer.valueOf(edit_name.getText().toString());
                    GotoActivityUtil.gotoMainActivity(LoginActivity.this);
                    finish();
                } else {
                    ToastUtil.showToast(R.string.yzmerror);
                }
                break;
            case R.id.btn_register:
                //注册
                GotoActivityUtil.gotoRegisterActivity(LoginActivity.this);
                break;
            case R.id.txt_yanzhengma:
                txt_yanzhengma.setText(getRandomNumber());
                txt_yanzhengma.setBackgroundColor(getRandomColor());
                break;
            case R.id.btn_sure:
                GotoActivityUtil.gotoFindPwdActivity(this);
                break;
        }


    }

    private boolean checkYanZhengMa() {
        if (txt_yanzhengma.getText().toString().compareTo(edit_yanzhengma.getText().toString()) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.d("requestCode = " + requestCode);
        if (requestCode == FindPwdActivity.FIND) {
            String pwd = data.getStringExtra("pwd");
            if (!TextUtils.isEmpty(pwd)) {
                edit_pwd.setText(pwd);
            } else {
                edit_pwd.setText("");
            }
        }
    }

    private String getRandomNumber() {
        return String.valueOf(Math.random() * (10000 - 1000) + 1000).substring(0, 4);
    }

    private int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int mColor = Color.rgb(r, g, b);
        return mColor;
    }
}
