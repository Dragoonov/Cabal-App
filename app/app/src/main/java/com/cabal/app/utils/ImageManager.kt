package com.cabal.app.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import id.zelory.compressor.Compressor
import java.io.ByteArrayOutputStream

object ImageManager {

    private const val IMAGE_TARGET_WIDTH = 150
    private const val IMAGE_TARGET_HEIGHT = 150
    private const val solution = 1

    fun scaleImage(image: Uri, resolver: ContentResolver, context: Context): Bitmap {
        var bitmap: Bitmap? = null
         when (solution) {
            0 -> bitmap = MediaStore.Images.Media.getBitmap(resolver, image)
            1 -> {
                val actualImage = FileUtil.from(context,image)
                bitmap = Compressor(context)
                    .setMaxHeight(IMAGE_TARGET_HEIGHT)
                    .setMaxWidth(IMAGE_TARGET_WIDTH)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToBitmap(actualImage) }
        }
        bitmap = Bitmap.createScaledBitmap(bitmap!!, IMAGE_TARGET_WIDTH, IMAGE_TARGET_HEIGHT, true)
        return bitmap
    }

    fun scaleImage(image: Bitmap): Bitmap = Bitmap.createScaledBitmap(image, IMAGE_TARGET_WIDTH, IMAGE_TARGET_HEIGHT, true)


    fun convertBitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    fun convertStringToBitmap(encodedString: String): Bitmap? {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(Base64.decode(encodedString, Base64.DEFAULT), 0, encodeByte.size)
    }
}