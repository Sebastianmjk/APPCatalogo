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
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiJuegos.ApiClient
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterJuegos
import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Buscar : Fragment() {

    private lateinit var liContenedorBuscar : LinearLayout
    private lateinit var liCargandoBuscar : LinearLayout

    private lateinit var drawerLayout: DrawerLayout
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private lateinit var search : SearchView

    private lateinit var adaptadorJuegos : AdapterJuegos
    private lateinit var recyclerViewJuegos : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        liContenedorBuscar = view.findViewById(R.id.liContenedorBuscar)
        liCargandoBuscar = view.findViewById(R.id.liCargandoBuscar)

        liCargandoBuscar.isVisible = false
        liContenedorBuscar.isVisible = true

       search = view.findViewById(R.id.searchViewBuscar)
        search.setOnClickListener {
            search.isIconified = false
        }

        search.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                adaptadorJuegos.juegosList.clear()
                adaptadorJuegos.notifyDataSetChanged()
            }
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    liCargandoBuscar.isVisible = true
                    liContenedorBuscar.isVisible = false
                    loadGames(query)
                    Handler(Looper.getMainLooper()).postDelayed({
                        liCargandoBuscar.isVisible = false
                        liContenedorBuscar.isVisible = true
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


        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!
        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)

        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewJuegos = view.findViewById(R.id.rvBuscar)
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
            findNavController().navigate(R.id.action_buscar_to_juegoDetail, bundle)
        }


        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)



        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_buscar_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_buscar_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_buscar_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_buscar_self)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_buscar_to_perfil)
                }
            }
            true
        }


    }
    private fun loadGames(titulo: String) {
        ApiClient.apiBuscar.listBuscar(titulo).enqueue(object : Callback<RemoteResult> {
            override fun onResponse(call: Call<RemoteResult>, response: Response<RemoteResult>) {
                if (response.isSuccessful) {
                    val juegos = response.body()?.results
                    Log.d("API", "Juegos recibidos: $juegos")
                    if (juegos != null && juegos.isNotEmpty()) {
                        adaptadorJuegos.juegosList.clear()
                        adaptadorJuegos.juegosList.addAll(juegos)
                        adaptadorJuegos.notifyDataSetChanged()
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

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerViewJuegos.adapter = null
        adaptadorJuegos.juegosList.clear()
    }

}