package com.ypf.kuaicha.bean;

import org.xutils.common.util.LogUtil;
import org.xutils.db.sqlite.WhereBuilder;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class CompanyDao {
    public static void save(Result result) throws Exception {
        DBManager.getInstance().saveBindingId(result);
        ResultSaveModel saveModel = new ResultSaveModel();
        saveModel.setIsSaveOK(true);
        EventBus.getDefault().post(saveModel);
        LogUtil.d("save ok ");
    }

    public static void updateResult(Result result) throws Exception {
        WhereBuilder builder = WhereBuilder.b();
        builder.and("no", "=", result.getNo());
        DBManager.getInstance().update(result, builder);
        ResultSaveModel saveModel = new ResultSaveModel();
        saveModel.setIsSaveOK(true);
        EventBus.getDefault().post(saveModel);
        LogUtil.d("updateResult ok ");
    }

    public static List<Result> findAll() throws Exception {
        return DBManager.getInstance().selector(Result.class).findAll();
    }

    public static Result findResultByNo(String no) throws Exception {
        LogUtil.d("查询结果2 = " + DBManager.getInstance().selector(Result.class).where("no", "=", no).findFirst());
        return DBManager.getInstance().selector(Result.class).where("no", "=", no).findFirst();
    }

    public static void deleteResult(String no) throws Exception {
        WhereBuilder builder = WhereBuilder.b();
        builder.and("no", "=", no);
        DBManager.getInstance().delete(Result.class, builder);
        ResultSaveModel saveModel = new ResultSaveModel();
        saveModel.setIsSaveOK(true);
        EventBus.getDefault().post(saveModel);
    }
}
