package com.example.appcatalogo.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.databinding.FragmentCorreoVerificacionBinding
import com.example.appcatalogo.apiConection.apiUsuario.Service.UserService
import com.example.appcatalogo.showError
import com.example.appcatalogo.messageErrorToStatus
import kotlinx.coroutines.*
import java.io.IOException
import java.util.concurrent.TimeoutException


class RegistroCorreoVerificacion : Fragment() {

    private var _binding: FragmentCorreoVerificacionBinding? = null

    private val binding get() = _binding!!

    private val args: RegistroCorreoVerificacionArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCorreoVerificacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.invisibleEditText.post {
            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.invisibleEditText, InputMethodManager.SHOW_IMPLICIT)
        }
        binding.invisibleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                // Actualiza los TextViews con los dígitos ingresados
                binding.Codigo1Text.text = if (text.length > 0) text[0].toString() else ""
                binding.Codigo4Text.text = if (text.length > 1) text[1].toString() else ""
                binding.Codigo3Text.text = if (text.length > 2) text[2].toString() else ""
                binding.Codigo2Text.text = if (text.length > 3) text[3].toString() else ""
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // No es necesario hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No es necesario hacer nada aquí
            }
        })
        binding.buttonNext.setOnClickListener {
            val code = binding.invisibleEditText.text.toString()
            if (code.isEmpty()) {
                 showError("El código es obligatorio")
                return@setOnClickListener
            }
            val email = args.correoElectronico
            CoroutineScope(Dispatchers.IO).launch {
                if (!tryVerifyCode(email, code)) {
                    return@launch
                }
                withContext(Dispatchers.Main) {
                    val action = RegistroCorreoVerificacionDirections.actionRegistroCorreoVerificacionToRegistroNuevaContra(
                        correoElectronico = email
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

    private suspend fun tryVerifyCode(email: String, code: String): Boolean {
        return try {
            withTimeout(5000) {
                val response = UserService.verifyCode(email=email, code=code)
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