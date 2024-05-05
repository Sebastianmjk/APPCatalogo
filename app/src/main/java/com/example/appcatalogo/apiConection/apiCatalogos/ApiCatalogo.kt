package com.example.appcatalogo.apiConection.apiCatalogos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCatalogo {
    private const val BASE_URL = "https://apicatalogojuegos.onrender.com/GameVault/"

    val apiCatalogos: ApiCatalogos by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCatalogos::class.java)
    }
}
