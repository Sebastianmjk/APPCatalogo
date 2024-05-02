package com.example.appcatalogo.apiConection.apiJuegos.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R


class AdapterJuegos(val juegosList : ArrayList<Result>) : RecyclerView.Adapter<AdapterJuegos.ViewHolder>() {

    var onItemClick : ((Result) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var idJuegos : Int = 0
        var tituloJuegos : TextView = itemView.findViewById(R.id.tx_titulo_juego)

        fun bind(juego: Result) {
            idJuegos = juego.id
            tituloJuegos.text = juego.titulo
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
        holder.tituloJuegos.setOnClickListener{
            onItemClick?.invoke(juego)
        }
    }
}
