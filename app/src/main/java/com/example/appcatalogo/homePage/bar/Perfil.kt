package com.example.appcatalogo.homePage.bar

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.TimeoutException
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.example.appcatalogo.databinding.FragmentPerfilBinding
import com.example.appcatalogo.apiConection.apiUsuario.service.UserService
import com.example.appcatalogo.messageErrorToStatus
import com.example.appcatalogo.showError
import com.squareup.picasso.Picasso
import com.example.appcatalogo.apiConection.apiUsuario.model.UserEdit
import com.example.appcatalogo.apiConection.apiUsuario.model.ImageProfile
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream


class Perfil : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val accessToken = TokenManager.accessToken

    private var initialUsername: String? = null
    private var initialNombreUser: String? = null
    private var initialEmailUser: String? = null

    private lateinit var apellido: String

    private var selectedImageUri: Uri? = null
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri

            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navView = activity?.findViewById(R.id.nav_view)
        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)
        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!
        CoroutineScope(Dispatchers.IO).launch {
            tryGetUserProfile()
        }
        coordinatorLayout?.visibility = View.VISIBLE
        appBarLayout?.visibility = View.VISIBLE
        navView?.visibility = View.VISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        navView?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_mi_perfil -> {
                    findNavController().navigate(R.id.action_perfil_self)
                    true
                }

                R.id.nav_item_inicio -> {
                    findNavController().navigate(R.id.action_perfil_to_homeFirstPage)
                    true
                }

                R.id.nav_item_categorias -> {
                    findNavController().navigate(R.id.action_perfil_to_categoriasSlideBar2)
                    true
                }

                R.id.nav_item_mis_catalogos -> {
                    findNavController().navigate(R.id.action_perfil_to_homeUsuario)
                    true
                }

                R.id.nav_item_cerrar_sesion -> {
                    findNavController().popBackStack(R.id.loginFragment, false)
                    true

                }

                else -> false
            }
        }
        val navView: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_icono -> {
                    findNavController().navigate(R.id.action_perfil_to_homeFirstPage)
                }

                R.id.home_usuario_icono -> {
                    findNavController().navigate(R.id.action_perfil_to_homeUsuario)
                }

                R.id.agregar_icono -> {
                    findNavController().navigate(R.id.action_perfil_to_crear)
                }

                R.id.search_icono -> {
                    findNavController().navigate(R.id.action_perfil_to_buscar)
                }

                R.id.profile_icono -> {
                    findNavController().navigate(R.id.action_perfil_self)
                }
            }
            true
        }

        binding.imageProfile.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
        binding.buttonSave.setOnClickListener {
            val usuarioEdit = UserEdit(
                username = binding.editTextUsername.text.toString(),
                nombre = binding.editTextTextNombreUser.text.toString(),
                apellido = apellido ?: "",
                email = binding.editTextEmailUser.text.toString()
            )
            val stringTokenAccess = "Bearer $accessToken"
            CoroutineScope(Dispatchers.IO).launch {
                if (tryEditUser(stringTokenAccess, usuarioEdit)) {
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_perfil_self)
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        _binding = null
    }

    private suspend fun getUserProfile() {
        val stringTokenAccess = "Bearer $accessToken"
        val response = UserService.profileUser(stringTokenAccess)
        return withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    initialUsername = user.usuario
                    initialNombreUser = user.nombre
                    initialEmailUser = user.email
                    apellido = user.apellido
                    binding.editTextUsername.text =
                        Editable.Factory.getInstance().newEditable(user.usuario)
                    binding.editTextTextNombreUser.text =
                        Editable.Factory.getInstance().newEditable(user.nombre)
                    binding.editTextEmailUser.text =
                        Editable.Factory.getInstance().newEditable(user.email)
                    showImageProfile(user.imagen)
                }
            } else {
                showError(messageErrorToStatus(response.code()))
            }
        }
    }

    private fun showImageProfile(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.logo)
            .into(binding.imageProfile, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Log.d("API", "Error al cargar la imagen: $e")
                }
            })
    }

    private suspend fun tryGetUserProfile() {
        return try {
            withTimeout(5000) {
                getUserProfile()
            }
        } catch (e: TimeoutException) {
            showError("Tiempo de espera agotado")
        } catch (e: IOException) {
            showError("Error de conexión de red")
        }
    }

    private suspend fun tryEditUser(authHeader: String, usuario: UserEdit): Boolean {
        return try {
            withTimeout(5000) {
                val response = UserService.editUser(authHeader, usuario)
                if (response.isSuccessful) {
                    true
                } else {
                    withContext(Dispatchers.Main) {
                        showError(messageErrorToStatus(response.code()))
                    }
                    false
                }
            }
        } catch (e: TimeoutException) {
            withContext(Dispatchers.Main) {
                showError("Tiempo de espera agotado")
            }
            false
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                showError("Error de conexión de red")
            }
            false
        }
    }

    private suspend fun tryChangeImageProfile(
        authHeader: String,
        imageProfile: ImageProfile
    ): Boolean {
        return try {
            withTimeout(5000) {
                val response = UserService.changeImageProfile(authHeader, imageProfile)
                if (response.isSuccessful) {
                    true
                } else {
                    withContext(Dispatchers.Main) {
                        showError(messageErrorToStatus(response.code()))
                    }
                    false
                }
            }
        } catch (e: TimeoutException) {
            withContext(Dispatchers.Main) {
                showError("Tiempo de espera agotado")
            }
            false
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                showError("Error de conexión de red")
            }
            false
        }
    }


    private fun createImageProfilePart(uri: Uri, applicationContext: Context): MultipartBody.Part {
        var inputStream: InputStream? = null
        try {
            inputStream = context?.contentResolver?.openInputStream(uri)
            if (inputStream != null) {
                val requestBody =
                    RequestBody.create(MediaType.parse("image/*"), inputStream.readBytes())
                return MultipartBody.Part.createFormData(
                    "image_profile",
                    uri.lastPathSegment!!,
                    requestBody
                )
            }
        } finally {
            inputStream?.close()
        }
        throw IOException("No se pudo abrir el InputStream")
    }
}