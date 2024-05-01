package com.example.appcatalogo.apiConection.apiJuegos.model

data class RemoteResult(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)