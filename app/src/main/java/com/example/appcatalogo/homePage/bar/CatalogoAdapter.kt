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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
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
    private val accessToken: String?,
    private val navController: NavController
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
                            requestJuegoId { juegoId ->
                                addJuegoToCatalogo(adapterPosition, juegoId)
                            }
                            true
                        }
                        R.id.eliminar_juego -> {
                            requestJuegoId { juegoId ->
                                deleteJuegoFromCatalogo(adapterPosition, juegoId)
                            }
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

    private fun addJuegoToCatalogo(position: Int, juegoId: Int) {
        val catalogo = catalogos[position]

        lifecycleScope.launch {
            try {
                val response = ApiCatalogo.apiAddJuego.addJuegoToCatalogo("Bearer $accessToken", catalogo.id, juegoId)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Juego agregado", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                    navController.navigate(R.id.action_homeUsuario_self)
                } else {
                    Toast.makeText(context, "Error al agregar el juego al catálogo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteJuegoFromCatalogo(position: Int, juegoId: Int) {
        val catalogo = catalogos[position]

        lifecycleScope.launch {
            try {
                val response = ApiCatalogo.apiDeleteJuego.deleteJuegoFromCatalogo("Bearer $accessToken", catalogo.id, juegoId)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Juego eliminado", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                    navController.navigate(R.id.action_homeUsuario_self)
                } else {
                    Toast.makeText(context, "Error al eliminar el juego del catálogo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestJuegoId(callback: (Int) -> Unit) {
        // Infla la vista del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        val juegoIdEditText = dialogView.findViewById<EditText>(R.id.inputIdJuego)

        // Crea el diálogo
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Ingrese el ID del juego")
            .setPositiveButton("Aceptar") { _, _ ->
                val juegoIdString = juegoIdEditText.text.toString()

                // Verifica si el campo EditText está vacío
                if (juegoIdString.isBlank()) {
                    // Si el campo está vacío, muestra un mensaje de error y detén la operación
                    Toast.makeText(context, "El campo no puede estar vacío", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                // Convierte el ID del juego a Int y llama al callback
                val juegoId = juegoIdString.toInt()
                callback(juegoId)
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

}