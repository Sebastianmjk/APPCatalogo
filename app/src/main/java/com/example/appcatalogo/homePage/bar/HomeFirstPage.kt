package com.example.appcatalogo.homePage.bar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiJuegos.ApiClient
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterJuegos
import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
import com.example.appcatalogo.homePage.AdapterCategorias
import com.example.appcatalogo.homePage.Categorias
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFirstPage : Fragment() {

    private lateinit var adaptadorCategorias : AdapterCategorias
    private lateinit var recyclerViewCategorias : RecyclerView
    private lateinit var adaptadorJuegos : AdapterJuegos
    private lateinit var recyclerViewJuegos : RecyclerView

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_first_page, container, false)
    }

    private fun getCategoriasList() : ArrayList<Categorias>{

        val categoriasList : ArrayList<Categorias> = ArrayList()
        categoriasList.add(Categorias(1, R.mipmap.adventure, "Adventure"))
        categoriasList.add(Categorias(2, R.mipmap.shooter, "Shooter"))
        categoriasList.add(Categorias(3, R.mipmap.rpg, "RPG"))
        categoriasList.add(Categorias(4, R.mipmap.simulator, "Simulator"))
        categoriasList.add(Categorias(5, R.mipmap.puzzle, "Puzzle"))
        categoriasList.add(Categorias(6, R.mipmap.musica, "Music"))
        categoriasList.add(Categorias(7, R.mipmap.arcade, "Arcade"))
        categoriasList.add(Categorias(8, R.mipmap.deportes, "Sport"))
        categoriasList.add(Categorias(9, R.mipmap.estrategia, "Strategy"))
        categoriasList.add(Categorias(10, R.mipmap.plataformas, "Platform"))
        return categoriasList

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        }

        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!
        navView = activity?.findViewById(R.id.nav_view)
        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)

        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        navView?.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)



        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_homeFirstPage_self)
                }
                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_homeFirstPage_to_homeUsuario)
                }
                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_homeFirstPage_to_crear)
                }
                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_homeFirstPage_to_buscar)
                }
                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_homeFirstPage_to_perfil)
                }
            }
            true
        }

        val layoutManagerCategorias = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategorias = view.findViewById(R.id.rvHomePageCategorias)
        recyclerViewCategorias.layoutManager = layoutManagerCategorias
        adaptadorCategorias = AdapterCategorias(getCategoriasList())
        recyclerViewCategorias.adapter = adaptadorCategorias

        adaptadorCategorias.onItemClick = { categoria ->
            val bundle = Bundle()
            bundle.putString("nombre_categoria", categoria.nombre)
            findNavController().navigate(R.id.action_homeFirstPage_to_categoriasDetail, bundle)
        }

        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewJuegos = view.findViewById(R.id.rvHomePageJuegos)
        recyclerViewJuegos.layoutManager = layoutManagerJuegos
        adaptadorJuegos = AdapterJuegos(ArrayList())
        recyclerViewJuegos.adapter = adaptadorJuegos

        loadGames()

        adaptadorJuegos.onItemClick = { juego ->
            val bundle = Bundle()
            bundle.putString("titulo_juego", juego.titulo)
            bundle.putString("resumen_juego", juego.resumen)
            if (juego.images.isNotEmpty()) {
                bundle.putString("imagen_juego", juego.images[0])
            }
            findNavController().navigate(R.id.action_homeFirstPage_to_juegoDetail, bundle)
        }
    }

    private fun loadGames() {
        ApiClient.apiService.listJuegos("20").enqueue(object : Callback<RemoteResult> {
            override fun onResponse(call: Call<RemoteResult>, response: Response<RemoteResult>) {
                if (response.isSuccessful) {
                    val juegos = response.body()?.results
                    Log.d("API", "Juegos recibidos: $juegos")
                    if (juegos != null) {
                        adaptadorJuegos.juegosList.clear()
                        adaptadorJuegos.juegosList.addAll(juegos)
                        adaptadorJuegos.notifyDataSetChanged()
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

}