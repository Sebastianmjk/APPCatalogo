package com.example.appcatalogo.apiConection.apiUsuario.model

import com.google.gson.annotations.SerializedName
data class EmailSendCode(
    @SerializedName("to_email")
    val toEmail: String
)
