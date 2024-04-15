package com.example.appcatalogo

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("status") var status : String,
    @SerializedName("message") var images : List<String>
)