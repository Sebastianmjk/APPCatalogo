package com.example.appcatalogo.apiConection.apiUsuario

import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionResponse
import com.example.appcatalogo.apiConection.apiUsuario.model.Usuario
import com.example.appcatalogo.apiConection.apiUsuario.model.UsuarioRegistro
import retrofit2.Response
import retrofit2.http.*

interface UsuarioApi {
    @POST("login/")
    suspend fun loginUser(@Body usuario: AutenticacionRequest): Response<AutenticacionResponse>

    @POST("register/")
    suspend fun registerUser(@Body usuario: UsuarioRegistro): Response<AutenticacionResponse>

    @GET("profile/")
    suspend fun profileUser(@Header("Authorization") authHeader: String): Response<Usuario>

    @DELETE("delete_account/{id}")
    suspend fun deleteUser(@Header("Authorization") authHeader: String ,@Path("id") id:Int):Void

    @FormUrlEncoded
    @PATCH("edit_user/")
    suspend fun editUser(@Header("Authorization") authHeader: String ,@FieldMap fieldsUser: Map<String,String>)

}