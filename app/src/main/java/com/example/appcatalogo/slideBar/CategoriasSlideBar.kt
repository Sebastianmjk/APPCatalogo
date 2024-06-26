package com.example.appcatalogo.slideBar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.homePage.Categorias
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class CategoriasSlideBar : Fragment() {

    private lateinit var liContenedorCategoriasSlideBar : LinearLayout
    private lateinit var liCargandoCategoriasSlideBar2 : LinearLayout
    private lateinit var liCargandoCategoriasSlideBar : LinearLayout

    private lateinit var adaptador : AdapterCategoriasSlideBar
    private lateinit var recyclerViewCategoriasSlideBar: RecyclerView

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias_slide_bar, container, false)
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
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_perfil)
                    true
                }
                R.id.nav_item_inicio -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_homeFirstPage)
                    true
                }
                R.id.nav_item_categorias -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_self)
                    true
                }

                R.id.nav_item_mis_catalogos -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_homeUsuario)
                    true
                }
                R.id.nav_item_cerrar_sesion -> {
                    findNavController().popBackStack(R.id.loginFragment, false)
                    true

                }
                else -> false
            }
        }

        val layoutManagerCategorias = GridLayoutManager(context, 2)
        recyclerViewCategoriasSlideBar = view.findViewById(R.id.rvCategoriasSlideBar)
        recyclerViewCategoriasSlideBar.layoutManager = layoutManagerCategorias
        adaptador = AdapterCategoriasSlideBar(getCategoriasList())
        recyclerViewCategoriasSlideBar.adapter = adaptador

        adaptador.onItemClick = { categoria ->
            val bundle = Bundle()
            bundle.putString("nombre_categoria", categoria.nombre)
            findNavController().navigate(R.id.action_categoriasSlideBar2_to_categoriasDetail, bundle)
        }

        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)



        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_categoriasSlideBar2_to_perfil)
                }
            }
            true
        }

        liCargandoCategoriasSlideBar2 = view.findViewById(R.id.liCargandoCategoriasSlideBar2)
        liCargandoCategoriasSlideBar = view.findViewById(R.id.liCargandoCategoriasSlideBar)
        liContenedorCategoriasSlideBar = view.findViewById(R.id.liContenedorCategoriasSlideBar)

        Handler(Looper.getMainLooper()).postDelayed({
            liCargandoCategoriasSlideBar2.isVisible = false
            liCargandoCategoriasSlideBar.isVisible = false
            liContenedorCategoriasSlideBar.isVisible = true
        }, 500)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        recyclerViewCategoriasSlideBar.adapter = null
        adaptador.clearData()
    }

}