package com.example.appcatalogo.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.appcatalogo.R
import com.google.android.material.navigation.NavigationView

open class SignUp : AppCompatActivity() {
    protected open lateinit var drawerLayout: DrawerLayout
    protected open lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawlerLayout)
        navView = findViewById(R.id.nav_view)


        val menuButton: ImageButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerViewSignUp) as NavHostFragment
        val navController = navHostFragment.navController

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            navController.popBackStack()
        }

    }
}