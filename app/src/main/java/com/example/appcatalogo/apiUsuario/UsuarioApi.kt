package com.example.appcatalogo.apiUsuario

import com.example.appcatalogo.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.apiUsuario.model.AutenticacionResponse
import com.example.appcatalogo.apiUsuario.model.Usuario
import com.example.appcatalogo.apiUsuario.model.UsuarioRegistro
import retrofit2.http.*

interface UsuarioApi {
    @POST("login/")
    fun loginUser(@Body usuario:AutenticacionRequest): AutenticacionResponse

    @POST("register/")
    fun registerUser(@Body usuario:UsuarioRegistro): AutenticacionResponse

    @GET("profile/")
    fun profileUser(@Header("Authorization") authHeader: String): Usuario

    @DELETE("delete_account/{id}")
    fun deleteUser(@Header("Authorization") authHeader: String ,@Path("id") id:Int):Void

    @FormUrlEncoded
    @PATCH("edit_user/")
    fun editUser(@Header("Authorization") authHeader: String ,@FieldMap fieldsUser: Map<String,String>)

}