package com.globalegrow.glideview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

/**
 * @author yangyong 从fresco转向glide，配置属性部分继承了fresco的属性，详见attrs.xml里面的glide属性
 * @date 2018-10-10
 */

public class CustomDraweeView extends AppCompatImageView {


    private final String TAG = "CustomDraweeView";
    private OnSizeChangeListener onSizeChangeListener;
    private RequestOptions requestOptions;
    private int fadeDuration;
    private int scaleType;
    private boolean scaleSet;
    private int placeholderImageScaleType;
    private int failureImageScaleType;
    private int radiusImage;

    public CustomDraweeView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomDraweeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setDecodeFormat(DecodeFormat decodeFormat) {
        getRequestOptions().format(decodeFormat);
    }

    public void setOnSizeChangeListener(OnSizeChangeListener onSizeChangeListener) {
        this.onSizeChangeListener = onSizeChangeListener;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        selfSetScaleType(ScaleType.FIT_CENTER);
//        TypedArray typedArray = attrs == null ? null : context.obtainStyledAttributes(attrs, R.styleable.glide);
//        if (typedArray != null) {//这些属性是为了兼容之前fresco的配置
//            fadeDuration = typedArray.getInt(R.styleable.glide_fadeDuration, 300);
//            placeholderImageScaleType = typedArray.getInt(R.styleable.glide_placeholderImageScaleType, 0);
//            failureImageScaleType = typedArray.getInt(R.styleable.glide_failureImageScaleType, 0);
//            radiusImage = typedArray.getInt(R.styleable.glide_radiusImage, 0);
//            scaleType = typedArray.getInt(R.styleable.glide_actualImageScaleType, 0);
//            int placeholderImage = typedArray.getResourceId(R.styleable.glide_placeholderImage, 0);
//            int failureImage = typedArray.getResourceId(R.styleable.glide_failureImage, 0);
//            int backgroundImage = typedArray.getResourceId(R.styleable.glide_backgroundImage, 0);
//            boolean roundAsCircle = typedArray.getBoolean(R.styleable.glide_roundAsCircle, false);
//            int actualImageResource = typedArray.getResourceId(R.styleable.glide_actualImageResource, 0);

//            typedArray.recycle();

            setScaleType();

//            if (placeholderImage != 0) {
//                setPlaceholderImage(placeholderImage);
//            }
//            if (failureImage != 0) {
//                setFailureImage(placeholderImage);
//            }
//            if (roundAsCircle) {
//                getRequestOptions().circleCrop();
//            }
//            if (backgroundImage != 0) {
//                setBackgroundResource(backgroundImage);
//            }
//            if (placeholderImage != 0) {
//                setImage(placeholderImage);
//            }
//            if (actualImageResource != 0) {
//                setImage(actualImageResource);
//            }
            if (radiusImage > 0) {//使用圆角的时候必须固定图片的宽度或者高度其中一个,不能使用wrap
                RoundedCorners roundedCorners = new RoundedCorners(radiusImage);
                getRequestOptions().transforms(new CenterInside(), roundedCorners);
            }

//        }
    }

    private synchronized RequestOptions getRequestOptions() {
        if (requestOptions == null) {
            requestOptions = new RequestOptions();
        }
        return requestOptions;
    }

    private void selfSetScaleType(ScaleType type) {
        switch (type) {
            default:
            case FIT_CENTER:
                if (getScaleType() != ScaleType.FIT_CENTER) {
                    setScaleType(ScaleType.FIT_CENTER);
                }
                break;
            case CENTER_CROP:
                if (getScaleType() != ScaleType.CENTER_CROP) {
                    setScaleType(ScaleType.CENTER_CROP);
                }
                break;
            case CENTER_INSIDE:
                if (getScaleType() != ScaleType.CENTER_INSIDE) {
                    setScaleType(ScaleType.CENTER_INSIDE);
                }
                break;
            case FIT_XY:
                if (getScaleType() != ScaleType.FIT_XY) {
                    setScaleType(ScaleType.FIT_XY);
                }
                break;
        }
    }

    public void setPlaceholderImage(int resourceId) {
        getRequestOptions().placeholder(resourceId);
    }

    public void setFailureImage(int resourceId) {
        getRequestOptions().error(resourceId);
    }

    public void setImage(Object obj) {
        setImageByWidthAuto(obj, 0);
    }

    public void setImage(Uri uri) {
        setImageByWidthAuto(uri, 0);
    }

    public void setImage(@DrawableRes int resId) {
        setImageByWidthAuto(resId, 0);
    }

    public void setImage(File file) {
        setImageByWidthAuto(file, 0);
    }

    public void setImage(String source) {
        setImageByWidthAuto(source, 0);
    }

    public void setImage(String source, ControllerListener controllerListener) {
        setImageByWidthAuto(source, 0, controllerListener);
    }

    public void setImage(File file, ControllerListener controllerListener) {
        setImageByWidthAuto(file, 0, controllerListener);
    }

