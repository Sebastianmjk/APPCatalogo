    package com.example.appcatalogo.homePage

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import com.example.appcatalogo.R


    class CategoriasDetail : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_categorias_detail, container, false)

            val nombreCategoria = arguments?.getString("nombre_categoria")
            val textView = view.findViewById<TextView>(R.id.categoriaNombre)
            textView.text = nombreCategoria

            return view
        }



    }