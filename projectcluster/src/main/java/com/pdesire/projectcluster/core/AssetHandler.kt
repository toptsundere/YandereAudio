package com.pdesire.projectcluster.core

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by pdesire on 10.12.17.
 */

class AssetHandler(val context: Context) {

    @Throws(IOException::class)
    fun copyDirorfileFromAssetManager(arg_assetDir: String, arg_destinationDir: String): String {
        val sd_path = Environment.getExternalStorageDirectory()
        val dest_dir_path = sd_path.toString() + addLeadingSlash(arg_destinationDir) + addLeadingSlash(context.javaClass.name)
        val dest_dir = File(dest_dir_path)

        createDir(dest_dir)

        val asset_manager = context.getApplicationContext().getAssets()
        val files = asset_manager.list(arg_assetDir)

        for (i in files.indices) {

            val abs_asset_file_path = addTrailingSlash(arg_assetDir) + files[i]
            val sub_files = asset_manager.list(abs_asset_file_path)

            if (sub_files.size == 0) {
                // It is a file
                val dest_file_path = addTrailingSlash(dest_dir_path) + files[i]
                copyAssetFile(abs_asset_file_path, dest_file_path)
            } else {
                // It is a sub directory
                copyDirorfileFromAssetManager(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + files[i])
            }
        }

        return dest_dir_path
    }


    @Throws(IOException::class)
    fun copyAssetFile(assetFilePath: String, destinationFilePath: String) {
        val inside = context.getApplicationContext().getAssets().open(assetFilePath)
        val out = FileOutputStream(destinationFilePath)

        val buf = ByteArray(1024)
        val len = inside.read(buf)
        while (len > 0)
            out.write(buf, 0, len)
        inside.close()
        out.close()
    }

    fun addTrailingSlash(path: String): String {
        var newpath = path
        if (newpath[newpath.length - 1] != '/') {
            newpath += "/"
        }
        return newpath
    }

    fun addLeadingSlash(path: String): String {
        var newpath = path
        if (newpath[0] != '/') {
            newpath = "/" + path
        }
        return newpath
    }

    @Throws(IOException::class)
    fun createDir(dir: File) {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw IOException("Can't create directory, a file is in the way")
            }
        } else {
            dir.mkdirs()
            if (!dir.isDirectory()) {
                throw IOException("Unable to create directory")
            }
        }
    }
}