    /**
     * 设置图片，等比缩放到maxWidth宽度
     *
     * @param obj
     * @param maxWidth
     */
    public void setImageByWidthAuto(Object obj, final int maxWidth) {
        setImage(obj, maxWidth, 0, null);
    }

    public void setImageByWidthAuto(final Object obj, final int maxWidth, final ControllerListener controllerListener) {
        setImage(obj, maxWidth, 0, controllerListener);
    }

    public void setImageByHeightAuto(final Object obj, final int maxHeight) {
        setImage(obj, 0, maxHeight, null);
    }

    public void setImage(final Object obj, final int maxWidth, final int maxHeight) {
        setImage(obj, maxWidth, maxHeight, null);
    }

    public void setImageByHeightAuto(final Object obj, final int maxHeight, final ControllerListener controllerListener) {
        setImage(obj, 0, maxHeight, controllerListener);
    }

    private void setScaleType(int type) {
        switch (type) {
            case 1:
                selfSetScaleType(ScaleType.FIT_CENTER);
                break;
            case 2:
                selfSetScaleType(ScaleType.CENTER_CROP);
                break;
            default:
            case 3:
                selfSetScaleType(ScaleType.CENTER_INSIDE);
                break;
            case 4:
                selfSetScaleType(ScaleType.FIT_XY);
                break;
        }
    }

    private void setScaleType() {
        setScaleType(scaleType == 0 ? 1 : scaleType);
    }

    private void setPlaceholderImageScaleType() {
        setScaleType(placeholderImageScaleType);
    }

    private void setFailureImageScaleType() {
        setScaleType(failureImageScaleType);
    }


