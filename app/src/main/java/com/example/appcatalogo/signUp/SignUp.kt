package com.example.appcatalogo.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appcatalogo.R
import com.google.android.material.navigation.NavigationView

open class SignUp : AppCompatActivity() {
        protected open lateinit var drawerLayout: DrawerLayout
        protected open lateinit var navView: NavigationView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sign_up)

            drawerLayout = findViewById(R.id.drawlerLayout)
            navView = findViewById(R.id.nav_view)
        }
    }