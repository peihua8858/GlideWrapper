package com.globalegrow.glideview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fz.imageloader.widget.RatioImageView;
import com.globalegrow.glideview.Constants;
import com.globalegrow.glideview.R;

/**
 * 旋转变换
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/3 08:44
 */
public class RotateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        RatioImageView imageView1 = findViewById(R.id.riv_image_1);
        imageView1.setImageUrl(Constants.urls[0]);
        RatioImageView imageView2 = findViewById(R.id.riv_image_2);
        imageView2.setImageUrl(Constants.urls[0]);
        RatioImageView imageView3 = findViewById(R.id.riv_image_3);
        imageView3.setImageUrl(Constants.urls[0]);
    }
}
