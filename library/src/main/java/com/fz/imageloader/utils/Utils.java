package com.fz.imageloader.utils;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * 图片加载工具类
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/6/17 15:13
 */
public final class Utils {
    private Utils() {
        throw new AssertionError();
    }

    /**
     * 检查请求参数选项
     * 检查请求参数选择placeholder 和size，避免因两个参数都不存在时，glide不加载图片的问题
     *
     * @param options
     * @author dingpeihua
     * @date 2019/6/17 15:13
     * @version 1.0
     */
    public static boolean checkOptions(RequestOptions options) {
        return checkPlaceholder(options) || checkSize(options);
    }

    /**
     * 检查加载占位图
     *
     * @param
     * @author dingpeihua
     * @date 2019/6/17 15:15
     * @version 1.0
     */
    public static boolean checkPlaceholder(RequestOptions requestOptions) {
        return requestOptions.getPlaceholderDrawable() != null || requestOptions.getPlaceholderId() > 0;
    }

    /**
     * 检查错误占位图
     *
     * @param
     * @author dingpeihua
     * @date 2019/6/17 15:15
     * @version 1.0
     */
    public static boolean checkErrorPlaceholder(RequestOptions requestOptions) {
        return requestOptions.getErrorPlaceholder() != null || requestOptions.getErrorId() > 0;
    }

    /**
     * 检查请求宽度和高度
     *
     * @param
     * @author dingpeihua
     * @date 2019/6/17 15:15
     * @version 1.0
     */
    public static boolean checkSize(RequestOptions requestOptions) {
        return checkSize(requestOptions.getOverrideWidth(), requestOptions.getOverrideHeight());
    }

    public static boolean checkSize(int width, int height) {
        return isDimensionValid(width) && isDimensionValid(height);
    }

    public static boolean isDimensionValid(int size) {
        return size > 0 || size == Target.SIZE_ORIGINAL;
    }
}
