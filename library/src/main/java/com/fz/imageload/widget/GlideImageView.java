package com.fz.imageload.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.request.RequestOptions;
import com.fz.imageload.R;
import com.fz.imageload.glide.ImageLoader;
import com.fz.imageload.glide.LoaderListener;
import com.fz.imageload.glide.RoundedCornersTransformation;

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
public class GlideImageView extends AppCompatImageView {
    private Drawable placeholderDrawable;
    private Drawable errorDrawable;
    /**
     * 圆角半径
     */
    private int roundedRadius;
    private int roundedMargin;
    /**
     * 圆角位置
     */
    private RoundedCornersTransformation.CornerType cornerType;
    private boolean isGrayScale;
    private boolean isBlur;
    private int rotateDegree;
    private boolean useAnimationPool;
    private int width, height;
    private float sizeMultiplier = 0f;
    private boolean isCropCircle;
    private float ratio;
    private LoaderListener<?> listener;

    public GlideImageView(Context context) {
        super(context);
    }

    public GlideImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, attrs);
    }

    public GlideImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, attrs);
    }

    protected void inflate(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GlideImageView);
        placeholderDrawable = a.getDrawable(R.styleable.GlideImageView_giv_placeholder);
        errorDrawable = a.getDrawable(R.styleable.GlideImageView_giv_error);
        roundedRadius = a.getDimensionPixelSize(R.styleable.GlideImageView_giv_roundedRadius, 0);
        roundedMargin = a.getDimensionPixelSize(R.styleable.GlideImageView_giv_roundedMargin, 0);
        isGrayScale = a.getBoolean(R.styleable.GlideImageView_giv_grayScale, false);
        isCropCircle = a.getBoolean(R.styleable.GlideImageView_giv_isCropCircle, false);
        isBlur = a.getBoolean(R.styleable.GlideImageView_giv_isBlur, false);
        rotateDegree = a.getInteger(R.styleable.GlideImageView_giv_rotateDegree, 0);
        useAnimationPool = a.getBoolean(R.styleable.GlideImageView_giv_useAnimationPool, false);
        width = a.getDimensionPixelSize(R.styleable.GlideImageView_giv_width, 0);
        height = a.getDimensionPixelSize(R.styleable.GlideImageView_giv_height, 0);
        a.recycle();
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
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }


    /**
     * 宽高比
     *
     * @param width
     * @param height
     */
    public void setRatio(float width, float height) {
        if (height == 0 || width == 0) {
            ratio = -1;
            return;
        }
        ratio = width / height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio == 0.0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (ratio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = Math.round(width / ratio);
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
        builder.apply(options)
                .useAnimationPool(useAnimationPool)
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
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
}

