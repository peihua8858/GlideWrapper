package com.fz.imageloader.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * 使用Glide加载并显示图片
 *
 * @author longxl
 * @version 1.0
 * @email 343827585@qq.com
 * @date 2016/7/15
 * @since 1.0
 */
public final class ImageLoader {

    public static Builder createBuilder() {
        return new Builder();
    }

    public static Builder createBuilder(Context context) {
        return new Builder(context);
    }

    public static Builder createBuilder(Activity activity) {
        return new Builder(activity);
    }

    public static Builder createBuilder(Fragment fragment) {
        return new Builder(fragment);
    }

    public static Builder createBuilder(FragmentActivity fragmentActivity) {
        return new Builder(fragmentActivity);
    }

    public final static class Builder {
        private Context context;
        private Fragment fragment;
        private Activity activity;
        private ImageView imageView;
        private float sizeMultiplier;
        private DiskCacheStrategy diskCacheStrategy;
        private Priority priority;
        private Drawable errorPlaceholder;
        private int errorId;
        private Drawable placeholderDrawable;
        private int placeholderId;
        private boolean isCacheAble = true;
        private int overrideHeight;
        private int overrideWidth;
        private Key signature;
        private Drawable fallbackDrawable;
        private int fallbackId;
        private Resources.Theme theme;
        private boolean isAutoCloneEnabled;
        private boolean useUnlimitedSourceGeneratorsPool;
        private boolean onlyRetrieveFromCache;
        private boolean useAnimationPool;
        private RequestOptions requestOptions;
        private Class<?> resourceType;
        private GlideScaleType scaleType = null;
        private LoaderListener<?> loaderListener;
        private RequestListener<?> requestListener;
        private RoundedCornersTransformation.CornerType cornerType;
        private MultiTransformation multiTransformation = new MultiTransformation<>();
        /**
         * 图片地址 包括网络地址、本地文件地址、资源id等
         */
        private Object imageUrl;
        /**
         * 圆角半径
         */
        private int roundedRadius;
        /**
         * 圆角
         */
        private int roundedMargin;
        /**
         * 是否是圆形
         */
        private boolean isCropCircle;
        /**
         * 灰度处理
         */
        private boolean isGrayScale;
        /**
         * 高斯模糊处理
         */
        private boolean isBlur;
        /**
         * 高斯模糊半径
         */
        private int fuzzyRadius;
        /**
         * 高斯模糊采样
         */
        private int sampling;
        /**
         * 旋转角度
         */
        private int rotateDegree;
        /**
         * 是否显示GIF
         */
        private boolean isShowGif;

        public Builder() {
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Activity activity) {
            this.activity = activity;
            this.context = activity;
        }

        public Builder(Fragment fragment) {
            this.fragment = fragment;
            this.context = fragment.getContext();
        }

        public Builder load(@NonNull Object val) {
            imageUrl = val;
            return this;
        }

        public Builder scaleType(@NonNull GlideScaleType val) {
            scaleType = val;
            return this;
        }

        public Builder listener(@NonNull LoaderListener val) {
            loaderListener = val;
            return this;
        }

        public Builder listener(RequestListener listener) {
            this.requestListener = listener;
            return this;
        }

