package com.globalegrow.glideview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.globalegrow.glideview.Constants;
import com.globalegrow.glideview.R;
import com.fz.imageloader.widget.RatioImageView;

/**
 * 圆形变换
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/3 08:41
 */
public class CropCircleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_circle);
        RatioImageView imageView1 = findViewById(R.id.giv_image_1);
        imageView1.setImageUrl(Constants.urls[0],550,550);
        RatioImageView imageView2 = findViewById(R.id.giv_image_2);
        imageView2.setImageUrl(Constants.urls[0]);
    }
}
