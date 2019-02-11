package com.fz.imageloader.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.request.RequestOptions;
import com.fz.imageloader.R;
import com.fz.imageloader.glide.GlideScaleType;
import com.fz.imageloader.glide.ImageLoader;
import com.fz.imageloader.glide.LoaderListener;
import com.fz.imageloader.glide.RoundedCornersTransformation;

/**
 * 封装glide图片加载处理及变换，但由于glide限制不支持多种变换组合。
 * {@link #setImageUrl(Object)}加载图片，glide 默认缩放
 * {@link #setImageUrl(Object, float)}宽和高按照给定比例缩放显示
 * {@link #setImageUrl(Object, int, int)}加载图片并重设宽度和高度
 * {@link #setImageUrl(Object, RequestOptions)}按照给定的请求选项加载图片
 * {@link #setImageUrl(Object, int, float)}按照给定的最大宽度等比缩放图片
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/2 09:50
 */
public class RatioImageView extends AppCompatImageView {
    /**
     * {@link RequestOptions#placeholderDrawable}
     */
    private Drawable placeholderDrawable;
    /**
     * {@link RequestOptions#errorPlaceholder}
     */
    private Drawable errorDrawable;
    /**
     * 圆角半径
     */
    private int roundedRadius;
    /**
     * 圆角边距
     */
    private int roundedMargin;
    /**
     * 圆角位置
     */
    private RoundedCornersTransformation.CornerType cornerType;
    /**
     * 是否需要灰度处理
     */
    private boolean isGrayScale;
    /**
     * 是否需要高斯模糊处理
     */
    private boolean isBlur;
    /**
     * 旋转角度
     */
    private int rotateDegree;
    /**
     * {@link RequestOptions#useAnimationPool}
     */
    private boolean useAnimationPool;
    /**
     * 宽度和高度
     */
    private int width, height;
    /**
     * {@link RequestOptions#sizeMultiplier}
     */
    private float sizeMultiplier = 0f;
    /**
     * 是否是圆形
     */
    private boolean isCropCircle;
    /**
     * 宽度与高度的比值
     */
    private float aspectRatio = 0f;
    private GlideScaleType scaleType;
    private LoaderListener<?> listener;
    private Object mUri;
    private RequestOptions mOptions;
    private static final GlideScaleType[] sScaleTypeArray = {
            GlideScaleType.FIT_CENTER,
            GlideScaleType.CENTER_INSIDE,
            GlideScaleType.CENTER_CROP,
            GlideScaleType.CIRCLE_CROP
    };

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, attrs, 0);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, attrs, defStyleAttr);
    }

    protected void inflate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        roundedRadius = a.getDimensionPixelSize(R.styleable.RatioImageView_riv_roundedRadius, 0);
        roundedMargin = a.getDimensionPixelSize(R.styleable.RatioImageView_riv_roundedMargin, 0);
        isGrayScale = a.getBoolean(R.styleable.RatioImageView_riv_grayScale, false);
        isCropCircle = a.getBoolean(R.styleable.RatioImageView_riv_isCropCircle, false);
        isBlur = a.getBoolean(R.styleable.RatioImageView_riv_isBlur, false);
        rotateDegree = a.getInteger(R.styleable.RatioImageView_riv_rotateDegree, 0);
        useAnimationPool = a.getBoolean(R.styleable.RatioImageView_riv_useAnimationPool, false);
        width = a.getDimensionPixelSize(R.styleable.RatioImageView_riv_width, 0);
        height = a.getDimensionPixelSize(R.styleable.RatioImageView_riv_height, 0);
        aspectRatio = a.getFloat(R.styleable.RatioImageView_riv_ratio, 0.0f);
        final int index = a.getInt(R.styleable.RatioImageView_riv_scaleType, -1);
        if (index >= 0) {
            setScaleType(sScaleTypeArray[index]);
        }
        int placeholderId = a.getResourceId(R.styleable.RatioImageView_riv_placeholder, -1);
        if (placeholderId != -1) {
            placeholderDrawable = AppCompatResources.getDrawable(context, placeholderId);
        }
        int errorId = a.getResourceId(R.styleable.RatioImageView_riv_error, -1);
        if (errorId != -1) {
            errorDrawable = AppCompatResources.getDrawable(context, errorId);
        }
        a.recycle();

    }

    public void setScaleType(GlideScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }
        if (this.scaleType != scaleType) {
            this.scaleType = scaleType;
            setImageUrl(mUri, mOptions);
        }
    }

    /**
     * 监听图片是否加载成功
     *
     * @param listener
     * @author dingpeihua
     * @date 2019/1/2 18:04
     * @version 1.0
     */
    public void setListener(LoaderListener<?> listener) {
        this.listener = listener;
    }

    /**
     * 设置宽高比
     * 注意：由算法决定，必须是宽度与高度的比值
     * 即:width/height
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.aspectRatio = ratio;
    }


    /**
     * 宽高比
     * 注意：由算法决定，必须是宽度与高度的比值
     * 即:width/height
     *
     * @param width
     * @param height
     */
    public void setRatio(float width, float height) {
        if (height == 0 || width == 0) {
            aspectRatio = -1;
            return;
        }
        aspectRatio = width / height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (aspectRatio == 0.0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (aspectRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = Math.round(width / aspectRatio);
            try {
                setMeasuredDimension(width, height);
            } catch (Exception e) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                e.printStackTrace();
            }
        } else { // radio <0 高度随图片变
            Drawable d = getDrawable();
            if (d != null) {
                // ceil not round - avoid thin vertical gaps along the left/right edges
                int width = MeasureSpec.getSize(widthMeasureSpec);
                //宽度定- 高度根据使得图片的宽度充满屏幕
                int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
                try {
                    setMeasuredDimension(width, height);
                } catch (Exception e) {
                    e.printStackTrace();
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }

    /**
     * 根据文件对象加载图片
     *
     * @param url 需要加载的路径
     * @author dingpeihua
     * @date 2017/7/4 15:06
     * @version 1.0
     */
    public void setImageUrl(Object url) {
        setImageUrl(url, 0, 0);
    }

    /**
     * 统一资源标志符加载图片,如果传入的宽和高大于0，则自动压缩图片以适应传入的宽和高
     *
     * @param uri    需要加载图片的统一资源标志符
     * @param width  图片压缩的宽度
     * @param height 图片压缩的高度
     * @author dingpeihua
     * @date 2017/7/4 15:07
     * @version 1.0
     */
    public void setImageUrl(Object uri, int width, int height) {
        if (uri == null) {
            return;
        }
        RequestOptions options = new RequestOptions();
        if (width != 0 && height != 0) {
            options.override(width, height);
        } else if (sizeMultiplier != 0) {
            options.sizeMultiplier(sizeMultiplier);
        }
        setImageUrl(uri, options);
    }

    /**
     * 根据文件对象加载图片
     *
     * @param url      需要加载的路径
     * @param maxWidth 控件显示的最大宽度
     * @param ratio    图片真实宽高比
     * @author dingpeihua
     * @date 2017/7/4 15:06
     * @version 1.0
     */
    public void setImageUrl(Object url, int maxWidth, float ratio) {
        final int newHeight = (int) Math.ceil((float) maxWidth * ratio);
        setImageUrl(url, maxWidth, newHeight);
    }

    /**
     * 统一资源标志符加载图片,如果传入的宽和高大于0，则自动压缩图片以适应传入的宽和高
     *
     * @param uri            需要加载图片的统一资源标志符
     * @param sizeMultiplier {@link RequestOptions#sizeMultiplier}
     * @author dingpeihua
     * @date 2017/7/4 15:07
     * @version 1.0
     */
    public void setImageUrl(Object uri, float sizeMultiplier) {
        if (uri == null) {
            return;
        }
        this.sizeMultiplier = sizeMultiplier;
        setImageUrl(uri, null);
    }

    /**
     * 统一资源标志符加载图片,如果传入的宽和高大于0，则自动压缩图片以适应传入的宽和高
     *
     * @param uri     需要加载图片的统一资源标志符
     * @param options 图片压缩的宽度
     * @author dingpeihua
     * @date 2017/7/4 15:07
     * @version 1.0
     */
    public void setImageUrl(Object uri, RequestOptions options) {
        if (uri == null) {
            return;
        }
        if (!checkContext()) {
            return;
        }
        ImageLoader.Builder builder = ImageLoader.createBuilder();
        if (isCropCircle) {
            builder.cropCircle();
        }
        if (roundedRadius > 0) {
            builder.setRoundedRadius(roundedRadius);
        }
        if (roundedMargin > 0) {
            builder.setRoundedMargin(roundedMargin);
        }
        if (isBlur) {
            builder.blur();
        }
        if (isGrayScale) {
            builder.grayScale();
        }
        if (cornerType != null) {
            builder.cornerType(cornerType);
        }
        if (sizeMultiplier != 0) {
            builder.sizeMultiplier(sizeMultiplier);
        } else if (width != 0 && height != 0) {
            builder.override(width, height);
        }
        this.mUri = uri;
        this.mOptions = options;
        builder.apply(options)
                .useAnimationPool(useAnimationPool)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .scaleType(scaleType)
                .rotateDegree(rotateDegree)
                .load(uri)
                .listener(listener)
                .into(this)
                .submit();
    }

    /**
     * 先判断bitmap 是否被回收
     * 避免Fatal Exception: java.lang.RuntimeException
     * Canvas: trying to use a recycled bitmap android.graphics.Bitmap
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            final Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
        }
        super.onDraw(canvas);
    }

    /**
     * 检查Context是否可以加载图片，避免出现"You cannot start a load for a destroyed activity"崩溃
     * {@link com.bumptech.glide.manager.RequestManagerRetriever#assertNotDestroyed(Activity)}
     * @return context是否可以加载图片
     */
    private boolean checkContext() {
        if (getContext() instanceof Activity) {
            final Activity activity = (Activity) getContext();
            return !activity.isDestroyed() && !activity.isFinishing();
        }
        return true;
    }

    public void setPlaceholderDrawable(Drawable placeholderDrawable) {
        this.placeholderDrawable = placeholderDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    public void setPlaceholderDrawable(@DrawableRes int placeholderResId) {
        this.placeholderDrawable = getContext().getResources().getDrawable(placeholderResId);
    }

    public void setErrorDrawable(@DrawableRes int errorResId) {
        this.errorDrawable = getContext().getResources().getDrawable(errorResId);
    }

    public void setRoundedRadius(int roundedRadius) {
        this.roundedRadius = roundedRadius;
    }

    public void setRoundedMargin(int roundedMargin) {
        this.roundedMargin = roundedMargin;
    }

    public void setCornerType(RoundedCornersTransformation.CornerType cornerType) {
        this.cornerType = cornerType;
    }

    public void setGrayScale(boolean grayScale) {
        isGrayScale = grayScale;
    }

    public void setBlur(boolean blur) {
        isBlur = blur;
    }

    public void setRotateDegree(int rotateDegree) {
        this.rotateDegree = rotateDegree;
    }

    public void setUseAnimationPool(boolean useAnimationPool) {
        this.useAnimationPool = useAnimationPool;
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
    }

    public void setCropCircle(boolean cropCircle) {
        isCropCircle = cropCircle;
    }

    public Drawable getPlaceholderDrawable() {
        return placeholderDrawable;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public float getRatio() {
        return aspectRatio;
    }
}

