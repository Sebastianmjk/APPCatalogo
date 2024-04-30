package com.example.appcatalogo.apiJuegos


import com.example.appcatalogo.apiJuegos.model.RemoteResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiJuegos {
    @GET("?limit=10")
    fun listJuegos(@Query ("offset") offset : String) : Call<RemoteResult>
}

