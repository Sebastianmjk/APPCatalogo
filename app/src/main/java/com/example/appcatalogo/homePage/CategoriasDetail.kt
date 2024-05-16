    package com.example.appcatalogo.homePage

    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.coordinatorlayout.widget.CoordinatorLayout
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
    import com.google.android.material.navigation.NavigationView
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response


    class CategoriasDetail : Fragment() {

        private lateinit var adaptadorJuegos: AdapterJuegos
        private lateinit var recyclerViewJuegos: RecyclerView
        private var nombreCategoria: String? = null

        private lateinit var drawerLayout: DrawerLayout
        private var navView: NavigationView? = null
        private var appBarLayout: AppBarLayout? = null
        private var coordinatorLayout: CoordinatorLayout? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_categorias_detail, container, false)

            nombreCategoria = arguments?.getString("nombre_categoria")
            val textView = view.findViewById<TextView>(R.id.categoriaNombre)
            textView.text = nombreCategoria

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
                        findNavController().navigate(R.id.action_categoriasDetail_to_perfil)
                        true
                    }
                    R.id.nav_item_inicio -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_homeFirstPage)
                        true
                    }
                    R.id.nav_item_categorias -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_categoriasSlideBar2)
                        true
                    }

                    R.id.nav_item_mis_catalogos -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_homeUsuario)
                        true
                    }
                    R.id.nav_item_cerrar_sesion -> {
                        findNavController().popBackStack(R.id.loginFragment, false)
                        true

                    }
                    else -> false
                }
            }

            val layoutManagerJuegos = LinearLayoutManager(context)
            recyclerViewJuegos = view.findViewById(R.id.rvCategoriasJuegos)
            recyclerViewJuegos.layoutManager = layoutManagerJuegos
            adaptadorJuegos = AdapterJuegos(ArrayList())
            recyclerViewJuegos.adapter = adaptadorJuegos

            loadGeneros()

            adaptadorJuegos.onItemClick = { juego ->
                val bundle = Bundle()
                bundle.putString("titulo_juego", juego.titulo)
                bundle.putString("resumen_juego", juego.resumen)
                if (juego.images.isNotEmpty()) {
                    bundle.putString("imagen_juego", juego.images[0])
                }
                findNavController().navigate(R.id.action_categoriasDetail_to_juegoDetail, bundle)
            }

            val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)



            navView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_icono -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_homeFirstPage)
                    }

                    R.id.home_usuario_icono -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_homeUsuario)
                    }

                    R.id.agregar_icono -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_crear)
                    }

                    R.id.search_icono -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_buscar)
                    }

                    R.id.profile_icono -> {
                        findNavController().navigate(R.id.action_categoriasDetail_to_perfil)
                    }
                }
                true
            }

        }

        private fun loadGeneros() {
            ApiClient.apiGenero.listGeneros(nombreCategoria ?: "", "20").enqueue(object : Callback<RemoteResult> {
                    override fun onResponse(
                        call: Call<RemoteResult>, response: Response<RemoteResult>) {
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

        override fun onDestroyView() {
            super.onDestroyView()
            coordinatorLayout?.visibility = View.GONE
            appBarLayout?.visibility = View.GONE
            navView?.visibility = View.GONE
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            recyclerViewJuegos.adapter = null
            adaptadorJuegos.juegosList.clear()
        }
    }