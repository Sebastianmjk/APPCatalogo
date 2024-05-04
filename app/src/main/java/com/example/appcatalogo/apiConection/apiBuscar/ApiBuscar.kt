package com.example.appcatalogo.apiConection.apiBuscar

import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiBuscar {
    @GET("?")
    fun listBuscar(@Query("titulo") titulo : String) : Call<RemoteResult>
}