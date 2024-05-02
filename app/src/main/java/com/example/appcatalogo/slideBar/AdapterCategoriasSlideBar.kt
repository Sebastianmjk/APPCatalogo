package com.example.appcatalogo.slideBar


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.homePage.Categorias

class AdapterCategoriasSlideBar(private val categoriaslist : ArrayList<Categorias>) : RecyclerView.Adapter<AdapterCategoriasSlideBar.ViewHolder>() {

    lateinit var context : Context

    var onItemClick : ((Categorias) -> Unit)? = null


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var idCaregoria : Int = 0
        var imageCategoria : ImageView = itemView.findViewById(R.id.ivSelecciones)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_selecciones, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoriaslist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categoriaslist[position]
        holder.idCaregoria = categoria.id
        holder.imageCategoria.setImageResource(categoria.image)

        holder.imageCategoria.setOnClickListener{
            onItemClick?.invoke(categoria)
        }
    }
}