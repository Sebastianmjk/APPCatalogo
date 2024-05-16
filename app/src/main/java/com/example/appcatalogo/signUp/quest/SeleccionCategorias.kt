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
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.homePage.Categorias


class SeleccionCategorias : Fragment() {

    private lateinit var liContenedorSeleccion : LinearLayout
    private lateinit var liCargandoSeleccion : LinearLayout
    private lateinit var liCargandoSeleccion2 : LinearLayout

    private lateinit var adaptador : AdapterSelecciones
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_de_categorias, container, false)
    }

    private fun getSeleccionList() : ArrayList<Selecciones>{

        val seleccionesList : ArrayList<Selecciones> = ArrayList()
        seleccionesList.add(Selecciones(1, R.mipmap.adventure, "Adventure"))
        seleccionesList.add(Selecciones(2, R.mipmap.shooter, "Shooter"))
        seleccionesList.add(Selecciones(3, R.mipmap.rpg, "RPG"))
        seleccionesList.add(Selecciones(4, R.mipmap.simulator, "Simulator"))
        seleccionesList.add(Selecciones(5, R.mipmap.puzzle, "Puzzle"))
        seleccionesList.add(Selecciones(6, R.mipmap.musica, "Music"))
        seleccionesList.add(Selecciones(7, R.mipmap.arcade, "Arcade"))
        seleccionesList.add(Selecciones(8, R.mipmap.deportes, "Sport"))
        seleccionesList.add(Selecciones(9, R.mipmap.estrategia, "Strategy"))
        seleccionesList.add(Selecciones(10, R.mipmap.plataformas, "Platform"))
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
            findNavController().navigate(R.id.action_seleccionCategorias_to_homeFirstPage)
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