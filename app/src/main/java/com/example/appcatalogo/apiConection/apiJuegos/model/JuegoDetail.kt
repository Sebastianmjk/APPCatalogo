package com.example.appcatalogo.apiConection.apiJuegos.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appcatalogo.R


class JuegoDetail : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_juego_detail, container, false)

        val tituloJuego = arguments?.getString("titulo_juego")
        val textView = view.findViewById<TextView>(R.id.tx_juego_titulo_detail)
        textView.text = tituloJuego
        val resumenJuego = arguments?.getString("resumen_juego")
        val textView1 = view.findViewById<TextView>(R.id.tx_juego_resumen_detail)
        textView1.text = resumenJuego

        return view
    }

}