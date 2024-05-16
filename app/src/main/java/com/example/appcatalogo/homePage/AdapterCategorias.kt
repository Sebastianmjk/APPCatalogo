package com.example.appcatalogo.homePage


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R

class AdapterCategorias(private val categoriaslist : ArrayList<Categorias>) : RecyclerView.Adapter<AdapterCategorias.ViewHolder>() {

    lateinit var context : Context

    var onItemClick : ((Categorias) -> Unit)? = null


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var idCaregoria : Int = 0
        var imageCategoria : ImageView = itemView.findViewById(R.id.ivCategorias)
        var nombreCategoria : TextView = itemView.findViewById(R.id.txCategoriasNombre)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_categorias, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoriaslist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categoriaslist[position]
        holder.idCaregoria = categoria.id
        holder.imageCategoria.setImageResource(categoria.image)
        holder.nombreCategoria.text = categoria.nombre

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(categoria)
        }
    }
    fun clearData() {
        categoriaslist.clear()
        notifyDataSetChanged()
    }
}