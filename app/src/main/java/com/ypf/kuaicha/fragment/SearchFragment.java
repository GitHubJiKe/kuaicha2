package com.ypf.kuaicha.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thinkland.sdk.android.DataCallBack;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.activity.CheckInput;
import com.ypf.kuaicha.activity.LoginActivity;
import com.ypf.kuaicha.bean.CompanyChoose;
import com.ypf.kuaicha.bean.CompanyDao;
import com.ypf.kuaicha.bean.DetialInfo;
import com.ypf.kuaicha.bean.NetWorkState;
import com.ypf.kuaicha.bean.Result;
import com.ypf.kuaicha.bean.ResultRoot;
import com.ypf.kuaicha.bean.SaoMiaoOK;
import com.ypf.kuaicha.dialog.LoadingDialog;
import com.ypf.kuaicha.http.GsonTools;
import com.ypf.kuaicha.http.HttpHelper;
import com.ypf.kuaicha.util.GotoActivityUtil;
import com.ypf.kuaicha.util.NetWorkStateUtil;
import com.ypf.kuaicha.util.PreferenceHelper;
import com.ypf.kuaicha.util.StringUtil;
import com.ypf.kuaicha.util.ToastUtil;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class SearchFragment extends Fragment implements TextWatcher, CheckInput, View.OnClickListener {
    private EditText edit_number;
    private EditText edit_company;
    private TextView title;
    private Button btn_saomiao;
    private Button btn_search;
    private ImageButton iv_back;
    private String no;
    private static String number = "";
    private static boolean hasRegister = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_search, null);
        if (!hasRegister) {
            EventBus.getDefault().register(this);
            hasRegister = true;
        }
        edit_number = (EditText) view.findViewById(R.id.edit_number);
        edit_company = (EditText) view.findViewById(R.id.edit_company);
        title = (TextView) view.findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.search));
        btn_saomiao = (Button) view.findViewById(R.id.btn_sure);
        btn_saomiao.setText(StringUtil.getString(R.string.saoma));
        btn_saomiao.setOnClickListener(this);
        iv_back = (ImageButton) view.findViewById(R.id.ib_back);
        iv_back.setVisibility(View.VISIBLE);
        btn_saomiao.setVisibility(View.VISIBLE);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        edit_company.addTextChangedListener(this);
        edit_company.setOnClickListener(this);
        edit_number.addTextChangedListener(this);
        LogUtil.d("查询");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                //扫描条形码
                GotoActivityUtil.gotoCaptureActivity(getContext());
                break;
            case R.id.edit_company:
                //进入公司选择界面
                if (!NetWorkStateUtil.isNetConnected()) {
                    ToastUtil.showToast(R.string.checknet);
                } else {
                    if (NetWorkStateUtil.is3gConnected()) {
                        ToastUtil.showToast(R.string.threeg);
                        GotoActivityUtil.gotoCompanyActivity(getActivity());
                    } else if (NetWorkStateUtil.isWifiConnected()) {
                        GotoActivityUtil.gotoCompanyActivity(getActivity());
                    }
                }

                break;
            case R.id.btn_search:
                //查询
                if (!NetWorkStateUtil.isNetConnected()) {
                    ToastUtil.showToast(R.string.checknet);
                    return;
                } else {
                    if (NetWorkStateUtil.is3gConnected()) {
                        ToastUtil.showToast(R.string.threeg);
                    } else if (NetWorkStateUtil.isWifiConnected()) {
                    }
                }
                final LoadingDialog loadingDialog = new LoadingDialog(getContext());
                loadingDialog.setContent(StringUtil.getString(R.string.searching));
                loadingDialog.setDelayedDismiss(30 * 1000);
                loadingDialog.show();
                String s = PreferenceHelper.ins().getStringShareData(edit_number.getText().toString().trim(), "");
                LogUtil.d("s=" + s);
                if (!TextUtils.isEmpty(s)) {
                    ResultRoot resultRoot = GsonTools.changeGsonToBean(s, ResultRoot.class);
                    Result result = resultRoot.getResult();
                    ArrayList<DetialInfo> list = result.getList();
                    try {
                        Result result1 = CompanyDao.findResultByNo(result.getNo());
                        if (result1 == null) {
                            result.setId(LoginActivity.name);
                            CompanyDao.save(result);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadingDialog.dismiss();
                    GotoActivityUtil.gotoDetialActivity(getActivity(), result.getNo(), result.getCom(), list, result.getCompany());
                    return;
                }
                HttpHelper.getHttpHelper().getInfo(new DataCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        LogUtil.d("s=" + s);
                        if (TextUtils.isEmpty(s)) {
                            return;
                        }
                        ResultRoot resultRoot = GsonTools.changeGsonToBean(s, ResultRoot.class);
                        if (resultRoot == null) {
                            return;
                        }
                        if (resultRoot.getResultcode().compareTo(StringUtil.getString(R.string.errorcode)) == 0 && resultRoot.getError_code() == 0) {
                            Result result = resultRoot.getResult();
                            if (result.getStatus() == 1) {//无需更新
                                PreferenceHelper.ins().storeShareStringData(result.getNo().toString(), s);
                                PreferenceHelper.ins().commit();
                            }
                            ArrayList<DetialInfo> list = result.getList();
                            if (list == null || list.size() <= 0) {
                                return;
                            }

                            LogUtil.d("LoginActivity.name = " + LoginActivity.name);
                            try {
                                Result result1 = CompanyDao.findResultByNo(result.getNo());
                                if (result1 == null) {
                                    result.setId(LoginActivity.name);
                                    CompanyDao.save(result);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            loadingDialog.dismiss();
                            GotoActivityUtil.gotoDetialActivity(getActivity(), result.getNo(), result.getCom(), list, result.getCompany());
                        } else {
                            ToastUtil.showToast(R.string.sorry3);
                        }
                    }

                    @Override
                    public void onFinish() {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {
                        ToastUtil.showToast(R.string.checknet);
                        loadingDialog.dismiss();
                        LogUtil.d("s = " + s + "throwable = " + throwable);
                    }
                }, no, edit_number.getText().toString().trim());
                break;
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
        btn_search.setEnabled(checkInput());
    }

    @Override
    public boolean checkInput() {
        if (TextUtils.isEmpty(edit_company.getText()) || TextUtils.isEmpty(edit_number.getText())) {
            return false;
        } else {
            return true;
        }
    }

    public void onEventMainThread(CompanyChoose companyChoose) {
        LogUtil.d("接受不住");
        if (companyChoose != null) {
            LogUtil.d("get name = " + companyChoose.getName().toString());
            no = companyChoose.getNo().toString();
            LogUtil.d("get no = " + no);
            edit_company.setText(companyChoose.getName().toString());
        }
    }

    public void onEventMainThread(NetWorkState netWorkState) {
        if (netWorkState != null) {
            if (netWorkState.isNetWorkOK()) {
                edit_company.setClickable(true);
            } else {
                edit_company.setClickable(false);
                btn_search.setEnabled(false);
            }
        }
    }

    public void onEventMainThread(SaoMiaoOK saoMiaoOK) {
        Log.d("TAG", "接受扫面结果= " + saoMiaoOK);
        if (saoMiaoOK != null) {
            if (saoMiaoOK.isSaoMiaoOK()) {
                number = saoMiaoOK.getResult().toString();
                Log.d("TAG", "number1=" + number);
            }

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("TAG", "number1=" + number);
        edit_number.setText(number);
        number = "";
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("莫非已经街注册");
        hasRegister = false;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
