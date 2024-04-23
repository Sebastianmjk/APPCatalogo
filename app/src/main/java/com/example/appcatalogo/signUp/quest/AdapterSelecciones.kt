package com.example.appcatalogo.signUp.quest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R

class AdapterSelecciones(private val seleccioneslist : ArrayList<Selecciones>) : RecyclerView.Adapter<AdapterSelecciones.ViewHolder>() {

    lateinit var context : Context

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var idSeleccion : Int = 0
        var imageSeleccion : ImageView = itemView.findViewById(R.id.ivSelecciones)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_selecciones, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return seleccioneslist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val seleccion = seleccioneslist[position]
        holder.idSeleccion = seleccion.id
        holder.imageSeleccion.setImageResource(seleccion.image)


    }
}