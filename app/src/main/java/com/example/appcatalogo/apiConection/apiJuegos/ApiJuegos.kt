package com.example.appcatalogo.apiConection.apiJuegos


import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import com.example.appcatalogo.apiConection.apiJuegos.model.Result
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiJuegos {
    @GET("?limit=10")
    fun listJuegos(@Query ("offset") offset : String) : Call<RemoteResult>

    @GET("catalogo/Juegos/{id}")
    suspend fun getJuego(@Path("id") id: Int): Response<Result>
}

