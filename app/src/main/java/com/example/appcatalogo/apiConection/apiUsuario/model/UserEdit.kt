package com.example.appcatalogo.apiConection.apiUsuario.model

import com.google.gson.annotations.SerializedName
data class UserEdit(
    @SerializedName("username") var username : String,
    @SerializedName("first_name") var nombre : String,
    @SerializedName("last_name") var apellido : String,
    @SerializedName("email") var email : String,

)