        public Builder into(@NonNull ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cornerType(@NonNull RoundedCornersTransformation.CornerType cornerType) {
            this.cornerType = cornerType;
            return this;
        }

        public <T> Builder as(Class<T> clazz) {
            resourceType = clazz;
            return this;
        }

        public <T> Builder transforms(@NonNull Transformation<T>... transformations) {
            multiTransformation.addTransforms(transformations);
            return this;
        }

        public Builder sizeMultiplier(@FloatRange(from = 0, to = 1) float val) {
            sizeMultiplier = val;
            return this;
        }

        public Builder diskCacheStrategy(DiskCacheStrategy val) {
            diskCacheStrategy = val;
            return this;
        }

        public Builder priority(Priority val) {
            priority = val;
            return this;
        }

        public Builder apply(RequestOptions val) {
            requestOptions = val;
            return this;
        }

        public Builder error(Drawable val) {
            errorPlaceholder = val;
            return this;
        }

        public Builder error(int val) {
            errorId = val;
            return this;
        }

        public Builder placeholder(Drawable val) {
            placeholderDrawable = val;
            return this;
        }

        public Builder placeholder(int val) {
            placeholderId = val;
            return this;
        }

        public Builder isCacheable(boolean val) {
            isCacheAble = val;
            return this;
        }

        public Builder override(int maxWidth, int newHeight) {
            overrideHeight = newHeight;
            overrideWidth = maxWidth;
            return this;
        }

        public Builder override(int size) {
            overrideHeight = size;
            overrideWidth = size;
            return this;
        }

        public Builder signature(Key val) {
            signature = val;
            return this;
        }

        public Builder fallback(Drawable val) {
            fallbackDrawable = val;
            return this;
        }

        public Builder fallback(int val) {
            fallbackId = val;
            return this;
        }

        public Builder theme(Resources.Theme val) {
            theme = val;
            return this;
        }

        public Builder isAutoCloneEnabled(boolean val) {
            isAutoCloneEnabled = val;
            return this;
        }

        public Builder useUnlimitedSourceGeneratorsPool(boolean val) {
            useUnlimitedSourceGeneratorsPool = val;
            return this;
        }

        public Builder onlyRetrieveFromCache(boolean val) {
            onlyRetrieveFromCache = val;
            return this;
        }


        public Builder useAnimationPool(boolean val) {
            useAnimationPool = val;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setRoundedRadius(int roundedRadius) {
            this.roundedRadius = roundedRadius;
            return this;
        }

        public Builder setRoundedMargin(int roundedMargin) {
            this.roundedMargin = roundedMargin;
            return this;
        }

        public Builder grayScale() {
            this.isGrayScale = true;
            return this;
        }

        public Builder blur() {
            this.isBlur = true;
            return this;
        }

        public Builder fuzzyRadius(int fuzzyRadius) {
            this.fuzzyRadius = fuzzyRadius;
            return this;
        }

        public Builder sampling(int sampling) {
            this.sampling = sampling;
            return this;
        }

        public Builder cropCircle() {
            this.isCropCircle = true;
            return this;
        }

        public Builder rotateDegree(int rotateDegree) {
            this.rotateDegree = rotateDegree;
            return this;
        }

        public Builder showGif(boolean isShowGif) {
            this.isShowGif = isShowGif;
            return this;
        }

        @SuppressLint("CheckResult")
        @SuppressWarnings("unchecked")
        public void submit() {
            RequestOptions options = new RequestOptions();
            if (isAutoCloneEnabled) {
                options.autoClone();
            }
            if (overrideHeight > 0 || overrideWidth > 0) {
                options.override(overrideWidth > 0 ? overrideWidth : overrideHeight,
                        overrideHeight > 0 ? overrideHeight : overrideWidth);
            }
            if (sizeMultiplier > 0) {
                options = options.sizeMultiplier(sizeMultiplier);
            }
            if (diskCacheStrategy != null) {
                options.diskCacheStrategy(diskCacheStrategy);
            } else {
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
            }
            if (priority != null) {
                options.priority(priority);
            }
            if (errorPlaceholder != null) {
                options.error(errorPlaceholder);
            } else if (errorId > 0) {
                options.error(errorId);
            }
            if (requestOptions != null) {
                options.apply(requestOptions);
            }
            if (placeholderDrawable != null) {
                options.placeholder(placeholderDrawable);
            } else if (placeholderId > 0) {
                options.placeholder(placeholderId);
            }
            options.skipMemoryCache(!isCacheAble);
            if (signature != null) {
                options.signature(signature);
            }
            if (fallbackDrawable != null) {
                options.fallback(fallbackDrawable);
            }
            if (fallbackId > 0) {
                options.fallback(fallbackId);
            }
            if (theme != null) {
                options.theme(theme);
            }
            if (isCropCircle) {
                multiTransformation.addTransform(new CircleCrop());
            }
            if (roundedRadius > 0) {
                multiTransformation.addTransform(new RoundedCornersTransformation(roundedRadius, roundedMargin, cornerType));
            }
            if (isGrayScale) {
                multiTransformation.addTransform(new GrayScaleTransformation());
            }
            if (isBlur) {
                multiTransformation.addTransform(new BlurTransformation(fuzzyRadius, sampling));
            }
            if (rotateDegree > 0) {
                multiTransformation.addTransform(new RotateTransformation(rotateDegree));
            }
            options.useAnimationPool(useAnimationPool);
            if (scaleType != null) {
                switch (scaleType) {
                    case CENTER_INSIDE:
                        multiTransformation.addTransform(new CenterInside());
                        break;
                    case FIT_CENTER:
                        multiTransformation.addTransform(new FitCenter());
                        break;
                    case CENTER_CROP:
                        multiTransformation.addTransform(new CenterCrop());
                        break;
                    case CIRCLE_CROP:
                        multiTransformation.addTransform(new CircleCrop());
                        break;
                    default:
                        break;
                }
            }
            options.optionalTransform(multiTransformation);
            options.optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(multiTransformation));
            if (imageView != null && imageUrl != null) {
                if (context == null) {
                    context = imageView.getContext();
                }
                final RequestManager requestManager;
                if (fragment != null) {
                    requestManager = Glide.with(fragment);
                } else if (activity != null) {
                    requestManager = Glide.with(activity);
                } else if (context != null) {
                    requestManager = Glide.with(context);
                } else {
                    requestManager = Glide.with(imageView);
                }
                RequestBuilder requestBuilder;
                if (isShowGif) {
                    requestBuilder = requestManager.asGif();
                } else if (resourceType != null) {
                    requestBuilder = requestManager.as(resourceType);
                } else {
                    requestBuilder = requestManager.asDrawable();
                }
                if (imageUrl instanceof Integer) {
                    int resourceId = (int) imageUrl;
                    requestBuilder = requestBuilder.load(resourceId);
                    requestBuilder.apply(options);
                } else if (imageUrl instanceof String) {
                    String url = (String) imageUrl;
                    url = url.trim();
                    if (url.startsWith("http")) {
                        requestBuilder = requestBuilder.load(url);
                        requestBuilder.apply(options);
                    } else {
                        requestBuilder = requestBuilder.load(new File(url));
                        requestBuilder.apply(getFileOptions(options));
                    }
                } else if (imageUrl instanceof File) {
                    requestBuilder = requestBuilder.load((File) imageUrl);
                    requestBuilder.apply(getFileOptions(options));
                } else {
                    requestBuilder = requestBuilder.load(imageUrl);
                    requestBuilder.apply(options);
                }
                if (requestListener != null) {
                    requestBuilder.listener(requestListener);
                } else if (loaderListener != null) {
                    requestBuilder.listener(new DRequestListener<>(imageView, loaderListener, overrideWidth, overrideHeight));
                }
                requestBuilder.into(imageView);
            }
        }
    }

