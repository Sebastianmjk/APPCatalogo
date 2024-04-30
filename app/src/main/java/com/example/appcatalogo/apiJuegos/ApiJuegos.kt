package com.example.appcatalogo.apiJuegos


import com.example.appcatalogo.apiJuegos.model.RemoteResult
import retrofit2.Call
import retrofit2.http.GET


interface ApiJuegos {
    @GET("?limit=10&offset=10")
    fun listJuegos() : Call<RemoteResult>
}

