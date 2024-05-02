package com.example.appcatalogo.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appcatalogo.Prueba
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiUsuario.Service.TokenManager
import com.example.appcatalogo.apiConection.apiUsuario.Service.UserService
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.example.appcatalogo.signUp.SignUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val inputUsername: EditText = findViewById(R.id.inputUsername)
        val inputPassword: EditText = findViewById(R.id.inputPassword)
        var username: String
        var userPassword: String
        val conectSignUp: TextView = findViewById(R.id.conectSignUp)
        conectSignUp.setOnClickListener {
            changeToSignUp()
        }

        val iniciarSesionButton = findViewById<Button>(R.id.buttonIniciarSesion);
        iniciarSesionButton.setOnClickListener {
            username = inputUsername.text.toString()
            userPassword = inputPassword.text.toString()
            println()
            println("Username: $username")
            println("Password: $userPassword")
            CoroutineScope(Dispatchers.IO).launch {
                val response = UserService.loginUser(AutenticacionRequest(username, userPassword))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("Response: ${response.body()}")
                        val tokensUser = response.body()
                        tokensUser?.let {
                            TokenManager.accessToken = it.access
                            TokenManager.refreshToken = it.refresh
                        }
                    } else {
                        showError(response.code())
                    }
                }


            }

        }

    }

    fun changeToSignUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        finish()
    }

    private fun createMessageError(status: Int):String{
        return when(status){
            401 -> "Usuario o contraseña incorrectos"
            403 -> "Acceso denegado"
            else -> "Error: $status"
        }
    }

    private fun showError(status: Int){
        Toast.makeText(this, createMessageError(status), Toast.LENGTH_SHORT).show()
    }
}