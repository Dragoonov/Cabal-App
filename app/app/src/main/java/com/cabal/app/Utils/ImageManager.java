package com.cabal.app.Utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageManager {

    private static final int IMAGE_TARGET_WIDTH = 150;
    private static final int IMAGE_TARGET_HEIGHT = 150;

    public static Bitmap scaleImage(Uri image, ContentResolver resolver) {
        Bitmap resized = null;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, image);
            resized = Bitmap.createScaledBitmap(bitmap, IMAGE_TARGET_WIDTH, IMAGE_TARGET_HEIGHT, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resized;
    }

    public static Bitmap scaleImage(Bitmap image) {
        return Bitmap.createScaledBitmap(image, IMAGE_TARGET_WIDTH, IMAGE_TARGET_HEIGHT, true);
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
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
