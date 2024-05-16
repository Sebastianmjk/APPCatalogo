package com.example.appcatalogo.homePage.bar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
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
import com.example.appcatalogo.showError.messageErrorToStatus
import com.example.appcatalogo.showError.showError
import com.squareup.picasso.Picasso
import com.example.appcatalogo.apiConection.apiUsuario.model.UserEdit


class Perfil : Fragment() {

    private lateinit var liImagenPerfil : LinearLayout
    private lateinit var liContenedorPerfil : LinearLayout

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

    private var apellido: String? = null
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

        binding.buttonSave.setOnClickListener {
            val usuarioEdit = UserEdit(
                username = binding.editTextUsername.text.toString(),
                nombre = binding.editTextTextNombreUser.text.toString(),
                apellido = apellido?:"",
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

        liContenedorPerfil = view.findViewById(R.id.liCargandoImagenPerfil)
        liImagenPerfil = view.findViewById(R.id.liImagenPerfil)

        Handler(Looper.getMainLooper()).postDelayed({
            liContenedorPerfil.isVisible = false
            liImagenPerfil.isVisible = true
        }, 1000)
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
            .error(R.drawable.logo) // muestra una imagen de error si la carga falla
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
}