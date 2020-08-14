package com.globalegrow.glideview.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fz.imageloader.glide.GlideScaleType;
import com.fz.imageloader.glide.ImageLoader;
import com.fz.imageloader.glide.LoaderListener;
import com.globalegrow.glideview.R;
import com.socks.library.KLog;

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
                startActivity(new Intent(this, GifActivity2.class));
                break;
            default:
                break;
        }
    }
}
