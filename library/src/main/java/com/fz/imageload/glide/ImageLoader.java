package com.fz.imageload.glide;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fz.imageload.utils.UriUtil;

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
public class ImageLoader {

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

    public static class Builder {
        private Context context;
        private Fragment fragment;
        private Activity activity;
        private ImageView imageView;
        private FragmentActivity fragmentActivity;
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
        private RequestOptions defaultRequestOptions;
        private Class<?> resourceType;
        private ScaleType scaleType = null;
        private LoaderListener<?> loaderListener;
        private RoundedCornersTransformation.CornerType cornerType;
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

        public Builder() {
            defaultRequestOptions = new RequestOptions();
        }

        public Builder(Context context) {
            this.context = context;
            defaultRequestOptions = new RequestOptions();
        }

        public Builder(Activity activity) {
            this.activity = activity;
            this.context = activity;
            defaultRequestOptions = new RequestOptions();
        }

        public Builder(Fragment fragment) {
            this.fragment = fragment;
            this.context = fragment.getContext();
            defaultRequestOptions = new RequestOptions();
        }

        public Builder(FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
            this.activity = fragmentActivity;
            this.context = fragmentActivity;
            defaultRequestOptions = new RequestOptions();
        }

        public Builder load(@NonNull Object val) {
            imageUrl = val;
            return this;
        }

        public Builder scaleType(@NonNull ScaleType val) {
            scaleType = val;
            return this;
        }

