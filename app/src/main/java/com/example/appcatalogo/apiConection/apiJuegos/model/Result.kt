package com.example.appcatalogo.apiConection.apiJuegos.model

import com.google.gson.annotations.SerializedName

data class Result(
    val comprado_no_jugado: Int,
    val desarrolladora: List<String>,
    val fecha_Lanzamiento: String,
    val generos: List<String>,
    val id: Int,
    val images: List<String>,
    val listas_de_deseos: Int,
    val menciones_listas: Int,
    val numero_Partidas: Int,
    val numero_jugadores: Int,
    val plataformas: List<String>,
    @SerializedName("reseñas") val resenas: Int,
    val resumen: String,
    val titulo: String,
    val valoracion: Double
)