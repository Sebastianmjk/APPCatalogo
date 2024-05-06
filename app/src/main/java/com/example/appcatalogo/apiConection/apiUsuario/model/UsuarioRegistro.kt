package com.example.appcatalogo.apiConection.apiUsuario.model

import com.google.gson.annotations.SerializedName

data class UsuarioRegistro(
    val username:String,
    @SerializedName("first_name") val firstName:String,
    @SerializedName("last_name")val lastName:String,
    val email:String,
    val password:String,
)
