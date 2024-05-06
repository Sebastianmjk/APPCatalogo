package com.example.appcatalogo.apiConection.apiUsuario.service

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object UserAuthenticateService {

    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequestBuilder = originalRequest.newBuilder()
                val accessToken = TokenManager.accessToken
                if (accessToken != null) {
                    newRequestBuilder.header("Authorization", "Bearer $accessToken")
                }
                val newRequest = newRequestBuilder.build()
                chain.proceed(newRequest)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}