package com.globalegrow.glideview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.globalegrow.glideview.Constants;
import com.globalegrow.glideview.R;
import com.fz.imageload.widget.GlideImageView;

/**
 * 颜色滤镜
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/3 08:44
 */
public class ColorFilterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_filter);
        GlideImageView imageView1 = findViewById(R.id.giv_image_1);
        imageView1.setImageUrl(Constants.urls[0]);
        GlideImageView imageView2 = findViewById(R.id.giv_image_2);
        imageView2.setImageUrl(Constants.urls[1]);
        GlideImageView imageView3 = findViewById(R.id.giv_image_3);
        imageView3.setImageUrl(Constants.urls[2]);
        GlideImageView imageView4 = findViewById(R.id.giv_image_4);
        imageView4.setImageUrl(Constants.urls[3]);
        GlideImageView imageView5 = findViewById(R.id.giv_image_5);
        imageView5.setImageUrl(Constants.urls[4]);
        GlideImageView imageView6 = findViewById(R.id.giv_image_6);
        imageView6.setImageUrl(R.mipmap.home);
    }
}
