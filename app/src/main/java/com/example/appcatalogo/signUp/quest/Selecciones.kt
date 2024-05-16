package com.example.appcatalogo.signUp.quest

data class Selecciones (
    val id : Int,
    val image : Int,
    val nombre : String,
    var seleccionado: Boolean = false)