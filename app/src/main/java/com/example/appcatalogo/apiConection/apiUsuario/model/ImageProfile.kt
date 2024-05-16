package com.example.appcatalogo.apiConection.apiUsuario.model

import okhttp3.MultipartBody
import com.google.gson.annotations.SerializedName

data class ImageProfile(
    @SerializedName("image_profile") var imageProfile : MultipartBody.Part
)
