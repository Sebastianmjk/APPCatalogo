package com.example.appcatalogo.apiConection.apiCatalogos

import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogo
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogos
import com.example.appcatalogo.apiConection.apiJuegos.model.Result
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiCatalogos {
    @GET("catalogo/Catalogos/usuario/")
    suspend fun getCatalogos(@Header("Authorization") authHeader: String): Response<List<Catalogos>>

    @POST("catalogo/Catalogos/create/")
    suspend fun createCatalogo(@Header("Authorization") authHeader: String, @Body newCatalogo: Catalogo): Response<Catalogo>

    @DELETE("catalogo/Catalogos/usuario/delete/{id}/")
    suspend fun deleteCatalogo(@Header("Authorization") authHeader: String, @Path("id") id: Int): Response<Void>

    @POST("catalogo/Catalogos/usuario/add_juego/{id}/{juego_id}/")
    suspend fun addJuegoToCatalogo(@Header("Authorization") authHeader: String, @Path("id") id: Int, @Path("juego_id") juegoId: Int): Response<Void>

    @DELETE("catalogo/Catalogos/usuario/delete_juego/{id}/{juego_id}/")
    suspend fun deleteJuegoFromCatalogo(@Header("Authorization") authHeader: String, @Path("id") id: Int, @Path("juego_id") juegoId: Int): Response<Void>

}
