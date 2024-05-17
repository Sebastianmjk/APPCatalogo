package com.example.appcatalogo.homePage.bar

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo
import com.example.appcatalogo.apiConection.apiCatalogos.ApiCatalogo.updateCatalogo
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterEliminar
import com.example.appcatalogo.apiConection.apiJuegos.model.AdapterJuegos
import com.example.appcatalogo.apiConection.apiJuegos.model.Result
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.example.appcatalogo.apiConection.functions.ImageController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


class CatalogosDetail : Fragment() {

    private lateinit var liContenedorCatalogosJuegos: LinearLayout
    private lateinit var liCargandoCatalogosJuegos: LinearLayout

    private lateinit var adaptadorEliminar: AdapterEliminar
    private lateinit var recyclerViewJuegos: RecyclerView
    private var idCatalogo: Int? = null
    private var nombreCatalogo: String? = null

    private val accessToken = TokenManager.accessToken


    private lateinit var drawerLayout: DrawerLayout
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null
    private var selectedImageUri: Uri? = null
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            val imageView = view?.findViewById<ImageView>(R.id.ivCatalogoDetail)
            imageView?.setImageURI(uri)

            selectedImageUri?.let { uri ->
                val imageFile = ImageController.uriToFile(uri, requireContext())
                val imagePortada = ImageController.fileToMultiparBody(imageFile!!,"Portada")
                if (idCatalogo != null) {
                    lifecycleScope.launch {
                        try {
                            val response = updateCatalogo.updateCatalogo("Bearer $accessToken", idCatalogo!!, imagePortada)
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Imagen actualizada", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.d("API", "Error: ${response}")
                                Toast.makeText(context, "${response}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            Toast.makeText(context, "Error de conexión de red", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Error: idCatalogo es nulo", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalogos_detail, container, false)

        nombreCatalogo = arguments?.getString("titulo_catalogo")
        idCatalogo = arguments?.getInt("catalogo_id")
        val textView = view.findViewById<TextView>(R.id.catalogosNombre)
        textView.text = nombreCatalogo
        val imageUrl = arguments?.getString("imagen_portada")
        val imageView = view.findViewById<ImageView>(R.id.ivCatalogoDetail)
        if (imageUrl != null) {
            Picasso.get()
                .load(imageUrl)
                .error(R.drawable.logo) // muestra una imagen de error si la carga falla
                .into(imageView, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        // La imagen se cargó correctamente
                    }

                    override fun onError(e: Exception?) {
                        // Hubo un error al cargar la imagen
                        Log.d("API", "Error al cargar la imagen: $e")
                    }
                })
        } else {
            // Cargar una imagen predeterminada de la carpeta drawable
            Picasso.get().load(R.drawable.logo).into(imageView)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManagerJuegos = LinearLayoutManager(context)
        recyclerViewJuegos = view.findViewById(R.id.rvCatalogosJuegos)
        recyclerViewJuegos.layoutManager = layoutManagerJuegos
        adaptadorEliminar = AdapterEliminar(ArrayList(),this)
        recyclerViewJuegos.adapter = adaptadorEliminar

        adaptadorEliminar.onItemClick = { juego ->
            val bundle = Bundle()
            bundle.putString("titulo_juego", juego.titulo)
            bundle.putString("resumen_juego", juego.resumen)
            if (juego.images.isNotEmpty()) {
                bundle.putString("imagen_juego", juego.images[0])
            }
            findNavController().navigate(R.id.action_catalogosDetail_to_juegoDetail, bundle)
        }
        val imageView = view.findViewById<ImageView>(R.id.ivCatalogoDetail)
        imageView.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        val juegoIds = arguments?.getIntegerArrayList("juego_ids")

        loadJuegos(juegoIds)

        val bundle = Bundle()
        bundle.putInt("catalogo_id", idCatalogo!!)

        val fab: FloatingActionButton = view.findViewById(R.id.addBtton)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_catalogosDetail_to_agregarJuego, bundle)
        }



        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)
        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!


        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)



        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)



        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_catalogosDetail_to_perfil)
                }
            }
            true
        }

        liContenedorCatalogosJuegos = view.findViewById(R.id.liContenedorCatalogosJuegos)
        liCargandoCatalogosJuegos = view.findViewById(R.id.liCargandoCatalogosJuegos)

        Handler(Looper.getMainLooper()).postDelayed({
            liCargandoCatalogosJuegos.isVisible = false
            liContenedorCatalogosJuegos.isVisible = true
        }, 3000)

    }



    fun loadJuegos(juegoIds: List<Int>?) {
        if (juegoIds != null) {
            lifecycleScope.launch {
                val juegosList = mutableListOf<Result>()
                for (id in juegoIds) {
                        try {
                            val response = ApiCatalogo.apiJuegos.getJuego(id)
                            if (response.isSuccessful) {
                                val juego = response.body()
                                if (juego != null) {
                                    juegosList.add(juego)
                                } else {
                                    // Maneja el caso en que juego es null
                                }
                            } else {
                                // Si la solicitud no fue exitosa, muestra un mensaje de error
                                Toast.makeText(context, "Error al obtener el juego", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            // Maneja cualquier excepción que pueda ocurrir
                            if (isAdded) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                adaptadorEliminar.juegosList.clear()
                adaptadorEliminar.juegosList.addAll(juegosList)
                adaptadorEliminar.notifyDataSetChanged()
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        recyclerViewJuegos.adapter = null
        adaptadorEliminar.juegosList.clear()
    }

    fun deleteJuegoFromCatalogo(juegoId: Int) {
        if (idCatalogo != null) {
            lifecycleScope.launch {
                try {
                    val response = ApiCatalogo.apiDeleteJuego.deleteJuegoFromCatalogo("Bearer $accessToken", idCatalogo!!, juegoId)
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Juego eliminado", Toast.LENGTH_SHORT).show()
                        adaptadorEliminar.notifyDataSetChanged()
                    } else {
                        Toast.makeText(context, "Error  ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Toast.makeText(context, "Error de conexión de red", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Error: idCatalogo es nulo", Toast.LENGTH_SHORT).show()
        }
    }

}