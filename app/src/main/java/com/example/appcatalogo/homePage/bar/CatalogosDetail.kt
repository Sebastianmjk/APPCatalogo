package com.example.appcatalogo.homePage.bar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo
import com.example.appcatalogo.apiConection.apiJuegos.ApiJuegos
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterJuegos
import com.example.appcatalogo.apiConection.apiJuegos.model.Result
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import retrofit2.Response


class CatalogosDetail : Fragment() {

    private lateinit var adaptadorJuegos: AdapterJuegos
    private lateinit var recyclerViewJuegos: RecyclerView
    private var nombreCatalogo: String? = null

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalogos_detail, container, false)

        nombreCatalogo = arguments?.getString("titulo_catalogo")
        val textView = view.findViewById<TextView>(R.id.catalogosNombre)
        textView.text = nombreCatalogo

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewJuegos = view.findViewById(R.id.rvCatalogosJuegos)
        recyclerViewJuegos.layoutManager = layoutManagerJuegos
        adaptadorJuegos = AdapterJuegos(ArrayList())
        recyclerViewJuegos.adapter = adaptadorJuegos

        adaptadorJuegos.onItemClick = { juego ->
            val bundle = Bundle()
            bundle.putString("titulo_juego", juego.titulo)
            bundle.putString("resumen_juego", juego.resumen)
            if (juego.images.isNotEmpty()) {
                bundle.putString("imagen_juego", juego.images[0])
            }
            findNavController().navigate(R.id.action_catalogosDetail_to_juegoDetail, bundle)
        }


        val juegoIds = arguments?.getIntegerArrayList("juego_ids")

        loadJuegos(juegoIds)



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
                    findNavController().navigate(R.id.action_catalogosDetail_to_perfil)
                    true
                }

                R.id.nav_item_inicio -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_homeFirstPage)
                    true
                }

                R.id.nav_item_categorias -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_categoriasSlideBar2)
                    true
                }

                R.id.nav_item_mis_catalogos -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_homeUsuario)
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
                    findNavController().navigate(R.id.action_catalogosDetail_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_perfil)
                }
            }
            true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun loadJuegos(juegoIds: List<Int>?) {
        if (juegoIds != null) {
            lifecycleScope.launch {
                val juegosList = mutableListOf<Result>()
                for (id in juegoIds) {
                        try {
                            val response = ApiCatalogo.apiJuegos.getJuego(id)
                            if (response.isSuccessful) {
                                val juego = response.body()
                                if (juego != null) {
                                    juegosList.add(juego)
                                } else {
                                    // Maneja el caso en que juego es null
                                }
                            } else {
                                // Si la solicitud no fue exitosa, muestra un mensaje de error
                                Toast.makeText(context, "Error al obtener el juego", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            // Maneja cualquier excepción que pueda ocurrir
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                adaptadorJuegos.juegosList.clear()
                adaptadorJuegos.juegosList.addAll(juegosList)
                adaptadorJuegos.notifyDataSetChanged()
                }
            }
        }

}