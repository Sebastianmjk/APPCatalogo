package com.example.appcatalogo.homePage.bar

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.appcatalogo.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogo
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class TusCatalogos : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private val accessToken = TokenManager.accessToken
    private lateinit var catalogoAdapter : CatalogoAdapter



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

        val addButton = view.findViewById<FloatingActionButton>(R.id.addBtton)
        addButton.setOnClickListener {
            showCreateCatalogoDialog()
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvTusCatalogos)

        // Crea una instancia de tu CatalogoAdapter
        catalogoAdapter = CatalogoAdapter(mutableListOf())

        // Configura el RecyclerView para usar tu CatalogoAdapter
        recyclerView.adapter = catalogoAdapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun showCreateCatalogoDialog() {
        // Infla la vista del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.add_catalogo, null)

        // Crea el diálogo
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Crear nuevo catálogo")
            .setPositiveButton("Crear") { _, _ ->
                val nombre = dialogView.findViewById<EditText>(R.id.etTituloCatalogo).text.toString()
                val juegosString = dialogView.findViewById<EditText>(R.id.etIdJuegos).text.toString()

                // Convierte la lista de IDs de juegos a una lista de Int
                val juegos = juegosString.split(",").map { it.trim().toInt() }

                val newCatalogo = Catalogo(nombre, juegos)

                // Llama a la API en una corutina
                lifecycleScope.launch {
                    val response = ApiCatalogo.apiCatalogos.createCatalogo("Bearer $accessToken", newCatalogo)

                    Log.d("MyApp", "Response code: ${response.code()}")

                    if (response.isSuccessful) {
                        // Si la solicitud fue exitosa, actualiza el RecyclerView
                        val catalogo = response.body()
                        if (catalogo != null) {
                            catalogoAdapter.addCatalogo(catalogo)
                        } else {
                            // Maneja el caso en que catalogo es null
                        }
                    } else {
                        // Si la solicitud no fue exitosa, muestra un mensaje de error
                        Toast.makeText(context, "Error al crear el catálogo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }



}