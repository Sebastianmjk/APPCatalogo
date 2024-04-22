package com.example.appcatalogo.signUp.quest

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

class SelectionDesarrolladoras : Fragment() {

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
        seleccionesList.add(Selecciones(1, R.mipmap.activision))
        seleccionesList.add(Selecciones(2, R.mipmap.rockstargames))
        seleccionesList.add(Selecciones(3, R.mipmap.epicgames))
        seleccionesList.add(Selecciones(4, R.mipmap.ubisoft))
        seleccionesList.add(Selecciones(5, R.mipmap.ea))
        seleccionesList.add(Selecciones(6, R.mipmap.sega))
        seleccionesList.add(Selecciones(7, R.mipmap.nintendo))
        seleccionesList.add(Selecciones(8, R.mipmap.santamonica))
        seleccionesList.add(Selecciones(9, R.mipmap.naughtydog))
        seleccionesList.add(Selecciones(10, R.mipmap.supercell))
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
    }
}