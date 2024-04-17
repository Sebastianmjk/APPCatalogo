package com.example.appcatalogo.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.AdapterSelecciones
import com.example.appcatalogo.R
import com.example.appcatalogo.Selecciones

class SeleccionCategorias : Fragment() {

    private lateinit var adaptador : AdapterSelecciones
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_de_categorias, container, false)
    }

    fun getSeleccionList() : ArrayList<Selecciones>{

        var seleccionesList : ArrayList<Selecciones> = ArrayList()
        seleccionesList.add(Selecciones(1, R.mipmap.brujulas))
        seleccionesList.add(Selecciones(2, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(3, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(4, R.mipmap.ic_launcher_round))
        seleccionesList.add(Selecciones(5, R.mipmap.ic_launcher_round))
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
            // findNavController().navigate(R.id.action_selectionDesarrolladoras_to_selectionDispositivos)
            println("Ultimo fragmento")
        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }
}