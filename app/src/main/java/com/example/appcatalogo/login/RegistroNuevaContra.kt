package com.example.appcatalogo.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.showError
import com.example.appcatalogo.messageErrorToStatus
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.databinding.FragmentNuevaContraBinding
import com.example.appcatalogo.apiConection.apiUsuario.model.UserChangePassword
import com.example.appcatalogo.apiConection.apiUsuario.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.TimeoutException


class RegistroNuevaContra : Fragment() {

    private var _binding: FragmentNuevaContraBinding? = null

    private val binding get() = _binding!!

    private val args: RegistroNuevaContraArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentNuevaContraBinding.inflate(inflater, container, false)
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
            val request = UserChangePassword(
                email = args.correoElectronico,
                code = args.code,
                password = password1
            )
            CoroutineScope(Dispatchers.IO).launch{
                if (!tryChangePassword(request)){
                    return@launch
                }
                withContext(Dispatchers.Main){
                    showError("Contraseña cambiada con éxito")
                    val action = RegistroNuevaContraDirections.actionRegistroNuevaContraToLoginFragment()
                    findNavController().navigate(action)
                }
            }
        }

        binding.buttonBack.setOnClickListener {
//            findNavController().navigateUp()
        }
    }

    private fun checkPassword(password1:String, password2:String):Boolean{
        return password1 == password2
    }

    private fun checkRequirements(password:String):Boolean{
        return password.length >= 8
    }

     private suspend fun tryChangePassword(request:UserChangePassword):Boolean{
         return try{
             withTimeout(5000) {
                 val response = UserService.changePasswordForgot(request)
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
         }catch (e: TimeoutException) {
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