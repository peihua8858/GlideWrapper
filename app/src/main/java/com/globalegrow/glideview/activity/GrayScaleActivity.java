package com.globalegrow.glideview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fz.imageloader.widget.RatioImageView;
import com.globalegrow.glideview.Constants;
import com.globalegrow.glideview.R;

/**
 * 灰度变换
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/3 08:44
 */
public class GrayScaleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gray_scale);
        RatioImageView imageView1 = findViewById(R.id.giv_image_1);
        imageView1.setImageUrl(Constants.urls[0]);
        RatioImageView imageView2 = findViewById(R.id.giv_image_2);
        imageView2.setImageUrl(Constants.urls[1]);
        RatioImageView imageView3 = findViewById(R.id.giv_image_3);
        imageView3.setImageUrl(Constants.urls[2]);
        RatioImageView imageView4 = findViewById(R.id.giv_image_4);
        imageView4.setImageUrl(Constants.urls[3]);
        RatioImageView imageView5 = findViewById(R.id.giv_image_5);
        imageView5.setImageUrl(Constants.urls[4]);
    }
}
