package com.example.appcatalogo.homePage.bar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appcatalogo.R
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class TusCatalogos : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tus_catalogos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navView = activity?.findViewById(R.id.nav_view)
        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)
        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!


        coordinatorLayout?.visibility = View.VISIBLE
        appBarLayout?.visibility = View.VISIBLE
        navView?.visibility = View.VISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        navView?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_mi_perfil -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_perfil)
                    true
                }
                R.id.nav_item_inicio -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_homeFirstPage)
                    true
                }
                R.id.nav_item_categorias -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_categoriasSlideBar2)
                    true
                }
                R.id.nav_item_mis_juegos -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_tusJuegos2)
                    true
                }
                R.id.nav_item_mis_catalogos -> {
                    findNavController().navigate(R.id.action_tusCatalogos_self)
                    true
                }
                R.id.nav_item_cerrar_sesion -> {
                    findNavController().popBackStack(R.id.loginFragment, false)
                    true

                }
                else -> false
            }
        }

        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_homeFirstPage)
                }
                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_homeUsuario)
                }
                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_crear)
                }
                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_buscar)
                }
                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_tusCatalogos_to_perfil)
                }
            }
            true
        }
    }

}