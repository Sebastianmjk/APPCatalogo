package com.example.appcatalogo.apiConection.apiJuegos.model

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiJuegos.ApiClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JuegoDetail : Fragment() {

    private lateinit var adaptadorJuegos : AdapterJuegos
    private lateinit var recyclerViewJuegos : RecyclerView
    private var tituloJuego: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_juego_detail, container, false)

        tituloJuego = arguments?.getString("titulo_juego")
        val textView = view.findViewById<TextView>(R.id.tx_juego_titulo_detail)
        textView.text = tituloJuego
        val resumenJuego = arguments?.getString("resumen_juego")
        val textView1 = view.findViewById<TextView>(R.id.tx_juego_resumen_detail)
        textView1.text = resumenJuego

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewJuegos = view.findViewById(R.id.rvRecomendaciones)
        recyclerViewJuegos.layoutManager = layoutManagerJuegos
        adaptadorJuegos = AdapterJuegos(ArrayList())
        recyclerViewJuegos.adapter = adaptadorJuegos

        loadJuegos()

        adaptadorJuegos.onItemClick = { juego ->
            val bundle = Bundle()
            bundle.putString("titulo_juego", juego.titulo)
            bundle.putString("resumen_juego", juego.resumen)
            findNavController().navigate(R.id.action_juegoDetail_self, bundle)
        }

        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)


        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_juegoDetail_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_juegoDetail_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_juegoDetail_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_juegoDetail_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_juegoDetail_to_perfil)
                }
            }
            true
        }

    }

    private fun loadJuegos() {
        ApiClient.apiRecomendation.listRecomendations(tituloJuego ?: "").enqueue(object :
            Callback<List<Result>> {
            override fun onResponse(
                call: Call<List<Result>>, response: Response<List<Result>>
            ) {
                if (response.isSuccessful) {
                    val juegos = response.body()
                    Log.d("API", "Juegos recibidos: $juegos")
                    if (juegos != null) {
                        adaptadorJuegos.juegosList.clear()
                        adaptadorJuegos.juegosList.addAll(juegos)
                        adaptadorJuegos.notifyDataSetChanged()
                    }
                } else {
                    Log.d("API", "Error en la respuesta: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                Log.d("API", "Error en la llamada: $t")
            }
        })
    }

}