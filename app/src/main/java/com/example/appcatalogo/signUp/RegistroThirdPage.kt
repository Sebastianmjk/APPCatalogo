package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.content.Context
import android.text.TextWatcher
import android.text.Editable
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.apiConection.apiUsuario.service.UserService
import com.example.appcatalogo.databinding.FragmentRegistroThirdPageBinding
import com.example.appcatalogo.messageErrorToStatus
import com.example.appcatalogo.showError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.TimeoutException
import com.example.appcatalogo.apiConection.apiUsuario.model.VerifyEmail

class RegistroThirdPage : Fragment() {

    private var _binding: FragmentRegistroThirdPageBinding? = null

    private val binding get() = _binding!!

    private val args: RegistroThirdPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroThirdPageBinding.inflate(inflater, container, false)
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
                CoroutineScope(Dispatchers.IO).launch {
                    if (!tryVerifyCode(args.correoElectronico, code)) {
                        return@launch
                    }
                    withContext(Dispatchers.Main) {
                        val action = RegistroThirdPageDirections.actionRegistroThirdPageToRegistroFourthPage(
                            userName = args.userName,
                            nombre = args.nombre,
                            apellido = args.apellido,
                            correoElectronico = args.correoElectronico
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
                val response = UserService.verifyCode(VerifyEmail(email=email, code=code))
                if (response.isSuccessful) {
                    val message = response.body()?.string()
                    if (message != null) {
                        withContext(Dispatchers.Main) {
                            showError(message)
                        }
                    }
                    true
                } else {
                    val message = response.errorBody()?.string()
                    if (message != null) {
                        withContext(Dispatchers.Main) {
                            showError(message)
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
