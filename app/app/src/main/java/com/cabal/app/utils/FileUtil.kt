package com.cabal.app.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object FileUtil {
        private const val EOF = -1
        private const val  DEFAULT_BUFFER_SIZE = 1024 * 4

    fun from(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = getFileName(context, uri)
        val splitName = splitFileName(fileName!!)
        var tempFile = File.createTempFile(splitName[0], splitName[1])
        tempFile = rename(tempFile, fileName)
        tempFile.deleteOnExit()
        val out = FileOutputStream(tempFile)
        inputStream?.let {
            copy(inputStream, out)
            inputStream.close() }
        out.let { out.close() }
        return tempFile
    }

    private fun splitFileName(fileName: String): Array<String> {
        val i = fileName.lastIndexOf(".")
        return if (fileName.lastIndexOf(".") != -1) arrayOf(fileName.substring(0,i),fileName.substring(i))
        else arrayOf(fileName, "")
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme.equals("content")) {
            val cursor : Cursor? = context.contentResolver.query(uri, null, null, null, null)
            result =
                    if (cursor?.moveToFirst() != null)
                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    else null
            cursor?.close()
        }
        if (result == null) {
            result = uri.path
            val cut: Int? = result?.lastIndexOf(File.separator)
            if (cut != -1) {
                result = result?.substring(cut!!.plus(1))
            }
        }
        return result
    }

    private fun rename(file: File, newName: String): File {
        val newFile = File(file.parent, newName)
        if (newFile != file) {
            if (newFile.exists() && newFile.delete()) {
                Log.d("FileUtil", "Delete old $newName file")
            }
            if (file.renameTo(newFile)) {
                Log.d("FileUtil", "Rename file to $newName")
            }
        }
        return newFile
    }

    private fun copy(input: InputStream, output: OutputStream): Long {
        var count: Long = 0
        var n: Int
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        n = input.read(buffer)
        while (EOF != n) {
            output.write(buffer, 0, n)
            count += n
            n = input.read(buffer)
        }
        return count
    }
}