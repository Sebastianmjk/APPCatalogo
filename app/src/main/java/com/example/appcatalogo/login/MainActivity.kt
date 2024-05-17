package com.example.appcatalogo.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import com.example.appcatalogo.R
import com.example.appcatalogo.signUp.SignUp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val comienzaButton =  findViewById<Button>(R.id.buttonInical);
        comienzaButton.setOnClickListener{
            it.isEnabled = false
            it.postDelayed({
                it.isEnabled = true // Habilita el botón de nuevo después de un retraso
            }, 2000)
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

    }


}