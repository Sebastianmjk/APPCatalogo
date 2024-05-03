package com.example.appcatalogo.signUp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appcatalogo.R
import com.google.android.material.navigation.NavigationView

class SignUp : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val toolbar : Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawlerLayout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_item_mi_perfil -> Toast.makeText(this,"item 1",Toast.LENGTH_SHORT).show()
            R.id.nav_item_inicio -> Toast.makeText(this,"item 2",Toast.LENGTH_SHORT).show()
            R.id.nav_item_categorias -> Toast.makeText(this,"item 3",Toast.LENGTH_SHORT).show()
            R.id.nav_item_mis_juegos-> Toast.makeText(this,"item 4",Toast.LENGTH_SHORT).show()
            R.id.nav_item_mis_catalogos-> Toast.makeText(this,"item 5",Toast.LENGTH_SHORT).show()
            R.id.nav_item_cerrar_sesion -> Toast.makeText(this,"item 6",Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}