package com.example.appcatalogo.apiConection.apiJuegos.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.squareup.picasso.Picasso


class AdapterJuegos(val juegosList : ArrayList<Result>) : RecyclerView.Adapter<AdapterJuegos.ViewHolder>() {

    var onItemClick : ((Result) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var idJuegos : Int = 0
        var tituloJuegos : TextView = itemView.findViewById(R.id.tx_titulo_juego)
        var imagenJuegos : ImageView = itemView.findViewById(R.id.ivGame)

        fun bind(juego: Result) {
            idJuegos = juego.id
            tituloJuegos.text = juego.titulo

            val imageUrl = if (juego.images.isNotEmpty()) juego.images[0] else null

            // Log de la URL de la imagen
            Log.d("API", "URL de la imagen: $imageUrl")

            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.logo) // muestra una imagen de error si la carga falla
                    .into(imagenJuegos, object : com.squareup.picasso.Callback {
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
                Picasso.get().load(R.drawable.logo).into(imagenJuegos)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_juego, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return juegosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val juego = juegosList[position]
        holder.bind(juego)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(juego)
        }
    }
}

