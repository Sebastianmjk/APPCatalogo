package com.example.appcatalogo.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.example.appcatalogo.apiConection.apiUsuario.service.UserService
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.appcatalogo.databinding.FragmentLoginBinding
import com.example.appcatalogo.showError
import com.google.android.material.navigation.NavigationView

class LoginFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var appBarLayout: AppBarLayout? = null
    private var navView: NavigationView? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        navView = activity?.findViewById(R.id.nav_view)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)
        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!

        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        navView?.visibility = View.VISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        var username: String
        var userPassword: String
        binding.passwordForgetText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroCorreoContra)
        }
        binding.conectSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFirstPage)
        }

        binding.buttonIniciarSesion.setOnClickListener {
            username = binding.inputUsername.text.toString().replace(" ","")
            userPassword = binding.inputPassword.text.toString().replace(" ","")
            if (username.isEmpty() || userPassword.isEmpty()) {
                showError("Tiene que completar los campos obligatorios")
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                val response = UserService.loginUser(AutenticacionRequest(username, userPassword))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val tokensUser = response.body()
                        tokensUser?.let {
                            TokenManager.accessToken = it.access
                            TokenManager.refreshToken = it.refresh
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_homeFirstPage)
                    } else {
                        showError(createMessageError(response.code()))
                    }
                }
            }
        }
    }



    private fun createMessageError(status: Int):String{
        return when(status){
            401 -> "Usuario o contraseña incorrectos"
            403 -> "Acceso denegado"
            else -> "Error: $status"
        }
    }



}