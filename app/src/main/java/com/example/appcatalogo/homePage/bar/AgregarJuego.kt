package com.example.appcatalogo.homePage.bar

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
import android.widget.TextView
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
import com.example.appcatalogo.apiConection.apiCatalogos.model.AddJuego
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogo
import com.example.appcatalogo.apiConection.apiJuegos.ApiClient
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterAgregar
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterCrear
import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AgregarJuego : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private val accessToken = TokenManager.accessToken
    private var idCatalogo: Int? = null

    private lateinit var recyclerViewAgregar: RecyclerView
    private lateinit var adapterAgregar : AdapterAgregar

    private lateinit var searchAgregar: SearchView
    private lateinit var botonAgregar: Button

    private lateinit var liCargandoAgregar: LinearLayout
    private lateinit var liContenedorAgregar: LinearLayout

    val listGame = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agregar_juego, container, false)
        idCatalogo = arguments?.getInt("catalogo_id")


        return view
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
                    findNavController().navigate(R.id.action_agregarJuego_to_perfil)
                    true
                }

                R.id.nav_item_inicio -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_homeFirstPage)
                    true
                }

                R.id.nav_item_categorias -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_categoriasSlideBar2)
                    true
                }

                R.id.nav_item_mis_catalogos -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_homeUsuario)
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
                    findNavController().navigate(R.id.action_agregarJuego_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_agregarJuego_to_perfil)
                }
            }
            true
        }

        botonAgregar = view.findViewById(R.id.buttonAgregar)



        botonAgregar.setOnClickListener {
            it.isEnabled = false
            it.postDelayed({
                it.isEnabled = true // Habilita el botón de nuevo después de un retraso
            }, 2000)
            val newJuegos = AddJuego(listGame)
            lifecycleScope.launch {
                if (addJuegoToCatalogo(newJuegos)){

                    listGame.clear()

                    adapterAgregar.juegosList.clear()
                    adapterAgregar.notifyDataSetChanged()

                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_agregarJuego_to_homeUsuario)
                    }
                }
            }

        }
        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewAgregar = view.findViewById(R.id.rvAgregar)
        recyclerViewAgregar.layoutManager = layoutManagerJuegos
        adapterAgregar = AdapterAgregar(ArrayList(), this)
        recyclerViewAgregar.adapter = adapterAgregar

        searchAgregar = view.findViewById(R.id.searchViewAgregar)
        searchAgregar.setOnClickListener {
            searchAgregar.isIconified = false
        }

        searchAgregar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                adapterAgregar.juegosList.clear()
                adapterAgregar.notifyDataSetChanged()
            }
        }



        liContenedorAgregar = view.findViewById(R.id.liContenedorAgregar)
        liCargandoAgregar = view.findViewById(R.id.liCargandoAgregar)

        liCargandoAgregar.isVisible = false
        liContenedorAgregar.isVisible = true


    searchAgregar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrEmpty()) {
                liCargandoAgregar.isVisible = true
                liContenedorAgregar.isVisible = false
                loadGames(query)
                Handler(Looper.getMainLooper()).postDelayed({
                    liCargandoAgregar.isVisible = false
                    liContenedorAgregar.isVisible = true
                }, 1000)
            }
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchAgregar.windowToken, 0)
            searchAgregar.clearFocus()
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
                        adapterAgregar.juegosList.clear()
                        adapterAgregar.juegosList.addAll(juegos)
                        adapterAgregar.notifyDataSetChanged()
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


    private suspend fun addJuegoToCatalogo(listGame : AddJuego) : Boolean {
        if (listGame.isEmpty()) {
            Toast.makeText(context, "No se puede agregar un juego vacío", Toast.LENGTH_SHORT).show()
            return false
        }

        return try {
                    val response = ApiCatalogo.apiAddJuego.addJuegoToCatalogo("Bearer $accessToken", idCatalogo!!, listGame)


                    if (response.isSuccessful) {
                        Toast.makeText(context, "Juego agregado", Toast.LENGTH_SHORT).show()
                        adapterAgregar.notifyDataSetChanged()
                        Log.d("API", "Juego ${response.body()}")
                        true
                    } else {
                        Toast.makeText(context, "Error al agregar el juego al catálogo", Toast.LENGTH_SHORT).show()
                        false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    false
                }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

}