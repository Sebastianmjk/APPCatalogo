package com.example.appcatalogo.apiConection.apiCatalogos

import com.example.appcatalogo.apiConection.apiJuegos.ApiJuegos
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

    val apiJuegos : ApiJuegos by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiJuegos::class.java)
    }

    val apiDelete : ApiCatalogos by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCatalogos::class.java)
    }
}
