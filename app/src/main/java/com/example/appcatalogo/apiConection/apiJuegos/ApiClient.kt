package com.example.appcatalogo.apiConection.apiJuegos

import com.example.appcatalogo.apiConection.apiGeneros.ApiGeneros
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://apicatalogojuegos.onrender.com/GameVault/catalogo/Juegos/"

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiJuegos by lazy {
        retrofit.create(ApiJuegos::class.java)
    }

    val apiGenero: ApiGeneros by lazy {
        retrofit.create(ApiGeneros::class.java)
    }
}
