package com.ypf.kuaicha.util;

import android.os.Environment;

import java.io.File;

/**
 * 存储路径相关配置
 */
public class StorageConfig {
    public static final String TAG = "StorageConfig";
    /** SD卡的目录 */
    public static String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    /** 应用的根目录 */
    public static String ROOT = "kuaicha";
    /** 根目录 */
    public static String defaultRootPath = externalStoragePath.concat("/").concat(ROOT).concat("/");
    /** 列表缓存目录 */
    public static String defaultCachePath = defaultRootPath.concat("/cache/");
    /** 图片缓存目录 */
    public static String defaultImagePath = defaultRootPath.concat("/image/");
    /** Sqlite数据库文件位置 */
    public static String defaultSqliteDbPath = defaultRootPath.concat("/sqlite/");

    /** 创建基础文件夹 */
    public static void mkDirs() {
        //根目录
        File defaultRoot = new File(defaultRootPath);
        if (!defaultRoot.exists()) {
            defaultRoot.mkdirs();
        }
        //列表缓存目录
        File defaultCache = new File(defaultCachePath);
        if (!defaultCache.exists()) {
            defaultCache.mkdirs();
        }
        //图片缓存目录
        File defaultImage = new File(defaultImagePath);
        if (!defaultImage.exists()) {
            defaultImage.mkdirs();
        }
        //Sqlite数据库文件位置
        File defaultSqliteDb = new File(defaultSqliteDbPath);
        if (!defaultSqliteDb.exists()) {
            defaultSqliteDb.mkdirs();
        }
    }
}
