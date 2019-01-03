package com.globalegrow.glideview;

import android.app.Application;

import java.io.File;

public class MyApplication extends Application {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 获取app 缓存目录
     *
     * @param uniqueName
     * @return 获取到的缓存目录
     * @author dingpeihua
     * @date 2017/3/24 10:11
     * @version 1.0
     */
    public String getDiskCacheDir(String folderName, String uniqueName) {
        String cachePathFile = getDiskCacheRootDir(folderName);
        File outFilePath = new File(cachePathFile, uniqueName);
        if (!outFilePath.exists()) {
            outFilePath.mkdirs();
        }
        return outFilePath.getAbsolutePath();
    }

    public String getDiskCacheRootDir(String folderName) {
        //如果SD卡存在通过getExternalCacheDir()获取路径，
        //放在路径 /sdcard/Android/data/<application package>/cache/uniqueName
        File file = getExternalCacheDir();
        //如果SD卡不存在通过getCacheDir()获取路径，
        //放在路径 /data/data/<application package>/cache/uniqueName
        if (file == null) {
            file = getCacheDir();
        }
        File outFilePath = new File(file, folderName);
        return outFilePath.getAbsolutePath();
    }
}
