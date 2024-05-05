package com.example.appcatalogo.apiConection.apiUsuario.model

import com.google.gson.annotations.SerializedName
data class VerifyEmail(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)
