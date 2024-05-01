package com.example.appcatalogo.apiConection.apiUsuario

import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionResponse
import com.example.appcatalogo.apiConection.apiUsuario.model.Usuario
import com.example.appcatalogo.apiConection.apiUsuario.model.UsuarioRegistro
import retrofit2.http.*

interface UsuarioApi {
    @POST("login/")
    fun loginUser(@Body usuario: AutenticacionRequest): AutenticacionResponse

    @POST("register/")
    fun registerUser(@Body usuario: UsuarioRegistro): AutenticacionResponse

    @GET("profile/")
    fun profileUser(@Header("Authorization") authHeader: String): Usuario

    @DELETE("delete_account/{id}")
    fun deleteUser(@Header("Authorization") authHeader: String ,@Path("id") id:Int):Void

    @FormUrlEncoded
    @PATCH("edit_user/")
    fun editUser(@Header("Authorization") authHeader: String ,@FieldMap fieldsUser: Map<String,String>)

}