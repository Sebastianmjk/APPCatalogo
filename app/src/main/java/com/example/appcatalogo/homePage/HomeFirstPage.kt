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

    private lateinit var adaptador : AdapterSelecciones
    private lateinit var recyclerViewCategorias : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_first_page, container, false)
    }

    private fun getSeleccionList() : ArrayList<Selecciones>{

        val seleccionesList : ArrayList<Selecciones> = ArrayList()
        seleccionesList.add(Selecciones(1, R.mipmap.adventure))
        seleccionesList.add(Selecciones(2, R.mipmap.shooter))
        seleccionesList.add(Selecciones(3, R.mipmap.rpg))
        seleccionesList.add(Selecciones(4, R.mipmap.simulator))
        seleccionesList.add(Selecciones(5, R.mipmap.puzzle))
        seleccionesList.add(Selecciones(6, R.mipmap.musica))
        seleccionesList.add(Selecciones(7, R.mipmap.arcade))
        seleccionesList.add(Selecciones(8, R.mipmap.deportes))
        seleccionesList.add(Selecciones(9, R.mipmap.estrategia))
        seleccionesList.add(Selecciones(10, R.mipmap.plataformas))
        return seleccionesList

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategorias = view.findViewById(R.id.rvHomePageCategorias)
        recyclerViewCategorias.layoutManager = layoutManager
        adaptador = AdapterSelecciones(getSeleccionList())
        recyclerViewCategorias.adapter = adaptador

    }
}