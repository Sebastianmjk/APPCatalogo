package com.example.appcatalogo.apiConection.functions

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

object ImageController {

    fun uriToFile(uri: Uri?, context:Context): File? {
        uri ?: return null
        try{
            val  inputStream = context.contentResolver.openInputStream(uri)?: return null
            val mimeType = context.contentResolver.getType(uri)?: return null
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)?: return null
            val file = File.createTempFile("tempImage", ".$extension", context.cacheDir)
            val outputStream = FileOutputStream(file)
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            return file
        }catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun fileToMultiparBody(file: File, name:String): MultipartBody.Part {
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

}

