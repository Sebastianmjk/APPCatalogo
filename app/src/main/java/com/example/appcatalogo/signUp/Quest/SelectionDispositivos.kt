package com.example.appcatalogo.signUp.Quest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R

class SelectionDispositivos : Fragment() {

    private lateinit var adaptador : AdapterSelecciones
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_dispositivos, container, false)
    }

    private fun getSeleccionList() : ArrayList<Selecciones>{

        var seleccionesList : ArrayList<Selecciones> = ArrayList()
        seleccionesList.add(Selecciones(1, R.mipmap.adventure))
        seleccionesList.add(Selecciones(2, R.mipmap.shooter))
        seleccionesList.add(Selecciones(3, R.mipmap.rpg))
        seleccionesList.add(Selecciones(4, R.mipmap.simulator))
        seleccionesList.add(Selecciones(5, R.mipmap.puzzle))
        seleccionesList.add(Selecciones(6, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(7, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(8, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(9, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(10, R.mipmap.ic_launcher_round))
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
            findNavController().navigate(R.id.action_selectionDispositivos_to_seleccionCategorias)
        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}