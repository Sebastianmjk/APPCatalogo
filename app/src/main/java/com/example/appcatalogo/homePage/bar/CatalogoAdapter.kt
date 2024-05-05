package com.example.appcatalogo.homePage.bar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogo

class CatalogoAdapter(private val catalogos: MutableList<Catalogo>) : RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>() {

    class CatalogoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var tituloCatalogo : TextView = view.findViewById(R.id.tvTituloTusCatalogos)
        var intCatalogo : TextView = view.findViewById(R.id.tvIdCatalogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogos, parent, false)
        return CatalogoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {
        val catalogo = catalogos[position]
        holder.intCatalogo.text = catalogo.juegos.toString()
        holder.tituloCatalogo.text = catalogo.Nombre
    }

    override fun getItemCount() = catalogos.size

    fun addCatalogo(catalogo: Catalogo) {
        // Agrega el nuevo catálogo a la lista
        catalogos.add(catalogo)

        // Notifica al RecyclerView que los datos han cambiado
        notifyDataSetChanged()
    }
}
