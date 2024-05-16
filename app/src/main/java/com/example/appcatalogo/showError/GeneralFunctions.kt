package com.example.appcatalogo.showError

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showError(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.messageErrorToStatus(status:Int):String{
    return when(status){
        400 -> "Error en la solicitud"
        401 -> "No autorizado"
        403 -> "Prohibido"
        404 -> "No encontrado"
        405 -> "Método no permitido"
        409 -> "Usuario ya registrado"
        500 -> "Error interno del servidor"
        else -> "Error desconocido"
    }
}