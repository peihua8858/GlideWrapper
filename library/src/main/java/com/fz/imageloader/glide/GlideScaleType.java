package com.fz.imageloader.glide;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

/**
 * glide 缩放类型
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/2/1 10:33
 */
public enum GlideScaleType {
    /**
     * 保持原图的纵横比计算一个比例，但是也要确保原图完全放入目标视图内，
     * 至少一个轴（X或Y）将精确配合。 结果以目标视图为中心。默认属性
     *
     * @see {@link FitCenter}
     */
    FIT_CENTER(0),
    /**
     * 缩小图像均匀（保持图像的纵横比），使图像的尺寸（宽度和高度）都等于或小于视图的对应尺寸（减去填充）。
     *
     * @see {@link CenterInside}
     */
    CENTER_INSIDE(1),
    /**
     * 缩放图像（保持图像的纵横比），使图像的尺寸（宽度和高度）都等于或大于视图的相应尺寸（减去填充）。
     * 所以会占满ImageView，但是可能会显示不完全图片
     *
     * @see {@link CenterCrop}
     */
    CENTER_CROP(2),
    /**
     * 圆形图显示
     *
     * @see {@link CircleCrop}
     */
    CIRCLE_CROP(3);

    GlideScaleType(int ni) {
        nativeInt = ni;
    }

    final int nativeInt;
}
