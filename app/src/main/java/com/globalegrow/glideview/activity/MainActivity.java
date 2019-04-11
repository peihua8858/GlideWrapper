package com.globalegrow.glideview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.globalegrow.glideview.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 主界面入口
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/3 08:55
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCircle = findViewById(R.id.btn_circle);
        btnCircle.setOnClickListener(this);
        Button btnRotate = findViewById(R.id.btn_rotate);
        btnRotate.setOnClickListener(this);
        Button btnBlur = findViewById(R.id.btn_blur);
        btnBlur.setOnClickListener(this);
        Button btnGrayScale = findViewById(R.id.btn_gray_scale);
        btnGrayScale.setOnClickListener(this);
        Button btnGif = findViewById(R.id.btn_gif);
        btnGif.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_circle:
                startActivity(new Intent(this, CropCircleActivity.class));
                break;
            case R.id.btn_rotate:
                startActivity(new Intent(this, RotateActivity.class));
                break;
            case R.id.btn_blur:
                startActivity(new Intent(this, BlurActivity.class));
                break;
            case R.id.btn_gray_scale:
                startActivity(new Intent(this, GrayScaleActivity.class));
                break;
            case R.id.btn_gif:
                startActivity(new Intent(this, GifActivity.class));
                break;
            default:
                break;
        }
    }
}
