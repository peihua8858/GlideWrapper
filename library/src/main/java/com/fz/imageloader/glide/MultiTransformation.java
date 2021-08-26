package com.fz.imageloader.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 多种变化组合
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/5/25 11:31
 */
public class MultiTransformation<T> implements Transformation<T> {
    private Collection<Transformation<T>> transformations;

    public MultiTransformation() {
        this.transformations = new ArrayList<>();
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public MultiTransformation(@NonNull Transformation<T>... transformations) {
        if (transformations.length > 0) {
            this.transformations = new ArrayList<>(Arrays.asList(transformations));
        } else {
            this.transformations = new ArrayList<>();
        }
    }

    public MultiTransformation(@NonNull Collection<Transformation<T>> transformationList) {
        if (transformationList.isEmpty()) {
            throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
        }
        this.transformations = transformationList;
    }

    public void addTransforms(@NonNull Transformation<T>... transformations) {
        this.transformations.addAll(Arrays.asList(transformations));
    }

    public void addTransform(@NonNull Transformation<T> transformation) {
        this.transformations.add(transformation);
    }

    public boolean isEmpty() {
        return transformations == null || transformations.isEmpty();
    }

    @NonNull
    @Override
    public Resource<T> transform(@NonNull Context context, @NonNull Resource<T> resource, int outWidth, int outHeight) {
        Resource<T> previous = resource;
        for (Transformation<T> transformation : transformations) {
            Resource<T> transformed = transformation.transform(context, previous, outWidth, outHeight);
            if (previous != null && !previous.equals(resource) && !previous.equals(transformed)) {
                previous.recycle();
            }
            previous = transformed;
        }
        return previous;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MultiTransformation) {
            MultiTransformation<?> other = (MultiTransformation<?>) o;
            return transformations.equals(other.transformations);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return transformations.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        for (Transformation<T> transformation : transformations) {
            transformation.updateDiskCacheKey(messageDigest);
        }
    }
}
