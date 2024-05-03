package com.example.appcatalogo.slideBar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

    }

}