    /**
     * 本地图片不用使用缓存，因为有的本地图片路径写死的不会变化，
     * 使用缓存就展示一直不变，而此时图片实际data可能变了
     *
     * @param options
     * @return
     */
    static RequestOptions getFileOptions(RequestOptions options) {
        return new RequestOptions().apply(options)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    /**
     * 默认监听器
     *
     * @author dingpeihua
     * @version 1.0
     * @date 2019/1/2 17:35
     */
    static class DRequestListener<RESOURCE> implements RequestListener<RESOURCE> {
        private final LoaderListener loaderListener;
        private final int overrideHeight;
        private final int overrideWidth;
        private final ImageView imageView;

        public DRequestListener(ImageView imageView, LoaderListener loaderListener, int overrideWidth, int overrideHeight) {
            this.loaderListener = loaderListener;
            this.overrideWidth = overrideWidth;
            this.overrideHeight = overrideHeight;
            this.imageView = imageView;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<RESOURCE> target, boolean isFirstResource) {
            if (loaderListener != null) {
                return loaderListener.onError(e);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(RESOURCE resource, Object model, Target<RESOURCE> target, DataSource dataSource, boolean isFirstResource) {
            if (loaderListener != null) {
                int width = overrideWidth;
                int height = overrideHeight;
                if (resource instanceof Drawable) {
                    Drawable drawable = ((Drawable) resource);
                    width = drawable.getIntrinsicWidth();
                    height = drawable.getIntrinsicHeight();
                } else if (resource instanceof Bitmap) {
                    Bitmap drawable = ((Bitmap) resource);
                    width = drawable.getWidth();
                    height = drawable.getHeight();
                }
                if (overrideWidth > 0) {
                    setSizeByWidthAuto(imageView, width, height, overrideWidth);
                } else if (overrideHeight > 0) {
                    setSizeByHeightAuto(imageView, width, height, overrideHeight);
                }
                return loaderListener.onSuccess(resource, width, height);
            }
            return false;
        }
    }

    /**
     * 等比缩放到maxWidth宽度
     *
     * @param width
     * @param height
     * @param maxWidth
     */
    private static void setSizeByWidthAuto(View view, float width, float height, float maxWidth) {
        if (maxWidth == 0 || height == 0 || width == 0 || view == null) return;
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = (int) maxWidth;
        lp.height = Math.round(maxWidth * height / width);
    }

    /**
     * 等比缩放到maxHeight高度
     *
     * @param width
     * @param height
     * @param maxHeight
     */
    private static void setSizeByHeightAuto(View view, float width, float height, float maxHeight) {
        if (maxHeight == 0 || height == 0 || width == 0 || view == null) return;
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = (int) maxHeight;
        lp.width = Math.round(maxHeight * width / height);
    }
}
