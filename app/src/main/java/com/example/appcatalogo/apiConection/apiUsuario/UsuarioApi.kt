package com.example.appcatalogo.apiConection.apiUsuario

import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionResponse
import com.example.appcatalogo.apiConection.apiUsuario.model.Usuario
import com.example.appcatalogo.apiConection.apiUsuario.model.UsuarioRegistro
import com.example.appcatalogo.apiConection.apiUsuario.model.EmailSendCode
import com.example.appcatalogo.apiConection.apiUsuario.model.VerifyEmail
import com.example.appcatalogo.apiConection.apiUsuario.model.UserChangePassword
import com.example.appcatalogo.apiConection.apiUsuario.model.UserEdit
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface UsuarioApi {
    @POST("login/")
    suspend fun loginUser(@Body usuario: AutenticacionRequest): Response<AutenticacionResponse>

    @POST("register/")
    suspend fun registerUser(@Body usuario: UsuarioRegistro): Response<AutenticacionResponse>

    @GET("profile/")
    suspend fun profileUser(@Header("Authorization") authHeader: String): Response<Usuario>

    @DELETE("delete_account/{id}")
    suspend fun deleteUser(@Header("Authorization") authHeader: String, @Path("id") id: Int): Void

    @PATCH("edit_user/")
    suspend fun editUser(
        @Header("Authorization") authHeader: String,
        @Body usuario: UserEdit
    ): Response<Usuario>

    @Multipart
    @PATCH("edit_user/")
    suspend fun changeImageProfile(
        @Header("Authorization") authHeader: String,
        @Part("image_profile") imageProfile: File
    ): Response<Usuario>

    @POST("email/register/send_code/")
    suspend fun sendCodeRegister(@Body email: EmailSendCode): Response<ResponseBody>

    @POST("email/change_password/send_code/")
    suspend fun sendCodeChangePassword(@Body email: EmailSendCode): Response<ResponseBody>

    @POST("email/code_verify/")
    suspend fun verifyCode(@Body solicitud: VerifyEmail): Response<ResponseBody>

    @POST("change_password/")
    suspend fun changePasswordForgot(@Body solicitud: UserChangePassword): Response<ResponseBody>

}