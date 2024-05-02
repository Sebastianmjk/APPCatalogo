package com.example.appcatalogo.apiConection.apiUsuario.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("Usuario") var usuario : String,
    @SerializedName("Nombre") var nombre : String,
    @SerializedName("Apellido") var apellido : String,
    @SerializedName("Email") var email : String,
    @SerializedName("Imagen") var imagen : String
)
