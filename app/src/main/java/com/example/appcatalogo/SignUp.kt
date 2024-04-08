package com.example.appcatalogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val buttonNext: Button = findViewById(R.id.buttonNext)
        val buttonBack: Button = findViewById(R.id.buttonBack)


        val navController = findNavController(R.id.fragmentContainerViewSignUp)
        buttonNext.setOnClickListener {
            val navegationFragments = when (navController.currentDestination?.id) {
                R.id.registroFirstPage -> R.id.action_registroFirstPage_to_registroSecondPage
                R.id.registroSecondPage -> R.id.action_registroSecondPage_to_registroThirdPage
                R.id.registroThirdPage -> R.id.action_registroThirdPage_to_registroFourthPage
                else -> null
            }
            navegationFragments?.let {
                navController.navigate(it)
            }
        }

        // Configura el botón para navegar al fragmento anterior
        buttonBack.setOnClickListener {
            navController.popBackStack()
        }
    }
}