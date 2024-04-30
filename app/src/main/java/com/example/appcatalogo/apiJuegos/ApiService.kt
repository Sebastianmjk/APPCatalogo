package com.example.appcatalogo.apiJuegos

import com.example.appcatalogo.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiService {
    @GET("https://apicatalogojuegos.onrender.com/GameVault/Usuario/profile/")
    suspend fun getGame(@Header("Authorization") authHeader: String): Response<ImageResponse>
}
