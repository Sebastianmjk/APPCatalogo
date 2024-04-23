package com.example.appcatalogo.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.signUp.quest.AdapterSelecciones
import com.example.appcatalogo.signUp.quest.Selecciones

class HomeFirstPage : Fragment() {

    private lateinit var adaptador : AdapterCategorias
    private lateinit var recyclerViewCategorias : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_first_page, container, false)
    }

    private fun getCategoriasList() : ArrayList<Categorias>{

        val categoriasList : ArrayList<Categorias> = ArrayList()
        categoriasList.add(Categorias(1, R.mipmap.adventure))
        categoriasList.add(Categorias(2, R.mipmap.shooter))
        categoriasList.add(Categorias(3, R.mipmap.rpg))
        categoriasList.add(Categorias(4, R.mipmap.simulator))
        categoriasList.add(Categorias(5, R.mipmap.puzzle))
        categoriasList.add(Categorias(6, R.mipmap.musica))
        categoriasList.add(Categorias(7, R.mipmap.arcade))
        categoriasList.add(Categorias(8, R.mipmap.deportes))
        categoriasList.add(Categorias(9, R.mipmap.estrategia))
        categoriasList.add(Categorias(10, R.mipmap.plataformas))
        return categoriasList

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategorias = view.findViewById(R.id.rvHomePageCategorias)
        recyclerViewCategorias.layoutManager = layoutManager
        adaptador = AdapterCategorias(getCategoriasList())
        recyclerViewCategorias.adapter = adaptador

    }
}