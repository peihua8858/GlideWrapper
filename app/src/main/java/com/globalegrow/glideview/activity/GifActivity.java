package com.globalegrow.glideview.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fz.imageloader.widget.RatioImageView;
import com.globalegrow.glideview.CustomDraweeView;
import com.globalegrow.glideview.R;

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
        imageView1.setImageUrl(urls[0],screenWidth, newHeight);
        RatioImageView imageView2 = findViewById(R.id.riv_image_2);
        newHeight = screenWidth / (500 / 253);
        imageView2.setImageUrl(urls[1],screenWidth, newHeight);
        RatioImageView imageView3 = findViewById(R.id.riv_image_3);
        newHeight = screenWidth / (500 / 300);
        imageView3.setImageUrl(urls[2],screenWidth, newHeight);
        RatioImageView imageView4 = findViewById(R.id.riv_image_4);
        imageView4.setImageUrl(R.mipmap.free_stock_photo,screenWidth, newHeight);
        RatioImageView imageView5 = findViewById(R.id.riv_image_5);
//        imageView5.setImageUrl(urls[4]);
        imageView5.setImageUrl("https://uidesign.zafcdn.com/ZF/image/2019/20191106_13705/M_10am.gif?yyyyy=w375_2x",screenWidth, newHeight);
        CustomDraweeView imageView6= findViewById(R.id.riv_image_6);
//        imageView6.setImage(urls[2],screenWidth, newHeight);
        imageView6.setImage("https://uidesign.zafcdn.com/ZF/image/2019/20191106_13705/M_10am.gif?im_scale=w375_2x",screenWidth, newHeight);
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
