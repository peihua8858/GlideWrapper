package com.globalegrow.glideview.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fz.imageloader.glide.LoaderListener;
import com.fz.imageloader.utils.ImageUtils;
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
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int newHeight = Math.round(screenWidth * 433f / 650f);
        RatioImageView imageView1 = findViewById(R.id.riv_image_1);
        imageView1.setImageUrl(Constants.urls[0], screenWidth, newHeight);
        RatioImageView imageView2 = findViewById(R.id.riv_image_2);
        imageView2.setImageUrl(Constants.urls[1]);
        final RatioImageView imageView3 = findViewById(R.id.riv_image_3);
//        imageView3.setCornerType(RoundedCornersTransformation.CornerType.TOP_LEFT);
//        imageView3.setRoundedRadius(24);
        imageView3.setListener(new LoaderListener<BitmapDrawable>() {
            @Override
            public boolean onSuccess(BitmapDrawable bitmapDrawable, int width, int height) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                bitmap = ImageUtils.toRoundCorner(bitmap, width, height, 40, ImageUtils.CORNER_TOP_LEFT|ImageUtils.CORNER_TOP_RIGHT);
                imageView3.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                return true;
            }

            @Override
            public boolean onError(Exception e) {
                return false;
            }
        });
        imageView3.setImageUrl(Constants.urls[2]);
        RatioImageView imageView4 = findViewById(R.id.riv_image_4);
        imageView4.setImageUrl(Constants.urls[3]);
        RatioImageView imageView5 = findViewById(R.id.riv_image_5);
        imageView5.setImageUrl(Constants.urls[4]);
    }
}
