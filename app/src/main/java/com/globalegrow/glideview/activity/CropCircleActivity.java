package com.globalegrow.glideview.activity;

import android.os.Bundle;

import com.fz.imageloader.widget.RatioImageView;
import com.globalegrow.glideview.Constants;
import com.globalegrow.glideview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        RatioImageView imageView1 = findViewById(R.id.riv_image_1);
        imageView1.setImageUrl("https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1567511002179&amp;di=b8c4a5e624902aeff2dd6404d9bb52ac&amp;imgtype=0&amp;src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201406%2F30%2F20140630150802_CjcEY.jpeg");
//        imageView1.setImageUrl("https://uidesign.rglcdn.com/RG/image/banner/20190118_7301/love.gif?imbypass=true",550,550);
        RatioImageView imageView2 = findViewById(R.id.riv_image_2);
        imageView2.setReverseDirection(RatioImageView.REVERSE_HORIZONTAL);
        imageView2.setGrayScale(true);
        imageView2.setImageUrl(Constants.urls[0]);
        RatioImageView imageView3 = findViewById(R.id.riv_image_3);
        imageView3.setReverseDirection(RatioImageView.REVERSE_VERTICAL);
        imageView3.setImageUrl(R.drawable.ic_birthday);

    }
}
