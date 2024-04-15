package com.example.appcatalogo.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.appcatalogo.Prueba
import com.example.appcatalogo.R
import com.example.appcatalogo.signUp.SignUp

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val conectSignUp: TextView = findViewById(R.id.conectSignUp)
        conectSignUp.setOnClickListener {
            changeToSignUp()
        }

        val iniciarSesionButton = findViewById<Button>(R.id.buttonIniciarSesion);
        iniciarSesionButton.setOnClickListener {
            val intent = Intent(this, Prueba::class.java)
            startActivity(intent)

        }

    }
    fun changeToSignUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        finish()
    }
}