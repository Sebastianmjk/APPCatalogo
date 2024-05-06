package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.apiConection.apiUsuario.service.UserService
import com.example.appcatalogo.apiConection.apiUsuario.model.UsuarioRegistro
import com.example.appcatalogo.apiConection.apiUsuario.service.TokenManager
import com.example.appcatalogo.databinding.FragmentRegistroFourthPageBinding
import com.example.appcatalogo.showError
import com.example.appcatalogo.messageErrorToStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.TimeoutException

class RegistroFourthPage : Fragment() {

    private var _binding: FragmentRegistroFourthPageBinding? = null
    private val binding get() = _binding!!

    private val args: RegistroFourthPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistroFourthPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonNext.setOnClickListener {
            val password1 = binding.inputContraseA.text.toString()
            val password2 = binding.inputConfirmarContraseA.text.toString()
            if (password1.isEmpty() || password2.isEmpty()){
                showError("Los campos son obligatorios")
                return@setOnClickListener
            }
            if (!checkPassword(password1,password2)){
                showError("Las contraseñas no coinciden")
                return@setOnClickListener
            }
            if (!checkRequirements(password1)){
                showError("La contraseña debe tener al menos 8 caracteres")
                return@setOnClickListener
            }
            val usuarioNuevo = UsuarioRegistro(
                username = args.userName,
                firstName = args.nombre,
                lastName = args.apellido,
                email = args.correoElectronico,
                password = password1
            )
            CoroutineScope(Dispatchers.IO).launch {
                if (tryRegisterUser(usuarioNuevo)){
                    withContext(Dispatchers.Main){
                        findNavController().navigate(RegistroFourthPageDirections.actionRegistroFourthPageToKnowMore())
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        showError("No se pudo registrar el usuario")
                    }
                    return@launch
                }
            }

        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPassword(password1:String, password2:String):Boolean{
        return password1 == password2
    }

    private fun checkRequirements(password:String):Boolean{
        return password.length >= 8
    }
    private suspend fun tryRegisterUser(usuarioNuevo: UsuarioRegistro): Boolean {
        return try {
            withTimeout(5000) {
                val response = UserService.registerUser(usuarioNuevo)
                if (response.isSuccessful) {
                    val tokensUser = response.body()
                    withContext(Dispatchers.Main) {
                        tokensUser?.let {
                            TokenManager.accessToken = it.access
                            TokenManager.refreshToken = it.refresh
                        }
                    }
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
        }catch (e: IOException) {
            withContext(Dispatchers.Main) {
                showError("Error de conexión de red")
            }
            false
        }
    }
}