        public Builder listener(@NonNull LoaderListener val) {
            loaderListener = val;
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

        public Builder transforms(@NonNull Transformation<Bitmap>... transformations) {
            defaultRequestOptions.transforms(transformations);
            return this;
        }

        public <T> Builder transform(@NonNull Class<T> resourceClass, @NonNull Transformation<T> transformation) {
            defaultRequestOptions.transform(resourceClass, transformation);
            return this;
        }

        public Builder transform(@NonNull Transformation<Bitmap> transformation) {
            defaultRequestOptions.transform(transformation);
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

        public void submit() {
            RequestOptions options = new RequestOptions();
            options.apply(defaultRequestOptions);
            if (isAutoCloneEnabled) {
                options.autoClone();
            }
            if (overrideHeight > 0 || overrideWidth > 0) {
                options.override(overrideWidth > 0 ? overrideWidth : overrideHeight,
                        overrideHeight > 0 ? overrideHeight : overrideWidth);
            }
            if (sizeMultiplier > 0) {
                options.sizeMultiplier(sizeMultiplier);
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
                options.circleCrop();
            }
            if (roundedRadius > 0) {
                options.transform(new RoundedCornersTransformation(roundedRadius, roundedMargin, cornerType));
            } else if (isGrayScale) {
                options.transform(new GrayScaleTransformation());
            } else if (isBlur) {
                options.transform(new BlurTransformation(fuzzyRadius, sampling));
            } else if (rotateDegree > 0) {
                options.transform(new RotateTransformation(rotateDegree));
            }
            options.useUnlimitedSourceGeneratorsPool(useUnlimitedSourceGeneratorsPool);
            options.onlyRetrieveFromCache(onlyRetrieveFromCache);
            options.useAnimationPool(useAnimationPool);
            if (scaleType != null) {
                switch (scaleType) {
                    case CENTER_INSIDE:
                        options.centerInside();
                        break;
                    case FIT_CENTER:
                        options.fitCenter();
                        break;
                    case CENTER_CROP:
                        options.centerCrop();
                        break;
                    default:
                        break;
                }
            }
            String url = "";
            if (imageView != null) {
                if (context == null) {
                    context = imageView.getContext();
                }
                Uri uri = null;
                if (imageUrl instanceof Integer) {
                    uri = UriUtil.getResourceUri(context,(Integer) imageUrl);
                } else if (imageUrl instanceof String) {
                    url = (String) imageUrl;
                    if (url.startsWith("http")) {
                        uri = Uri.parse(url);
                    } else {
                        uri = Uri.fromFile(new File(url));
                    }
                } else if (imageUrl instanceof File) {
                    File imageFile = ((File) imageUrl);
                    url = imageFile.getAbsolutePath();
                    uri = Uri.fromFile(imageFile);
                } else if (imageUrl instanceof Uri) {
                    uri = (Uri) imageUrl;
                }
                boolean asGif = false;
                if (!TextUtils.isEmpty(url)) {
                    //判断当前url是不是gif
                    int index = url.lastIndexOf(".");
                    if (index != -1) {
                        //有点图片地址没有后缀
                        String urlSuffix = url.substring(index);
                        asGif = ".gif".equalsIgnoreCase(urlSuffix);
                    }
                }
                if (uri != null) {
                    final RequestManager requestManager;
                    if (fragment != null) {
                        requestManager = Glide.with(fragment);
                    } else if (fragmentActivity != null) {
                        requestManager = Glide.with(fragmentActivity);
                    } else if (activity != null) {
                        requestManager = Glide.with(activity);
                    } else if (context != null) {
                        requestManager = Glide.with(context);
                    } else {
                        requestManager = Glide.with(imageView);
                    }
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    RequestListener requestListener = new DRequestListener(loaderListener, imageView, scaleType, overrideWidth, overrideHeight);
                    RequestBuilder requestBuilder;
                    if (asGif) {
                        requestBuilder = requestManager.asGif();
                    } else if (resourceType != null) {
                        requestBuilder = requestManager.as(resourceType);
                    } else {
                        requestBuilder = requestManager.asBitmap();
                    }
                    requestBuilder.load(uri).apply(options)
                            .listener(requestListener)
                            .into(imageView);
                }
            }
        }
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
        private final ScaleType scaleType;

        public DRequestListener(LoaderListener loaderListener, ImageView imageView, ScaleType scaleType, int overrideWidth, int overrideHeight) {
            this.loaderListener = loaderListener;
            this.overrideWidth = overrideWidth;
            this.overrideHeight = overrideHeight;
            this.imageView = imageView;
            this.scaleType = scaleType;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<RESOURCE> target, boolean isFirstResource) {
            if (loaderListener != null) {
                return loaderListener.onError(e);
            }
            setScaleType(imageView, scaleType);
            return false;
        }

        @Override
        public boolean onResourceReady(RESOURCE resource, Object model, Target<RESOURCE> target, DataSource dataSource, boolean isFirstResource) {
            setScaleType(imageView, scaleType);
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
                return loaderListener.onSuccess(resource, width, height);
            }
            return false;
        }

        void setScaleType(ImageView imageView, ScaleType scaleType) {
            if (scaleType == null || imageView == null) {
                return;
            }
            switch (scaleType) {
                case FIT_CENTER:
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case CENTER_INSIDE:
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    break;
                case CENTER_CROP:
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Options for scaling the bounds of an image to the bounds of this view.
     */
    public enum ScaleType {
        /**
         * Scale the image using {@link Matrix.ScaleToFit#CENTER}.
         * From XML, use this syntax:
         * <code>android:scaleType="fitCenter"</code>.
         */
        FIT_CENTER,
        /**
         * Scale the image uniformly (maintain the image's aspect ratio) so
         * that both dimensions (width and height) of the image will be equal
         * to or larger than the corresponding dimension of the view
         * (minus padding). The image is then centered in the view.
         * From XML, use this syntax: <code>android:scaleType="centerCrop"</code>.
         */
        CENTER_CROP,
        /**
         * Scale the image uniformly (maintain the image's aspect ratio) so
         * that both dimensions (width and height) of the image will be equal
         * to or less than the corresponding dimension of the view
         * (minus padding). The image is then centered in the view.
         * From XML, use this syntax: <code>android:scaleType="centerInside"</code>.
         */
        CENTER_INSIDE,
    }
}
