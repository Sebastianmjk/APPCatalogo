package com.example.appcatalogo.apiConection.apiUsuario.service

import com.example.appcatalogo.apiConection.apiUsuario.UsuarioApi
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionResponse
import com.example.appcatalogo.apiConection.apiUsuario.model.Usuario
import com.example.appcatalogo.apiConection.apiUsuario.model.UsuarioRegistro
import com.example.appcatalogo.apiConection.apiUsuario.model.EmailSendCode
import com.example.appcatalogo.apiConection.apiUsuario.model.VerifyEmail
import com.example.appcatalogo.apiConection.apiUsuario.model.UserChangePassword
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import com.example.appcatalogo.apiConection.apiUsuario.model.UserEdit

object UserService {
    private val baseUrlUser:String = "https://apicatalogojuegos.onrender.com/GameVault/Usuario/"
    private val client = UserAuthenticateService.getOkHttpClient()
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrlUser)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val usuarioApi = retrofit.create(UsuarioApi::class.java)



    suspend fun loginUser(usuario: AutenticacionRequest): Response<AutenticacionResponse> {
        return withContext(Dispatchers.IO) {
            usuarioApi.loginUser(usuario)
        }
    }

    suspend fun registerUser(usuario: UsuarioRegistro): Response<AutenticacionResponse> {
        return withContext(Dispatchers.IO) {
            usuarioApi.registerUser(usuario)
        }
    }
    suspend fun profileUser(authHeader: String): Response<Usuario> {
        return withContext(Dispatchers.IO) {
            usuarioApi.profileUser(authHeader)
        }
    }

    suspend fun sendCode(email:EmailSendCode): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            usuarioApi.sendCode(email)
        }
    }

    suspend fun verifyCode(solicitud:VerifyEmail): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            usuarioApi.verifyCode(solicitud)
        }
    }

    suspend fun changePasswordForgot(solicitud:UserChangePassword): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            usuarioApi.changePasswordForgot(solicitud)
        }
    }

    suspend fun editUser(authHeader: String, usuario: UserEdit): Response<Usuario> {
        return withContext(Dispatchers.IO) {
            usuarioApi.editUser(authHeader, usuario)
        }
    }

}