    /**
     * 可以传递 资源ID、文件、文件路径、URL、URI
     *
     * @param obj
     * @param maxWidth
     * @param maxHeight
     * @param controllerListener
     */
    private void setImage(final Object obj, final int maxWidth, final int maxHeight, final ControllerListener controllerListener) {
        Uri uri = null;
        int resourceId = 0;
        String url = null;
        File f = null;
        if (obj != null) {
            if (obj instanceof Integer) {
                Integer resId = (Integer) obj;
                resourceId = resId;
                uri = Uri.parse("res://" + getContext().getPackageName() + "/" + resId);
            } else if (obj instanceof String) {
                String source = (String) obj;
                source = source.trim();
                //服务器导出返回了两个http域名，发现的已告知服务端，客户端先去掉一个展示
                int firstIndex = source.indexOf("http");
                int lastIndex = source.lastIndexOf("http");
                if (firstIndex != -1 && lastIndex != -1 && firstIndex != lastIndex) {
                    source = source.substring(lastIndex);
                }

                File file = new File(source);
                if (file.exists()) {
                    uri = Uri.fromFile(file);
                    f = file;
                } else {
                    url = source;
                    uri = Uri.parse(source);
                }
            } else if (obj instanceof File) {
                File file = (File) obj;
                uri = Uri.fromFile(file);
                f = file;
            } else if (obj instanceof Uri) {
                uri = (Uri) obj;
            }
        }
        if (uri == null || TextUtils.isEmpty(uri.toString())) {
            uri = Uri.parse("");
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            final int resId = resourceId;
            final Uri src = uri;
            final String net = url;
            final File ff = f;
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {//java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
                        setImage(resId, net, ff, src, maxWidth, maxHeight, controllerListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {//java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
                setImage(resourceId, url, f, uri, maxWidth, maxHeight, controllerListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setImage(int resourceId, String url, File file, Uri uri, final int maxWidth, final int maxHeight, final ControllerListener controllerListener) throws Exception {
        final long timeStart = System.currentTimeMillis();
        RequestManager requestManager;

        Activity activity = (Activity) getContext();
        if (activity == null) {
            requestManager = Glide.with(this);
        } else {
            if (activity.isFinishing()) {
                return;
            }
            requestManager = Glide.with(activity);
        }

        final Object source;
        RequestBuilder builder;
        if (resourceId != 0) {
            source = resourceId;
            builder = requestManager.load(resourceId);
        } else if (url != null) {//分开写加载，url的才能成功添加AppGlideModule配置的header
            source = url;
            builder = requestManager.load(url);
            builder.apply(getRequestOptions());
        } else if (file != null && file.exists()) {//本地图片不用使用缓存，因为有的本地图片路径写死的不会变化，使用缓存就展示一直不变，而此时图片实际data可能变了
            source = file;
            builder = requestManager.load(file);
            RequestOptions options = new RequestOptions();
            options.apply(getRequestOptions());
            options.skipMemoryCache(true);
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
            builder.apply(options);
        } else {
            source = uri;
            builder = requestManager.load(uri);
            builder.apply(getRequestOptions());
        }

        //if (fadeDuration > 0) builder.transition(withCrossFade());//不要这个，否则占位图到原图有个渐变
        try {
            builder.into(new ImageViewTarget<Drawable>(this) {

                @Override
                public void onLoadStarted(@androidx.annotation.Nullable Drawable placeholder) {
                    setPlaceholderImageScaleType();
                    super.onLoadStarted(placeholder);
                }

                @Override
                public void onLoadFailed(@androidx.annotation.Nullable Drawable errorDrawable) {
                    setFailureImageScaleType();
                    super.onLoadFailed(errorDrawable);
                    if (controllerListener != null) {
                        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                controllerListener.onFailure(source, System.currentTimeMillis() - timeStart);
                            }
                        });
                    }
                }

                @Override
                public void onResourceReady(@NonNull Drawable resource, @androidx.annotation.Nullable Transition<? super Drawable> transition) {
                    setScaleType();
                    super.onResourceReady(resource, transition);
                }

                @Override
                protected void setResource(@androidx.annotation.Nullable final Drawable resource) {
                    if (resource != null) {
                        if (controllerListener != null) {
                            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    controllerListener.onSuccess(resource, source, System.currentTimeMillis() - timeStart);
                                }
                            });
                        }
                        int imgWidth = resource.getIntrinsicWidth();
                        int imgHeight = resource.getIntrinsicHeight();
                        view.setImageDrawable(resource);
                        if (maxWidth > 0) {
                            setSizeByWidthAuto(imgWidth, imgHeight, maxWidth);
                        } else if (maxHeight > 0) {
                            setSizeByHeightAuto(imgWidth, imgHeight, maxHeight);
                        }

                        synchronized (CustomDraweeView.this) {
                            if (!scaleSet) {
                                scaleSet = true;
                                int viewWidth = getWidth();
                                int viewHeight = getHeight();
                                boolean override = false;
                                float scale = 0.95f;//为了降低内存消耗，降低一部分清晰度，最低0.8，不然比较模糊
                                if (viewWidth > 0 && viewHeight > 0 && (imgWidth > viewWidth || imgHeight > viewHeight)) {
                                    int minWidth = Math.min(imgWidth, viewWidth);
                                    int minHeight = Math.min(imgHeight, viewHeight);
                                    int nowWidth = getRequestOptions().getOverrideWidth();
                                    int nowHeight = getRequestOptions().getOverrideHeight();
                                    if (minWidth != nowWidth || minHeight != nowHeight) {
                                        int resultWidth = nowWidth > 0 ? Math.min(minWidth, nowWidth) : minWidth;
                                        int resultHeight = nowHeight > 0 ? Math.min(minHeight, nowHeight) : minHeight;
                                        getRequestOptions().override((int) (resultWidth * scale), (int) (resultHeight * scale));
                                        override = true;
                                    }
                                }
                                if (!override && imgWidth > 0 && imgHeight > 0) {
                                    getRequestOptions().override((int) (imgWidth * scale), (int) (imgHeight * scale));
                                }
                            }
                        }
                    }
                }

                @Override
                public void onLoadCleared(@androidx.annotation.Nullable Drawable placeholder) {
                    setPlaceholderImageScaleType();
                    super.onLoadCleared(placeholder);
                }
            });
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 等比缩放到maxWidth宽度
     *
     * @param width
     * @param height
     * @param maxWidth
     */
    private void setSizeByWidthAuto(int width, int height, int maxWidth) {
        if (maxWidth == 0 || height == 0 || width == 0) return;
       final ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = maxWidth;
        lp.height = (int) (Float.valueOf(maxWidth) * Float.valueOf(height) / Float.valueOf(width));
        if (onSizeChangeListener != null) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onSizeChangeListener.onSizeChanged(lp.width, lp.height);
                    }
                });
            } else {
                onSizeChangeListener.onSizeChanged(lp.width, lp.height);
            }
        }
    }

    /**
     * 等比缩放到maxHeight高度
     *
     * @param width
     * @param height
     * @param maxHeight
     */
    private void setSizeByHeightAuto(int width, int height, int maxHeight) {
        if (maxHeight == 0 || height == 0 || width == 0) return;
        final  ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = maxHeight;
        lp.width = (int) (Float.valueOf(maxHeight) * Float.valueOf(width) / Float.valueOf(height));
        if (onSizeChangeListener != null) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onSizeChangeListener.onSizeChanged(lp.width, lp.height);
                    }
                });
            } else {
                onSizeChangeListener.onSizeChanged(lp.width, lp.height);
            }
        }
    }

    public interface OnSizeChangeListener {
        void onSizeChanged(int w, int h);
    }

    public interface ControllerListener {
        /**
         * @param source 图片路径
         * @param cost   网络消耗时间
         */
        void onFailure(@androidx.annotation.Nullable Object source, long cost);

        /**
         * @param drawable
         * @param source   图片路径
         * @param cost     网络消耗时间
         */
        void onSuccess(Drawable drawable, @androidx.annotation.Nullable Object source, long cost);
    }

}
