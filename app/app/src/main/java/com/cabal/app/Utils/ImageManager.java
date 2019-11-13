package com.cabal.app.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.cabal.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class ImageManager {

    private static final int IMAGE_TARGET_WIDTH = 150;
    private static final int IMAGE_TARGET_HEIGHT = 150;
    //TODO DECIDE WHICH SOLUTION
    private static final int solution = 1;

    public static Bitmap scaleImage(Uri image, ContentResolver resolver, Context context) {
        Bitmap bitmap = null;
        try {
            switch (solution){
                case 0:
                    bitmap = MediaStore.Images.Media.getBitmap(resolver, image);
                    break;
                case 1:
                    File actualImage = FileUtil.from(context,image);
                    bitmap = new Compressor(context)
                            .setMaxHeight(IMAGE_TARGET_HEIGHT)
                            .setMaxWidth(IMAGE_TARGET_WIDTH)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .compressToBitmap(actualImage);
                    break;
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_TARGET_WIDTH, IMAGE_TARGET_HEIGHT, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap scaleImage(Bitmap image) {
        return Bitmap.createScaledBitmap(image, IMAGE_TARGET_WIDTH, IMAGE_TARGET_HEIGHT, true);
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap convertStringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
