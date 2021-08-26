[![](https://jitpack.io/v/peihua8858/GlideWrapper.svg)](https://jitpack.io/#peihua8858/GlideWrapper)
## Android 基于glide封装图片加载工具。

基于
[Glide 4.12.0](https://github.com/bumptech/glide)，[Glide 中文文档](https://muyangmin.github.io/glide-docs-cn/)

## 使用Imageloader

通过XML将RatioImageView添加为布局文件，可以将其与任何其他视图一样使用

```css
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="vertical">

        <com.fz.imageloader.widget.RatioImageView
            android:id="@+id/riv_image_1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:riv_error="@mipmap/ic_loading_fail"
            app:riv_placeholder="@drawable/ic_android_placehold" />

    </LinearLayout>
```

属性列表

```css
<declare-styleable name="RatioImageView">
        <!--加载中-->
        <attr name="riv_placeholder" format="reference" />
        <!--加载失败-->
        <attr name="riv_error" format="reference" />
        <!--圆角半径-->
        <attr name="riv_roundedRadius" format="dimension|reference" />
        <!--圆角边距-->
        <attr name="riv_roundedMargin" format="dimension|reference" />
        <!--灰度处理-->
        <attr name="riv_grayScale" format="boolean" />
        <!--高斯模糊-->
        <attr name="riv_isBlur" format="boolean" />
        <!--圆形-->
        <attr name="riv_isCropCircle" format="boolean" />
        <!--转角-->
        <attr name="riv_rotateDegree" format="integer" />
        <!--动画-->
        <attr name="riv_useAnimationPool" format="boolean" />
        <!--目标宽度-->
        <attr name="riv_width" format="dimension|reference" />
        <!--目标高度-->
        <attr name="riv_height" format="dimension|reference" />
        <!--宽高比 即：width/height-->
        <attr name="riv_ratio" format="float" />
        <!--图片缩放类型-->
        <attr name="riv_scaleType" format="enum">
            <enum name="fitCenter" value="0" />
            <enum name="centerInside" value="1" />
            <enum name="centerCrop" value="2" />
            <enum name="circleCrop" value="3" />
        </attr>
        <!--自动以图片大小计算控件大小-->
        <attr name="riv_auto_size" format="boolean" />
    </declare-styleable>
```

加载图片

```java
 RatioImageView imageView1 = findViewById(R.id.riv_image_1);
 //加载显示指定尺寸
 imageView1.setImageUrl("https://uidesign.rglcdn.com/RG/image/banner/20190118_7301/love.gif?imbypass=true",550,550);

 或者
 imageView1.setImageUrl("https://uidesign.rglcdn.com/RG/image/banner/20190118_7301/love.gif?imbypass=true");
```

原生ImageView 加载

```java
  ImageLoader.createBuilder(context)
                .load(imageUrl)
                .into(imageView)
                .placeholder(R.id.ic_android_placehold)
                .error(R.id.ic_loading_fail)
                .scaleType(GlideScaleType.CENTER_CROP)
                .submit();
```

## 加载GIF图片

### 1、首先设置属性app:riv\_isShowGif 如果等于true，则加载gif图片

```

  <com.fz.imageloader.widget.RatioImageView
     android:id="@+id/iv_birthday_tips"
     android:layout_width="22dp"
     android:layout_height="22dp"
     android:layout_marginBottom="2dp"
     android:visibility="gone"
     app:riv_isShowGif="true" />
```

### 2、通过代码设置图片地址或资源id

```
ivBirthdayTips.setImageUrl(R.drawable.ic_birthday);
```

### 或者

### 直接通过代码设置isShowGif属性是否为true

```
ivBirthdayTips.setImageUrl(R.drawable.ic_birthday,true);//第一个参数图片资源id，第二个参数是否显示为gif
```

## 添加存储库

```py
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

## 添加依赖

```py
dependencies {
   implementation 'com.github.peihua8858:GlideWrapper:Tag'
    implementation ("com.github.bumptech.glide:glide:4.12.0"){
        //com.squareup.okhttp3:okhttp
        exclude group: 'com.squareup.okhttp3', module: 'okhttp' //by both name and group
    }
    // webpdecoder
    implementation "com.github.zjupure:webpdecoder:2.0.4.12.0"
}
```



