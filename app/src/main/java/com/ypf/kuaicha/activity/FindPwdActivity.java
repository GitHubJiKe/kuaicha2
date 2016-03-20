package com.ypf.kuaicha.activity;

import android.content.Intent;
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
import com.ypf.kuaicha.bean.User;
import com.ypf.kuaicha.bean.UserDao;
import com.ypf.kuaicha.util.StringUtil;
import com.ypf.kuaicha.util.ToastUtil;

import org.xutils.common.util.LogUtil;

import java.io.InputStream;

public class FindPwdActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ImageButton iv_back;
    private TextView title;
    private Button btn_sure;
    private EditText edit_name;
    private Button btn_find;
    private TextView txt_pwd;
    public static final int FIND = 1101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pwdfind);
        TApplication.activities.add(this);
        initViews();
    }

    private void initViews() {
        iv_back = (ImageButton) findViewById(R.id.ib_back);
        iv_back.setVisibility(View.VISIBLE);
        InputStream is = getResources().openRawResource(R.raw.chat_back);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        iv_back.setImageBitmap(mBitmap);
        iv_back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.findpwd));
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setVisibility(View.VISIBLE);
        btn_sure.setText(StringUtil.getString(R.string.noon));
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_name.addTextChangedListener(this);
        btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(this);
        txt_pwd = (TextView) findViewById(R.id.txt_pwd);
        txt_pwd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.addToClipboardWithoutToast(FindPwdActivity.this, ((TextView) v).getText().toString());
                return true;
            }
        });
    }

    private String getName() {
        return edit_name.getText().toString();
    }

    private String getPwd() {
        return txt_pwd.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                Intent intent = new Intent();
                intent.putExtra("pwd", getPwd());
                setResult(FIND, intent);
                LogUtil.d("getPwd = " + getPwd());
                finish();
                break;
            case R.id.btn_find:
                try {
                    User user = UserDao.findUserByName(getName());
                    if (user != null) {
                        txt_pwd.setText(user.getPassword());
                        txt_pwd.setVisibility(View.VISIBLE);
                        btn_find.setEnabled(false);
                    } else {
                        ToastUtil.showToast(R.string.nobody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("pwd", getPwd());
        setResult(FIND, intent);
        LogUtil.d("getPwd = " + getPwd());
        super.onBackPressed();
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
        LogUtil.d("s=" + s);
        btn_find.setEnabled(!checkInput());
    }

    @Override
    public void finish() {
        super.finish();
        //this.overridePendingTransition(R.anim.out_anim, 0);
    }

    private boolean checkInput() {
        return TextUtils.isEmpty(edit_name.getText().toString());
    }
}
