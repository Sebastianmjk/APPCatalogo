package com.example.appcatalogo.homePage.bar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo
import com.example.appcatalogo.apiConection.apiCatalogos.model.Catalogos
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class CatalogoAdapter(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val context: Context,
    val catalogos: MutableList<Catalogos>,
    private val accessToken: String?  // Agrega esta línea
) : RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>() {

    var onItemClick: ((Catalogos) -> Unit)? = null
    private val apiDelete = ApiCatalogo.apiDelete

    inner class CatalogoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var tituloCatalogo: TextView = view.findViewById(R.id.tvTituloTusCatalogos)
        var imagenCatalogo: ImageView = itemView.findViewById(R.id.ivCatalogo)
        var menuCatalogo: ImageView = view.findViewById(R.id.popMenu)

        fun bind(catalogo: Catalogos) {
            tituloCatalogo.text = catalogo.Nombre

            val imageUrl = if (catalogo.Portada.isNotEmpty()) catalogo.Portada else null

            Log.d("API", "URL de la imagen: $imageUrl")

            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.logo)
                    .into(imagenCatalogo, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                        }

                        override fun onError(e: Exception?) {
                            Log.d("API", "Error al cargar la imagen: $e")
                        }
                    })
            } else {
                Picasso.get().load(R.drawable.logo).into(imagenCatalogo)
            }

            menuCatalogo.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.show_menu, popup.menu)
                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete_catalogo -> {
                            deleteCatalogo(adapterPosition)
                            true
                        }
                        R.id.edit_catalogo -> {
                            editCatalogo(adapterPosition)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }

            view.setOnClickListener {
                onItemClick?.invoke(catalogo)
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
    }

    override fun getItemCount() = catalogos.size

    private fun deleteCatalogo(position: Int) {
        val catalogo = catalogos[position]

        lifecycleScope.launch {
            try {
                val response = apiDelete.deleteCatalogo("Bearer $accessToken",catalogo.id)

                if (response.isSuccessful) {
                    catalogos.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    Toast.makeText(context, "Error al eliminar el catálogo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun editCatalogo(position: Int) {
        val catalogo = catalogos[position]

        // Crea un diálogo para editar el catálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.edit_catalogo, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Editar catálogo")
            .setPositiveButton("Guardar") { _, _ ->
                val juegosString = dialogView.findViewById<EditText>(R.id.etIdJuegos).text.toString()

                // Verifica si el campo EditText está vacío
                if (juegosString.isBlank()) {
                    // Si el campo está vacío, muestra un mensaje de error y detén la edición
                    Toast.makeText(context, "El campo no puede estar vacío", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                // Convierte la lista de IDs de juegos a una lista de Int
                val juegos = juegosString.split(",").map { it.trim().toInt() }

                // Actualiza el catálogo con los nuevos juegos
                catalogo.juegos = juegos

                // Llama a la API en una corutina para actualizar el catálogo
                lifecycleScope.launch {
                    val response = ApiCatalogo.apiCatalogos.updateCatalogo("Bearer $accessToken", catalogo)

                    if (response.isSuccessful) {
                        // Si la solicitud fue exitosa, actualiza el RecyclerView
                        catalogos[position] = catalogo
                        notifyItemChanged(position)
                    } else {
                        // Si la solicitud no fue exitosa, muestra un mensaje de error
                        Toast.makeText(context, "Error al editar el catálogo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}