package com.example.appcatalogo.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import com.example.appcatalogo.apiConection.apiUsuario.Service.TokenManager
import com.example.appcatalogo.apiConection.apiUsuario.Service.UserService
import com.example.appcatalogo.apiConection.apiUsuario.model.AutenticacionRequest
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)
        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!

        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        val inputUsername: EditText = view.findViewById(R.id.inputUsername)
        val inputPassword: EditText = view.findViewById(R.id.inputPassword)
        var username: String
        var userPassword: String
        val conectSignUp: TextView = view.findViewById(R.id.conectSignUp)
        conectSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFirstPage)
        }

        val iniciarSesionButton = view.findViewById<Button>(R.id.buttonIniciarSesion);
        iniciarSesionButton.setOnClickListener {
            username = inputUsername.text.toString()
            userPassword = inputPassword.text.toString()
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
                        showError(response.code())
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

    private fun showError(status: Int){
        Toast.makeText(requireContext(), createMessageError(status), Toast.LENGTH_SHORT).show()
    }


}