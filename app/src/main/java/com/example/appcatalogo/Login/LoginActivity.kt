package com.example.appcatalogo.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.appcatalogo.R
import com.example.appcatalogo.SignUp.SignUp

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val conectSignUp:TextView = findViewById(R.id.conectSignUp)
        conectSignUp.setOnClickListener {
            changeToSignUp()
        }

    }

    //Cambiar de actividad de Login a SignUp
    fun changeToSignUp(){
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        finish()
    }
}