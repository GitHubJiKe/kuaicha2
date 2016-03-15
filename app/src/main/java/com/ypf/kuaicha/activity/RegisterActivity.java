package com.ypf.kuaicha.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.bean.UserSaveModel;
import com.ypf.kuaicha.bean.User;
import com.ypf.kuaicha.bean.UserDao;
import com.ypf.kuaicha.util.GotoActivityUtil;
import com.ypf.kuaicha.util.PreferenceHelper;
import com.ypf.kuaicha.util.StringUtil;
import com.ypf.kuaicha.util.ToastUtil;

import java.io.InputStream;

import de.greenrobot.event.EventBus;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, CheckInput {


    private EditText edit_name;

    private EditText edit_pwd;

    private EditText edit_pwd2;

    private ImageButton ib_back;

    private TextView title;

    private Button btn_commit;

    private Button btn_login;
    private Button btn_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        TApplication.activities.add(this);
        EventBus.getDefault().register(this);
        initViews();
    }

    private void initViews() {
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_name.addTextChangedListener(this);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        edit_pwd.addTextChangedListener(this);
        edit_pwd2 = (EditText) findViewById(R.id.edit_pwd2);
        edit_pwd2.addTextChangedListener(this);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setVisibility(View.VISIBLE);
        InputStream is = getResources().openRawResource(R.raw.chat_back);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        ib_back.setImageBitmap(mBitmap);
        ib_back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.register));
        btn_commit = (Button) findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setText("  ");
        btn_sure.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                GotoActivityUtil.gotoLoginActivity(RegisterActivity.this);
                finish();
                break;
            case R.id.btn_commit:
                //注册
                if (checkPwd()) {
                    User user = new User();
                    user.setName(getName());
                    user.setPassword(getPwd());
                    try {
                        LoginActivity.name = user.getName();
                        User user1 = UserDao.findUserByName(String.valueOf(getName()));
                        if (user1 == null) {
                            UserDao.save(user);
                        } else {
                            ToastUtil.showToast(R.string.haduser);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showToast(R.string.samepwd);
                }

                break;
            case R.id.btn_login:
                //直接登录
                PreferenceHelper.ins().storeIntShareData("name", Integer.valueOf(edit_name.getText().toString()));
                PreferenceHelper.ins().storeShareStringData("password", edit_pwd.getText().toString());
                PreferenceHelper.ins().commit();
                GotoActivityUtil.gotoMainActivity(RegisterActivity.this);
                finish();
                break;
        }
    }

    private boolean checkPwd() {
        if (getPwd().compareTo(getPwd2()) == 0) {
            return true;
        } else {
            return false;
        }
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
        btn_commit.setEnabled(checkInput());
    }

    @Override
    public boolean checkInput() {
        if (TextUtils.isEmpty(String.valueOf(getName())) || TextUtils.isEmpty(getPwd()) || TextUtils.isEmpty(getPwd2())) {
            return false;
        } else {
            return true;
        }

    }

    private int getName() {
        return Integer.valueOf(edit_name.getText().toString());
    }

    private String getPwd() {
        return edit_pwd.getText().toString();
    }

    private String getPwd2() {
        return edit_pwd2.getText().toString();
    }

    public void onEventMainThread(UserSaveModel saveModel) {
        if (saveModel != null) {
            if (saveModel.isSaveOK()) {
                ToastUtil.showToast(R.string.registersuccess);
                btn_login.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
