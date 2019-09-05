package com.globalegrow.glideview.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fz.imageloader.widget.RatioImageView;
import com.globalegrow.glideview.R;

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
        imageView1.setImageUrl("https://review.rglcdn.com/upload/rosegal/avatar/20190827/0C05D3C4F0F0947047E9B420AD569CF7.gif");
//        Glide.with(this).load("http://hiphotos.baidu.com/feed/pic/item/9e3df8dcd100baa1e12cffe04c10b912c8fc2e6c.gif")
//                .into(imageView1);
//        imageView1.setImageUrl("https://uidesign.zafcdn.com/ZF/image/other/20190831_12438/es_10.gif");
//        imageView1.setImageUrl("https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1567511002179&amp;di=b8c4a5e624902aeff2dd6404d9bb52ac&amp;imgtype=0&amp;src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201406%2F30%2F20140630150802_CjcEY.jpeg");
//        imageView1.setImageUrl("http://10.36.5.100:8090/files/upload/fr_02.gif");
//        imageView1.setImageUrl("http://hiphotos.baidu.com/feed/pic/item/9e3df8dcd100baa1e12cffe04c10b912c8fc2e6c.gif",200,200);
        RatioImageView imageView2 = findViewById(R.id.riv_image_2);
        imageView2.setReverseDirection(RatioImageView.REVERSE_HORIZONTAL);
        imageView2.setGrayScale(true);
        imageView2.setImageUrl("http://hiphotos.baidu.com/feed/pic/item/9e3df8dcd100baa1e12cffe04c10b912c8fc2e6c.gif");
        RatioImageView imageView3 = findViewById(R.id.riv_image_3);
        imageView3.setReverseDirection(RatioImageView.REVERSE_VERTICAL);
        imageView3.setImageUrl(R.drawable.ic_birthday);

    }
}
