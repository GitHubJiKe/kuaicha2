package com.ypf.kuaicha.bean;


import com.ypf.kuaicha.util.StorageConfig;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class DBManager {
    private static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName(StorageConfig.ROOT)
            .setDbDir(new File(StorageConfig.defaultSqliteDbPath))
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                }
            });

    public static DbManager getInstance() {
        return x.getDb(daoConfig);

    }
}
