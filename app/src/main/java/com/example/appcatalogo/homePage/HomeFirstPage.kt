package com.example.appcatalogo.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R

class HomeFirstPage : Fragment() {

    private lateinit var adaptadorCategorias : AdapterCategorias
    private lateinit var recyclerViewCategorias : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_first_page, container, false)
        // Inflate the layout for this fragment
    }

    private fun getCategoriasList() : ArrayList<Categorias>{

        val categoriasList : ArrayList<Categorias> = ArrayList()
        categoriasList.add(Categorias(1, R.mipmap.adventure, "Adventure"))
        categoriasList.add(Categorias(2, R.mipmap.shooter, "Shooter"))
        categoriasList.add(Categorias(3, R.mipmap.rpg, "RPG"))
        categoriasList.add(Categorias(4, R.mipmap.simulator, "Simulator"))
        categoriasList.add(Categorias(5, R.mipmap.puzzle, "Puzzle"))
        categoriasList.add(Categorias(6, R.mipmap.musica, "Musica"))
        categoriasList.add(Categorias(7, R.mipmap.arcade, "Arcade"))
        categoriasList.add(Categorias(8, R.mipmap.deportes, "Deportes"))
        categoriasList.add(Categorias(9, R.mipmap.estrategia, "Estrategia"))
        categoriasList.add(Categorias(10, R.mipmap.plataformas, "Plataformas"))
        return categoriasList

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategorias = view.findViewById(R.id.rvHomePageCategorias)
        recyclerViewCategorias.layoutManager = layoutManager
        adaptadorCategorias = AdapterCategorias(getCategoriasList())
        recyclerViewCategorias.adapter = adaptadorCategorias

        adaptadorCategorias.onItemClick = { categoria ->
            val bundle = Bundle()
            bundle.putString("nombre_categoria", categoria.nombre)
            findNavController().navigate(R.id.action_homeFirstPage_to_categoriasDetail, bundle)

        }

    }
}