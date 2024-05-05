package com.example.appcatalogo.apiConection.apiUsuario.model

import com.google.gson.annotations.SerializedName
data class UserChangePassword(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("password")
    val password: String
)
