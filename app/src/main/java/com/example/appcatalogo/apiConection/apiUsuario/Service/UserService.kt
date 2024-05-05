package com.example.appcatalogo.apiConection.apiUsuario.Service

import com.example.appcatalogo.apiConection.apiUsuario.UsuarioApi
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionResponse
import com.example.appcatalogo.apiConection.apiUsuario.model.Usuario
import com.example.appcatalogo.apiConection.apiUsuario.model.UsuarioRegistro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

object UserService {
    private val baseUrlUser:String = "https://apicatalogojuegos.onrender.com/GameVault/Usuario/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrlUser)
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

    suspend fun sendCode(email: String): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            usuarioApi.sendCode(email)
        }
    }

    suspend fun verifyCode(email: String, code: String): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            usuarioApi.verifyCode(email, code)
        }
    }

}