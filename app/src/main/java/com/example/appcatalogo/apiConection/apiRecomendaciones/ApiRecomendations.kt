package com.example.appcatalogo.apiConection.apiRecomendaciones
import com.example.appcatalogo.apiConection.apiJuegos.model.Result
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiRecomendations {
    @POST("recomendations/{juego}/")
    fun listRecomendations(@Path("juego") juego :String): Call<List<Result>>
}