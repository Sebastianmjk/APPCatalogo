package com.example.appcatalogo.apiConection.apiGeneros

import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGeneros {
    @GET("?&limit=10")
    fun listGeneros(@Query("generos") genero : String, @Query("offset") offset : String): Call<RemoteResult>
}