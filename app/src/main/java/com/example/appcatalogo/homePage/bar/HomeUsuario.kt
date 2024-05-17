package com.example.appcatalogo.homePage.bar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class HomeUsuario : Fragment() {

    private lateinit var liContenedorCatalogos : LinearLayout
    private lateinit var liCargandoCatalogos : LinearLayout

    private lateinit var drawerLayout: DrawerLayout
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private val accessToken = TokenManager.accessToken

    private lateinit var recyclerViewCatalogo : RecyclerView
    private lateinit var catalogoAdapter : CatalogoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!
        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)

        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)



        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)



        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_homeUsuario_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_homeUsuario_self)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_homeUsuario_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_homeUsuario_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_homeUsuario_to_perfil)
                }
            }
            true
        }

        val layoutManagerCatalogo = LinearLayoutManager(context)
        recyclerViewCatalogo= view.findViewById(R.id.rvTusCatalogos)
        recyclerViewCatalogo.layoutManager = layoutManagerCatalogo
        catalogoAdapter = CatalogoAdapter(lifecycleScope, requireContext(), mutableListOf(), accessToken, findNavController())
        recyclerViewCatalogo.adapter = catalogoAdapter

        fetchCatalogos()

        catalogoAdapter.onItemClick = { catalogo ->
            val bundle = Bundle()
            bundle.putInt("catalogo_id", catalogo.id)
            bundle.putString("titulo_catalogo", catalogo.Nombre)
            if (catalogo.Portada.isNotEmpty()) {
                bundle.putString("imagen_portada", catalogo.Portada)
            }

            val juegoIds = ArrayList<Int>()
            for (juego in catalogo.juegos) {
                juegoIds.add(juego.id)
            }

            bundle.putIntegerArrayList("juego_ids", juegoIds)

            findNavController().navigate(R.id.action_homeUsuario_to_catalogosDetail, bundle)
        }

        liCargandoCatalogos = view.findViewById(R.id.liCargandoCatalogos)
        liContenedorCatalogos = view.findViewById(R.id.liContenedorCatalogos)

        Handler(Looper.getMainLooper()).postDelayed({
            liCargandoCatalogos.isVisible = false
            liContenedorCatalogos.isVisible = true
        }, 3000)
    }

    private fun fetchCatalogos() {
        lifecycleScope.launch {
            try {
                val response = ApiCatalogo.apiCatalogos.getCatalogos("Bearer $accessToken")
                if (response.isSuccessful) {
                    // Si la solicitud fue exitosa, actualiza el RecyclerView
                    val catalogos = response.body()
                    if (catalogos != null) {
                        catalogoAdapter.catalogos.clear()
                        catalogoAdapter.catalogos.addAll(catalogos)
                        catalogoAdapter.notifyDataSetChanged()
                    } else {
                        // Maneja el caso en que catalogos es null
                    }
                } else {
                    // Si la solicitud no fue exitosa, muestra un mensaje de error
                    Toast.makeText(context, "Error al obtener los catálogos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Maneja cualquier excepción que pueda ocurrir
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerViewCatalogo.adapter = null
        catalogoAdapter.catalogos.clear()
    }

}