package com.globalegrow.glideview.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.globalegrow.glideview.BuildConfig;

import java.io.InputStream;

/**
 * 所以你知道要创建一个额外的类去定制 Glide。
 * 下一步是要全局的去声明这个类，让 Glide 知道它应该在哪里被加载和使用。
 * Glide 会扫描 AndroidManifest.xml 为 Glide module 的 meta 声明。
 * 因此，你必须在 AndroidManifest.xml 的 <application> 标签内去声明这个SimpleGlideModule。
 *
 * @author longxl
 * @version 1.0
 * @email 343827585@qq.com
 * @date 2016/7/15
 * @since 1.0
 */
@GlideModule
public class SimpleGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.DEBUG);
        }
        builder.setDefaultRequestOptions(new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .disallowHardwareConfig());
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(3)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
        // 配置图片将缓存到SD卡
        ExternalCacheDiskCacheFactory externalCacheDiskCacheFactory = new ExternalCacheDiskCacheFactory();
        builder.setDiskCache(externalCacheDiskCacheFactory);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 配置使用OKHttp来请求网络
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
