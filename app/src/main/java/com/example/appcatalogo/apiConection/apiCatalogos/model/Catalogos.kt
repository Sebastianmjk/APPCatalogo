package com.example.appcatalogo.apiConection.apiCatalogos.model

import com.example.appcatalogo.apiConection.apiJuegos.model.Result

data class Catalogos(
    val Nombre: String,
    val Portada: String,
    val id: Int,
    val juegos: List<Result>,
    val usuario: Int
)