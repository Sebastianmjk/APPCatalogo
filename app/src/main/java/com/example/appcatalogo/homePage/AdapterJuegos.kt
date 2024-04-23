package com.example.appcatalogo.homePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R

class AdapterJuegos(private val juegosList : ArrayList<Juegos>) : RecyclerView.Adapter<AdapterJuegos.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var idJuegos : Int = 0
        var tituloJuegos : TextView = itemView.findViewById(R.id.tx_titulo_juego)
        var imageJuegos : ImageView = itemView.findViewById(R.id.iv_imagen_juego)
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
        holder.idJuegos = juego.id
        holder.tituloJuegos.text = juego.titulo
        holder.imageJuegos.setImageResource(juego.imagen)
    }
}
