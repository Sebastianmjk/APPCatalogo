package com.example.appcatalogo.homePage.bar

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogo
import com.example.appcatalogo.apiConection.apiJuegos.ApiClient
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterCrear
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterJuegos
import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.example.appcatalogo.showError.showError
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class Crear : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private val accessToken = TokenManager.accessToken

    private lateinit var recyclerViewCrear: RecyclerView
    private lateinit var adapterCrear: AdapterCrear

    private lateinit var search: SearchView
    private lateinit var botonCrear : Button

    private lateinit var liCargandoCargar: LinearLayout
    private lateinit var liContenedorCargar: LinearLayout


    val listGame = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear, container, false)
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
                    findNavController().navigate(R.id.action_crear_to_perfil)
                    true
                }

                R.id.nav_item_inicio -> {
                    findNavController().navigate(R.id.action_crear_to_homeFirstPage)
                    true
                }

                R.id.nav_item_categorias -> {
                    findNavController().navigate(R.id.action_crear_to_categoriasSlideBar2)
                    true
                }

                R.id.nav_item_mis_catalogos -> {
                    findNavController().navigate(R.id.action_crear_to_homeUsuario)
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
                    findNavController().navigate(R.id.action_crear_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_crear_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_crear_self)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_crear_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_crear_to_perfil)
                }
            }
            true
        }

        botonCrear = view.findViewById(R.id.buttonCrear)



        botonCrear.setOnClickListener {
            val nombre = view.findViewById<EditText>(R.id.etTituloCatalogo).text.toString()
            val newCatalogo  = Catalogo(nombre, listGame)
            if (nombre.isBlank()) {
                Toast.makeText(context, "Debe colocar el nombre del catalogo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                if (tryCreateCatalogo(newCatalogo)) {
                    val etTituloCatalogo: EditText = requireView().findViewById(R.id.etTituloCatalogo)
                    etTituloCatalogo.text.clear()

                    listGame.clear()

                    adapterCrear.juegosList.clear()
                    adapterCrear.notifyDataSetChanged()
                }
            }

        }


        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewCrear = view.findViewById(R.id.rvCrear)
        recyclerViewCrear.layoutManager = layoutManagerJuegos
        adapterCrear = AdapterCrear(ArrayList(),this)
        recyclerViewCrear.adapter = adapterCrear

        search = view.findViewById(R.id.searchView)
        search.setOnClickListener {
            search.isIconified = false
        }

        search.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                adapterCrear.juegosList.clear()
                adapterCrear.notifyDataSetChanged()
            }
        }



        liContenedorCargar = view.findViewById(R.id.liContenedorCrear)
        liCargandoCargar = view.findViewById(R.id.liCargandoCrear)

        liCargandoCargar.isVisible = false
        liContenedorCargar.isVisible = true

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    liCargandoCargar.isVisible = true
                    liContenedorCargar.isVisible = false
                    loadGames(query)
                    Handler(Looper.getMainLooper()).postDelayed({
                        liCargandoCargar.isVisible = false
                        liContenedorCargar.isVisible = true
                    }, 1000)
                }
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(search.windowToken, 0)
                search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }
    fun loadGamesToList(idJuego : Int) {
        listGame.add(idJuego)
    }

    fun removeGameToList(idJuego : Int) {
        if (listGame.contains(idJuego)){
            listGame.remove(idJuego)
        }
    }

    private fun loadGames(titulo: String) {
        ApiClient.apiBuscar.listBuscar(titulo).enqueue(object : Callback<RemoteResult> {
            override fun onResponse(call: Call<RemoteResult>, response: Response<RemoteResult>) {
                if (response.isSuccessful) {
                    val juegos = response.body()?.results
                    Log.d("API", "Juegos recibidos: $juegos")
                    if (juegos != null && juegos.isNotEmpty()) {
                        adapterCrear.juegosList.clear()
                        adapterCrear.juegosList.addAll(juegos)
                        adapterCrear.notifyDataSetChanged()
                    } else {
                        // Muestra un Toast cuando no se encuentren resultados
                        Toast.makeText(context, "Juego no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("API", "Error en la respuesta: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RemoteResult>, t: Throwable) {
                Log.d("API", "Error en la llamada: $t")
            }
        })
    }

    private suspend fun tryCreateCatalogo(catalogo: Catalogo): Boolean {
        return try {
            val response = ApiCatalogo.apiCatalogos.createCatalogo("Bearer $accessToken", catalogo)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Catálogo creado", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No se pudo crear el catálogo", Toast.LENGTH_SHORT).show()
                }
                false
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error de conexión de red", Toast.LENGTH_SHORT).show()
            }
            false
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error de conexión de red", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

}