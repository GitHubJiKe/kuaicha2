package com.ypf.kuaicha.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkland.sdk.android.DataCallBack;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.activity.LoginActivity;
import com.ypf.kuaicha.adapter.RecordAdapter;
import com.ypf.kuaicha.bean.CompanyDao;
import com.ypf.kuaicha.bean.DetialInfo;
import com.ypf.kuaicha.bean.Result;
import com.ypf.kuaicha.bean.ResultRoot;
import com.ypf.kuaicha.bean.ResultSaveModel;
import com.ypf.kuaicha.dialog.LoadingDialog;
import com.ypf.kuaicha.dialog.TipDialog;
import com.ypf.kuaicha.http.GsonTools;
import com.ypf.kuaicha.http.HttpHelper;
import com.ypf.kuaicha.util.BitmapUtil;
import com.ypf.kuaicha.util.DialogUtil;
import com.ypf.kuaicha.util.ForeTask;
import com.ypf.kuaicha.util.GotoActivityUtil;
import com.ypf.kuaicha.util.NetWorkStateUtil;
import com.ypf.kuaicha.util.PreferenceHelper;
import com.ypf.kuaicha.util.ShareUtil;
import com.ypf.kuaicha.util.StorageConfig;
import com.ypf.kuaicha.util.StringUtil;
import com.ypf.kuaicha.util.ToastUtil;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class RecordFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView lv_record;
    private TextView title;
    private View defaultpic;
    private List<Result> results = new ArrayList<>();
    private RecordAdapter adapter;
    private static boolean hasRegister = false;
    private Button btn_share;
    private ImageButton btn_back;
    private String share_url = "http://www.wandoujia.com/apps/com.ypf.kuaicha";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_record, null);
        if (!hasRegister) {
            EventBus.getDefault().register(this);
            hasRegister = true;
        }
        btn_back = (ImageButton) view.findViewById(R.id.ib_back);
        btn_back.setVisibility(View.VISIBLE);
        btn_share = (Button) view.findViewById(R.id.btn_sure);
        btn_share.setText(StringUtil.getString(R.string.support));
        btn_share.setVisibility(View.VISIBLE);
        btn_share.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (ShareUtil.isInstallWX()) {
                                                 final Bitmap qrimage = BitmapUtil.createQRImage(share_url);
                                                 final String qrImagePath = StorageConfig.defaultImagePath + File.separator + String.valueOf(System.currentTimeMillis())
                                                         + "share.jpg";
                                                 new ForeTask() {
                                                     @Override
                                                     protected void runInBackground() {
                                                         Log.d("TAG", "mbitmap.size = " + qrimage.getByteCount());
                                                         BitmapUtil.saveBitmapToDisk(qrImagePath, qrimage);
                                                         BitmapUtil.insertImageToPhotoAlbum(getActivity(), qrImagePath);
                                                     }

                                                     @Override
                                                     protected void runInForeground() {
                                                         final TipDialog tipDialog = new TipDialog(getContext());
                                                         tipDialog.setContent(StringUtil.getString(R.string.support));
                                                         tipDialog.setTitle(StringUtil.getString(R.string.moreknow));
                                                         tipDialog.setLeftBtnText(StringUtil.getString(R.string.share));
                                                         tipDialog.setRightBtnText(StringUtil.getString(R.string.sharetofri));
                                                         tipDialog.show();
                                                         tipDialog.setOnBtnClickListener(new TipDialog.OnDialogBtnClickListener() {
                                                             @Override
                                                             public void onLeftBtnClicked(TipDialog dialog) {
                                                                 boolean toWXFriend = ShareUtil.shareToWXFriend(qrImagePath, 1);
                                                                 if (toWXFriend) {
                                                                     tipDialog.dismiss();
                                                                 } else {
                                                                     ToastUtil.showToast(R.string.sharefail);
                                                                     tipDialog.dismiss();
                                                                 }
                                                             }

                                                             @Override
                                                             public void onRightBtnClicked(TipDialog dialog) {
                                                                 boolean toWXFriend = ShareUtil.shareToWXFriend(qrImagePath, 0);
                                                                 if (toWXFriend) {
                                                                     tipDialog.dismiss();
                                                                 } else {
                                                                     ToastUtil.showToast(R.string.sharefail);
                                                                     tipDialog.dismiss();
                                                                 }
                                                             }
                                                         });
                                                     }
                                                 }.execute();

                                             } else {
                                                 ToastUtil.showToast(R.string.noWX);
                                             }

                                         }

                                     }

        );
        defaultpic = view.findViewById(R.id.defaultpic);
        lv_record = (ListView) view.findViewById(R.id.lv_record);
        lv_record.setEmptyView(defaultpic);
        title = (TextView) view.findViewById(R.id.txt_title);
        title.setText(StringUtil.getString(R.string.record));
        try

        {
            results = CompanyDao.findAll();
            LogUtil.d("results1 = " + results);
        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }

        if (results == null || results.size() < 0)

        {

        } else

        {
            adapter = new RecordAdapter(results);
            lv_record.setAdapter(adapter);
        }

        setListener();

        LogUtil.d("记录");
        return view;
    }

    private void setListener() {
        lv_record.setOnItemClickListener(this);
        lv_record.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //进入详情界面
        Result result1 = results.get(position);
        if (result1.getStatus() == 1) {
            String s = PreferenceHelper.ins().getStringShareData(result1.getNo(), "");
            Log.d("TAG", s);
            if (!TextUtils.isEmpty(s)) {
                ResultRoot resultRoot = GsonTools.changeGsonToBean(s, ResultRoot.class);
                Result result = resultRoot.getResult();
                ArrayList<DetialInfo> list = result.getList();
                GotoActivityUtil.gotoDetialActivity(getActivity(), result.getNo(), result.getCom(), list, result.getCompany());
                return;
            }
        } else {
            if (!NetWorkStateUtil.isNetConnected()) {
                ToastUtil.showToast(R.string.checknet);
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
                    if (resultRoot.getResultcode().compareTo("200") == 0 && resultRoot.getError_code() == 0) {
                        Result result = resultRoot.getResult();
                        if (result.getStatus() == 1) {//无需更新
                            try {
                                CompanyDao.updateResult(result);
                                PreferenceHelper.ins().storeShareStringData(result.getNo(), s);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                        ToastUtil.showToast(R.string.noinfo);
                    }
                }

                @Override
                public void onFinish() {
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailure(int i, String s, Throwable throwable) {
                    ToastUtil.showToast(R.string.searchfail);
                    loadingDialog.dismiss();
                    LogUtil.d("s = " + s + "throwable = " + throwable);
                }
            }, result1.getCom(), result1.getNo());
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        //弹框删除
        DialogUtil.showTipDialog(getContext(), StringUtil.getString(R.string.delete), "", StringUtil.getString(R.string.sure), StringUtil.getString(R.string.global_cancel), new TipDialog.OnDialogBtnClickListener() {
            @Override
            public void onLeftBtnClicked(TipDialog dialog) {
                try {
                    CompanyDao.deleteResult(results.get(position).getNo());
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRightBtnClicked(TipDialog dialog) {
                dialog.dismiss();
            }
        });
        return true;
    }


    public void onEventMainThread(ResultSaveModel resultSaveModel) {
        if (resultSaveModel != null) {
            if (resultSaveModel.isSaveOK()) {
                LogUtil.d("刷新数据");
                try {
                    results = CompanyDao.findAll();
                    LogUtil.d("results2 = " + results);
                    if (results == null || results.size() < 0) {

                    } else {
                        adapter = new RecordAdapter(results);
                        lv_record.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onDestroy() {
        hasRegister = false;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
