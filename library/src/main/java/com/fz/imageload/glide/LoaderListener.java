package com.fz.imageload.glide;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

/**
 * 加载图片回调
 *
 * @author longxl
 * @version 1.0
 * @email 343827585@qq.com
 * @date 2016/7/15
 * @since 1.0
 */
public interface LoaderListener<RESOURCE> {
    /**
     * 图片加载成功
     *
     * @param bitmap
     * @param width
     * @param height
     * @return 阻止调用 {@link Target#onResourceReady(Object, Transition)}
     *      {@code target}，通常是因为侦听器想要更新{@code目标}或对象
     *      {@code target}包装自己或{@code false}允许{@link Target#onResourceReady(Object, Transition)}
     *     {@code target}上调用。
     * @author dingpeihua
     * @date 2019/1/2 17:59
     * @version 1.0
     */
    boolean onSuccess(RESOURCE bitmap, int width, int height);

    /**
     * 图片加载失败
     *
     * @param e
     * @return 阻止调用 {@link Target#onLoadFailed(Drawable)}
     *      {@code target}，通常是因为侦听器想要更新{@code目标}或对象
     *      {@code target}包装自己或{@code false}允许{@link Target#onLoadFailed(Drawable)}
     *     {@code target}上调用。
     * @author dingpeihua
     * @date 2019/1/2 17:59
     * @version 1.0
     */
    boolean onError(Exception e);
}
