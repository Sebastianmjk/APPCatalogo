package com.example.appcatalogo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getGame(@Url url:String):Response<ImageResponse>
}