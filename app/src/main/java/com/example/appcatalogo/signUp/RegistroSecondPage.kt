package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.databinding.FragmentRegistroSecondPageBinding
import com.example.appcatalogo.showError


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
            val action = RegistroSecondPageDirections.actionRegistroSecondPageToRegistroThirdPage(
                userName = args.userName,
                nombre = args.nombre,
                apellido = args.apellido,
                correoElectronico = correoElectronico
            )
            findNavController().navigate(action)
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

}