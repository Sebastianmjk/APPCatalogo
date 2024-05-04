package com.example.appcatalogo.signUp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appcatalogo.R
import com.google.android.material.navigation.NavigationView

open class SignUp : AppCompatActivity() {
        protected open lateinit var drawerLayout: DrawerLayout
        protected open lateinit var navView: NavigationView
        private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sign_up)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawlerLayout)
        navView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        val menuButton: ImageButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {


        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    }