package com.example.appcatalogo.apiConection.apiJuegos.model

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


            val imageUrl = juego.images

            if (imageUrl.isNotEmpty()) {
                Picasso.get().load(imageUrl as String).into(imagenJuegos)
            } else {
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

