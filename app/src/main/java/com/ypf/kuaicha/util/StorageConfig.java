package com.ypf.kuaicha.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * 存储路径相关配置
 */
public class StorageConfig {
    public static final String TAG = "StorageConfig";
    /**
     * SD卡的目录
     */
    public static String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * 应用的根目录
     */
    public static String ROOT = "KuaiCha";
    /**
     * 根目录
     */
    public static String defaultRootPath = externalStoragePath.concat("/").concat(ROOT).concat("/");
    /**
     * 列表缓存目录
     */
    public static String defaultCachePath = defaultRootPath.concat("/cache/");
    /**
     * 图片缓存目录
     */
    public static String defaultImagePath = defaultRootPath.concat("/image/");
    /**
     * gif缓存目录
     */
    public static String defaultGifPath = defaultRootPath.concat("/gif/");
    /**
     * Sqlite数据库文件位置
     */
    public static String defaultSqliteDbPath = defaultRootPath.concat("/sqlite/");

    /**
     * 创建基础文件夹
     */
    public static void mkDirs() {
        //根目录
        File defaultRoot = new File(defaultRootPath);
        if (!defaultRoot.exists()) {
            defaultRoot.mkdirs();
            Log.d("TAG", "创建文件夹成功");
        }
        //列表缓存目录
        File defaultCache = new File(defaultCachePath);
        if (!defaultCache.exists()) {
            defaultCache.mkdirs();
            Log.d("TAG", "创建文件夹成功");
        }
        //图片缓存目录
        File defaultImage = new File(defaultImagePath);
        if (!defaultImage.exists()) {
            defaultImage.mkdirs();
            Log.d("TAG", "创建文件夹成功");
        }
        //gif缓存目录
        File defaultGif = new File(defaultGifPath);
        if (!defaultGif.exists()) {
            defaultGif.mkdirs();
            Log.d("TAG", "创建文件夹成功");
        }
        //Sqlite数据库文件位置
        File defaultSqliteDb = new File(defaultSqliteDbPath);
        if (!defaultSqliteDb.exists()) {
            defaultSqliteDb.mkdirs();
            Log.d("TAG", "创建文件夹成功");
        }
    }
}
