package com.fz.imageloader.glide;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 矩阵变化
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/5/25 11:08
 */
public class MatrixTransformation extends BitmapTransformation {

    private float[] values;

    public MatrixTransformation(float[] values) {
        this.values = values;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();
        matrix.setValues(values);
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("MatrixTransformation").getBytes());
    }

}