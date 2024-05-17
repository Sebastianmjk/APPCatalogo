package com.example.appcatalogo.apiConection.apiCatalogos.model

import com.google.gson.annotations.SerializedName

data class AddJuego(
    @SerializedName("juego_id") val juegoId: MutableList<Int>
) {
    fun isEmpty(): Boolean {
        return juegoId.isEmpty()
    }
}
