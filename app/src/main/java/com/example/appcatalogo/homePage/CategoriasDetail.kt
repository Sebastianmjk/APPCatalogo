    package com.example.appcatalogo.homePage

    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.appcatalogo.R
    import com.example.appcatalogo.apiConection.apiJuegos.ApiClient
    import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterJuegos
    import com.example.appcatalogo.apiConection.apiJuegos.model.RemoteResult
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response


    class CategoriasDetail : Fragment() {

        private lateinit var adaptadorJuegos: AdapterJuegos
        private lateinit var recyclerViewJuegos: RecyclerView
        private var nombreCategoria: String? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_categorias_detail, container, false)

            nombreCategoria = arguments?.getString("nombre_categoria")
            val textView = view.findViewById<TextView>(R.id.categoriaNombre)
            textView.text = nombreCategoria

            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val layoutManagerJuegos = LinearLayoutManager(context)
            recyclerViewJuegos = view.findViewById(R.id.rvCategoriasJuegos)
            recyclerViewJuegos.layoutManager = layoutManagerJuegos
            adaptadorJuegos = AdapterJuegos(ArrayList())
            recyclerViewJuegos.adapter = adaptadorJuegos

            loadGeneros()

            adaptadorJuegos.onItemClick = { juego ->
                val bundle = Bundle()
                bundle.putString("titulo_juego", juego.titulo)
                bundle.putString("resumen_juego", juego.resumen)
                findNavController().navigate(R.id.action_categoriasDetail_to_juegoDetail, bundle)
            }
        }
        private fun loadGeneros() {
            ApiClient.apiGenero.listGeneros(nombreCategoria ?: "", "20").enqueue(object : Callback<RemoteResult> {
                    override fun onResponse(
                        call: Call<RemoteResult>, response: Response<RemoteResult>) {
                        if (response.isSuccessful) {
                            val juegos = response.body()?.results
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

                    override fun onFailure(call: Call<RemoteResult>, t: Throwable) {
                        Log.d("API", "Error en la llamada: $t")
                    }
                })
        }
    }