package com.example.appcatalogo.signUp.quest

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R

class AdapterSelecciones(private val seleccioneslist : ArrayList<Selecciones>) : RecyclerView.Adapter<AdapterSelecciones.ViewHolder>() {

    lateinit var context : Context

    var onItemClick : ((Selecciones, View) -> Unit)? = null


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

        // Establece el color de fondo inicial
        holder.imageSeleccion.setBackgroundColor(if (seleccion.seleccionado) Color.parseColor("#3F51B5") else Color.TRANSPARENT)

        holder.imageSeleccion.setOnClickListener{
            val numSeleccionados = seleccioneslist.count { it.seleccionado }
            if (!seleccion.seleccionado && numSeleccionados >= 3) {
                // Si ya hay 3 elementos seleccionados, no permitas que se seleccionen más
                return@setOnClickListener
            }

            // Cambia el estado de seleccionado y el color de fondo
            seleccion.seleccionado = !seleccion.seleccionado
            it.setBackgroundColor(if (seleccion.seleccionado) Color.parseColor("#3F51B5") else Color.TRANSPARENT)

            onItemClick?.invoke(seleccion, it)
        }

    }
}