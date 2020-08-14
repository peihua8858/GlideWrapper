package com.globalegrow.glideview.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fz.imageloader.glide.GlideScaleType;
import com.fz.imageloader.glide.ImageLoader;
import com.fz.imageloader.glide.LoaderListener;
import com.fz.imageloader.widget.RatioImageView;
import com.globalegrow.glideview.R;
import com.socks.library.KLog;

/**
 * 灰度变换
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/3 08:44
 */
public class GifActivity extends AppCompatActivity {
    public static final String[] urls = {
            "http://hiphotos.baidu.com/feed/pic/item/9e3df8dcd100baa1e12cffe04c10b912c8fc2e6c.gif",
            "http://ww1.sinaimg.cn/large/85cccab3gw1etdes4ofjvg20dw08c7h9.gif",
            "http://d.ifengimg.com/w128/p0.ifengimg.com/pmop/2017/0806/C0BA921DAA14CD256C09AA83A737C3C768C5DB16_size795_w500_h208.gif",
            "http://pic.sc.chinaz.com/files/pic/pic9/201610/apic23847.jpg",
            "http://www.seotcs.com/public/ueditor/php/upload1/2018090414/3699-1-130424134245X5.jpg"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        RatioImageView imageView1 = findViewById(R.id.riv_image_1);
        int screenWidth = getScreenWidth();
        int newHeight = screenWidth / (500 / 208);
//        Transformation<Bitmap> circleCrop = new CircleCrop();
//        GlideApp.with(this)
//                .load("https://uidesign.zafcdn.com/ZF/image/189/BANNER_01.gif")
//                .optionalTransform(circleCrop)
//                .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
//                .into(imageView1);
//        imageView1.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574081143936&di=fbc35b1b5134857f89c9774d4be8871f&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F43efd35d1e9cadc6d8ff5cdc5faccec06f1082bb4efc4-o8K27E_fw658");
//        imageView1.setImageUrl("https://uidesign.zafcdn.com/ZF/image/189/BANNER_01.gif",screenWidth,newHeight);
//        Glide.with(this).asGif()
//                .apply(new RequestOptions().override(screenWidth,newHeight)).load("https://uidesign.zafcdn.com/ZF/image/189/BANNER_01.gif").into(imageView1);
        RatioImageView imageView2 = findViewById(R.id.riv_image_2);
        newHeight = screenWidth / (500 / 253);
//        imageView2.setImageUrl(urls[1], screenWidth, newHeight);
        RatioImageView imageView3 = findViewById(R.id.riv_image_3);
        newHeight = screenWidth / (500 / 300);
//        imageView3.setImageUrl("https://gloimg.zafcdn.com/zaful/pdm-product-pic/Clothing/2019/09/04/goods-grid-app/1567743520941253652.jpg", screenWidth, newHeight);
        RatioImageView imageView4 = findViewById(R.id.riv_image_4);
//        imageView4.setImageUrl(R.mipmap.free_stock_photo, screenWidth, newHeight);
        RatioImageView imageView5 = findViewById(R.id.riv_image_5);
//        imageView5.setImageUrl(" https://uidesign.zafcdn.com/ZF/image/2019/20191029_13493/M-750-386-fr.jpg");
//        imageView5.setImageUrl("https://uidesign.zafcdn.com/ZF/image/2019/20191106_13705/M_10am.gif?yyyyy=w375_2x",screenWidth, newHeight);
        RatioImageView imageView6 = findViewById(R.id.riv_image_6);
//        imageView6.setImage(urls[2],screenWidth, newHeight);
//        imageView6.setImageUrl("https://uidesign.zafcdn.com/ZF/image/189/BANNER_01.gif", screenWidth, newHeight);

        ImageView imageView7 = findViewById(R.id.riv_image_7);
        ImageLoader.createBuilder(this)
                .load("https://uidesign.rglcdn.com/RG/image/others/20190830_12416/LOGO@3x.png")
                .into(imageView7)
                .listener(new LoaderListener<Drawable>() {
                    @Override
                    public boolean onSuccess(Drawable bitmap, int width, int height) {
                        KLog.d("ImageLoader>>bitmap:[" + bitmap.getIntrinsicWidth() + ":" + bitmap.getIntrinsicHeight() + "]");
                        return false;
                    }

                    @Override
                    public boolean onError(Exception e) {
                        return false;
                    }
                })
                .scaleType(GlideScaleType.CENTER_INSIDE).submit();
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public int getScreenWidth() {
        return getScreenWidth(this);
    }

    public int getScreenHeight() {
        return getScreenHeight(this);
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
