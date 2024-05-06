package com.example.appcatalogo.homePage.bar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogo
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogos
import com.example.appcatalogo.apiConection.apiJuegos.model.Result
import com.squareup.picasso.Picasso

class CatalogoAdapter( val catalogos: MutableList<Catalogos>) : RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>() {

    var onItemClick : ((Catalogos) -> Unit)? = null

    class CatalogoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var tituloCatalogo : TextView = view.findViewById(R.id.tvTituloTusCatalogos)
        var imagenCatalogo : ImageView = itemView.findViewById(R.id.ivCatalogo)

        fun bind(catalogo: Catalogos) {
            tituloCatalogo.text = catalogo.Nombre

            val imageUrl = if (catalogo.Portada.isNotEmpty()) catalogo.Portada else null

            // Log de la URL de la imagen
            Log.d("API", "URL de la imagen: $imageUrl")

            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.logo) // muestra una imagen de error si la carga falla
                    .into(imagenCatalogo, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            // La imagen se cargó correctamente
                        }

                        override fun onError(e: Exception?) {
                            // Hubo un error al cargar la imagen
                            Log.d("API", "Error al cargar la imagen: $e")
                        }
                    })
            } else {
                // Cargar una imagen predeterminada de la carpeta drawable
                Picasso.get().load(R.drawable.logo).into(imagenCatalogo)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogos, parent, false)
        return CatalogoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {
        val catalogo = catalogos[position]
        holder.bind(catalogo)


        holder.view.setOnClickListener {
            onItemClick?.invoke(catalogo)
        }
    }

    override fun getItemCount() = catalogos.size

}
