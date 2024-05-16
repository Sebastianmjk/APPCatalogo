package com.example.appcatalogo.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.showError.showError
import com.example.appcatalogo.databinding.FragmentCorreoContraBinding
import com.example.appcatalogo.apiConection.apiUsuario.service.UserService
import com.example.appcatalogo.showError.messageErrorToStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.TimeoutException
import com.example.appcatalogo.apiConection.apiUsuario.model.EmailSendCode



class RegistroCorreoContra : Fragment() {

    private var _binding: FragmentCorreoContraBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCorreoContraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            val textEmail = binding.inputEmail.text.toString()
            if (textEmail.isEmpty()) {
                showError("El campo tiene que ser completado")
                return@setOnClickListener
            }
            if (!validateEmail(textEmail)) {
                showError("El email no es valido")
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch{
                if (!trySendCode(textEmail)){
                    return@launch
                }
                withContext(Dispatchers.Main){
                    val action = RegistroCorreoContraDirections.actionRegistroCorreoContraToRegistroCorreoVerificacion(
                        correoElectronico = textEmail
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
                val response = UserService.sendCodeChangePassword(EmailSendCode(email))
                if (response.isSuccessful) {
                    val message = response.body()?.string()
                    if (message != null) {
                        withContext(Dispatchers.Main) {
                            showError("Codigo enviado")
                        }
                    }
                    true
                } else {
                    val message = response.errorBody()?.string()
                    if (message != null) {
                        withContext(Dispatchers.Main) {
                            println("Mensaje de error: $message")
                            showError("$message")
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            showError(messageErrorToStatus(response.code()))
                        }
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