package com.example.appcatalogo.homePage

import com.google.gson.annotations.SerializedName

data class Juegos (
    val id : Int,
    val titulo : String,
    @SerializedName("fecha_Lanzamiento") val fechaLanzamiento : String,
    val resumen : String,
    val valoracion : Float,
    @SerializedName("numero_Partidas") val numeroPartidas : Int,
    @SerializedName("numero_jugadores") val numeroJugadores : Int,
    @SerializedName("comprado_no_jugado") val compradoNoJugado : Int,
    @SerializedName("menciones_listas") val mencionesListas : Int,
    @SerializedName("listas_de_deseos") val listasDeDeseos : Int,
    @SerializedName("reseñas") val resenas : Int,
    val generos : List<String>,
    val plataformas : List<String>,
    val desarrolladora : List<String>,
    val imagen : Int
    )
