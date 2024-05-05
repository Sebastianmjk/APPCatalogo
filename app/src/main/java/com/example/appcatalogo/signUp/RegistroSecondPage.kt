package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.apiConection.apiUsuario.Service.UserService
import com.example.appcatalogo.databinding.FragmentRegistroSecondPageBinding
import com.example.appcatalogo.messageErrorToStatus
import com.example.appcatalogo.showError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.TimeoutException


class RegistroSecondPage : Fragment() {


    private var _binding: FragmentRegistroSecondPageBinding? = null

    private val binding get() = _binding!!

    private val args: RegistroSecondPageArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroSecondPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonNext.setOnClickListener {
            val correoElectronico = binding.inputEmail.text.toString()
            if (correoElectronico.isEmpty()) {
                showError("El campo de correo electrónico no puede estar vacío")
                return@setOnClickListener
            }
            if (!validateEmail(correoElectronico)) {
                showError("El correo electrónico no es válido")
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                if (!trySendCode(correoElectronico)) {
                    return@launch
                }
                withContext(Dispatchers.Main) {
                    val action = RegistroSecondPageDirections.actionRegistroSecondPageToRegistroThirdPage(
                        userName = args.userName,
                        nombre = args.nombre,
                        apellido = args.apellido,
                        correoElectronico = correoElectronico
                    )
                    findNavController().navigate(action)
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

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private suspend fun trySendCode(email: String): Boolean {
        return try {
            withTimeout(5000) {
                val response = UserService.sendCode(email)
                if (response.isSuccessful) {
                    val message = response.body()?.string()
                    if (message != null) {
                        showError(message)
                    }
                    true
                } else {
                    val message = response.errorBody()?.string()
                    if (message != null) {
                        showError(message)
                    } else {
                        showError(messageErrorToStatus(response.code()))
                    }
                    false
                }
            }
        } catch (e: TimeoutException) {
            showError("Tiempo de espera agotado")
            false
        }catch (e: IOException) {
            showError("Error de conexión de red")
            false
        }
    }

}