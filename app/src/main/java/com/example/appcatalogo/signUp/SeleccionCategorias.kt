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
import com.example.appcatalogo.R

class SeleccionCategorias : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_de_categorias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<Button>(R.id.buttonNextSelection)
        val buttonBack = view.findViewById<Button>(R.id.buttonBackSelection)
        buttonNext.setOnClickListener {
            // findNavController().navigate(R.id.action_selectionDesarrolladoras_to_selectionDispositivos)
            println("Ultimo fragmento")
        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val recycler = view.findViewById<RecyclerView>(R.id.rvSeleccion)

        val LayoutManager = GridLayoutManager(this, 2)
        recycler.layoutManager = LayoutManager
    }
}