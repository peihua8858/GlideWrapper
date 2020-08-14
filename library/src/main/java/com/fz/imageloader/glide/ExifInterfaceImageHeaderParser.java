package com.fz.imageloader.glide;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.util.ByteBufferUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 修复Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.
 * 使用androidx 下的{@link ExifInterface}
 * @author dingpeihua
 * @version 1.0
 * @date 2020/1/7 14:22
 */
public class ExifInterfaceImageHeaderParser implements ImageHeaderParser {
    @NonNull
    @Override
    public ImageType getType(@NonNull InputStream is) throws IOException {
        return ImageType.UNKNOWN;
    }

    @NonNull
    @Override
    public ImageType getType(@NonNull ByteBuffer byteBuffer) throws IOException {
        return ImageType.UNKNOWN;
    }

    @Override
    public int getOrientation(@NonNull InputStream is, @NonNull ArrayPool byteArrayPool)
            throws IOException {
        ExifInterface exifInterface = new ExifInterface(is);
        int result =
                exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        if (result == ExifInterface.ORIENTATION_UNDEFINED) {
            return ImageHeaderParser.UNKNOWN_ORIENTATION;
        }
        return result;
    }

    @Override
    public int getOrientation(@NonNull ByteBuffer byteBuffer, @NonNull ArrayPool byteArrayPool)
            throws IOException {
        return getOrientation(ByteBufferUtil.toStream(byteBuffer), byteArrayPool);
    }
}
