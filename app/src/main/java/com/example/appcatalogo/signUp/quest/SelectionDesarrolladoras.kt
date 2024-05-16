package com.example.appcatalogo.signUp.quest

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView

class SelectionDesarrolladoras : Fragment() {

    private lateinit var liContenedorSeleccion : LinearLayout
    private lateinit var liCargandoSeleccion : LinearLayout
    private lateinit var liCargandoSeleccion2 : LinearLayout

    private lateinit var adaptador : AdapterSelecciones
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_desarrolladoras, container, false)
    }

    private fun getSeleccionList() : ArrayList<Selecciones>{

        val seleccionesList : ArrayList<Selecciones> = ArrayList()
        seleccionesList.add(Selecciones(1, R.mipmap.activision, "Activision"))
        seleccionesList.add(Selecciones(2, R.mipmap.rockstargames, "Rockstar Games"))
        seleccionesList.add(Selecciones(3, R.mipmap.epicgames, "Epic Games"))
        seleccionesList.add(Selecciones(4, R.mipmap.ubisoft, "Ubisoft"))
        seleccionesList.add(Selecciones(5, R.mipmap.ea, "EA"))
        seleccionesList.add(Selecciones(6, R.mipmap.sega, "Sega"))
        seleccionesList.add(Selecciones(7, R.mipmap.nintendo, "Nintendo"))
        seleccionesList.add(Selecciones(8, R.mipmap.santamonica, "Santa Monica"))
        seleccionesList.add(Selecciones(9, R.mipmap.naughtydog, "Naughty Dog"))
        seleccionesList.add(Selecciones(10, R.mipmap.supercell, "Supercell"))
        return seleccionesList

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = GridLayoutManager(context, 2)
        recyclerView = view.findViewById(R.id.rvSeleccion)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adaptador = AdapterSelecciones(getSeleccionList())
        recyclerView.adapter = adaptador

        val buttonNext = view.findViewById<Button>(R.id.buttonNextSelection)
        val buttonBack = view.findViewById<Button>(R.id.buttonBackSelection)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_selectionDesarrolladoras_to_selectionDispositivos)
        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        adaptador.onItemClick = { seleccion, view ->
            view.setBackgroundColor(if (seleccion.seleccionado) Color.parseColor("#3F51B5") else Color.TRANSPARENT)
        }

        liCargandoSeleccion = view.findViewById(R.id.liCargandoSeleccion)
        liCargandoSeleccion2 = view.findViewById(R.id.liCargandoSeleccion2)
        liContenedorSeleccion = view.findViewById(R.id.liContenedorSeleccion)

        Handler(Looper.getMainLooper()).postDelayed({
            liCargandoSeleccion.isVisible = false
            liCargandoSeleccion2.isVisible = false
            liContenedorSeleccion.isVisible = true
        }, 500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
        recyclerView.layoutManager = null
        adaptador.clearData()
    }
}