package com.globalegrow.glideview.glide;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.globalegrow.glideview.MyApplication;

/**
 * glide图片保存至SD卡
 *
 * @author longxl
 * @version 1.0
 * @email 343827585@qq.com
 * @date 2016/7/15
 * @since 1.0
 */
public class ExternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    /**
     * app缓存目录
     */
    public static final String APP_DISK_CACHE_CONFIG = "diskCache";
    /**
     * fresco图片加载缓存目录
     */
    public static final String IMAGE_DISK_CACHE_CONFIG = "imageCache";
    public static final String IMAGE_DISK_CACHE_DIR = MyApplication.getInstance().getDiskCacheDir(APP_DISK_CACHE_CONFIG, IMAGE_DISK_CACHE_CONFIG);

    public ExternalCacheDiskCacheFactory() {
        this(DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public ExternalCacheDiskCacheFactory(int diskCacheSize) {
        this(IMAGE_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalCacheDiskCacheFactory(final String diskCacheName, int diskCacheSize) {
        super(diskCacheName, diskCacheSize);
    }
}
