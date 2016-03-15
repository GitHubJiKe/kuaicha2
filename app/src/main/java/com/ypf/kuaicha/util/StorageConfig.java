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
    public static String ROOT = "KuaiCha";
    /** 根目录 */
    public static String defaultRootPath = externalStoragePath.concat("/").concat(ROOT).concat("/");
    /** 列表缓存目录 */
    public static String defaultCachePath = defaultRootPath.concat("/cache/");
    /** 错误日志 目录 */
    public static String defaultErrorPath = defaultRootPath.concat("/error/");
    /** 图片缓存目录 */
    public static String defaultImagePath = defaultRootPath.concat("/image/");
    /** gif缓存目录 */
    public static String defaultGifPath = defaultRootPath.concat("/gif/");
//    /** 全屏礼物图片，游艇，烟花等处理完后的图片的本地目录 */
//    public static String defaultFullScreenGiftPath = defaultRootPath.concat("/fullscreen/");
    /** 伴奏目录 */
    public static String defaultAccompPath = defaultRootPath.concat("/accomp/");
//    /** 歌词目录 */
//    public static String defaultSDJLyricPath = defaultRootPath.concat("/accomplyric/");
    /** 歌词数据目录 */
    public static String defaultLyricPath = defaultRootPath.concat("/lyric/");
    /** drc歌词目录 */
    public static String defaultDRCPath = defaultRootPath.concat("/drc/");
//    /** 下载路径 */
//    public static String sdkDownloadLocationPath = defaultRootPath.concat("/download/");
    /** Sqlite数据库文件位置 */
    public static String defaultSqliteDbPath = defaultRootPath.concat("/sqlite/");
    /** 用户信息存储路径 */
    public static String defaultUserPath = defaultRootPath.concat("/user/");
    /** 用户下载的应用apk存储路径 */
    public static String defaultApkPath = defaultRootPath.concat("/apk/");
    /** 音频存储路径 */
    public static String defaultAudioPath = defaultRootPath.concat("/audio/");

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
        //错误日志 目录
        File defaultError = new File(defaultErrorPath);
        if (!defaultError.exists()) {
            defaultError.mkdirs();
        }
        //图片缓存目录
        File defaultImage = new File(defaultImagePath);
        if (!defaultImage.exists()) {
            defaultImage.mkdirs();
        }
        //gif缓存目录
        File defaultGif = new File(defaultGifPath);
        if (!defaultGif.exists()) {
            defaultGif.mkdirs();
        }
        //        //全屏礼物图片，游艇，烟花等处理完后的图片的本地目录
        //        File defaultFullScreenGift = new File(defaultFullScreenGiftPath);
        //        if (!defaultFullScreenGift.exists()) {
        //            defaultFullScreenGift.mkdirs();
        //        }
        //SDJ伴奏目录
        File defaultAccomp = new File(defaultAccompPath);
        if (!defaultAccomp.exists()) {
            defaultAccomp.mkdirs();
        }
//        //SDJ歌词目录
//        File defaultSDJLyric = new File(defaultSDJLyricPath);
//        if (!defaultSDJLyric.exists()) {
//            defaultSDJLyric.mkdirs();
//        }
        //歌词数据目录
        File defaultLyric = new File(defaultLyricPath);
        if (!defaultLyric.exists()) {
            defaultLyric.mkdirs();
        }
        //drc歌词目录
        File defaultDRC = new File(defaultDRCPath);
        if (!defaultDRC.exists()) {
            defaultDRC.mkdirs();
        }
//        //下载路径
//        File sdkDownloadLocation = new File(sdkDownloadLocationPath);
//        if (!sdkDownloadLocation.exists()) {
//            sdkDownloadLocation.mkdirs();
//        }
        //Sqlite数据库文件位置
        File defaultSqliteDb = new File(defaultSqliteDbPath);
        if (!defaultSqliteDb.exists()) {
            defaultSqliteDb.mkdirs();
        }
        //用户信息存储路径
        File defaultUser = new File(defaultUserPath);
        if (!defaultUser.exists()) {
            defaultUser.mkdirs();
        }
        //用户下载的应用apk存储路径
        File apkPath=new File(defaultApkPath);
        if (!apkPath.exists()) {
            apkPath.mkdirs();
        }
        
        //音频存储路径
        File audioPath=new File(defaultAudioPath);
        if (!audioPath.exists()) {
            audioPath.mkdirs();
        }
    }
}
