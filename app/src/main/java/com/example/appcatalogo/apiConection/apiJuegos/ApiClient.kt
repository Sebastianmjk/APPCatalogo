package com.example.appcatalogo.apiConection.apiJuegos

import com.example.appcatalogo.apiConection.apiBuscar.ApiBuscar
import com.example.appcatalogo.apiConection.apiRecomendaciones.ApiRecomendations
import com.example.appcatalogo.apiConection.apiGeneros.ApiGeneros
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://apicatalogojuegos-production.up.railway.app/GameVault/catalogo/Juegos/"

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

    val apiRecomendation : ApiRecomendations by lazy {
        retrofit.create(ApiRecomendations::class.java)
    }

    val apiBuscar : ApiBuscar by lazy {
        retrofit.create(ApiBuscar::class.java)
    }
}
