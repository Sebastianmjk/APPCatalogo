package com.example.appcatalogo.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.R
import com.example.appcatalogo.databinding.FragmentCorreoVerificacionBinding
import com.example.appcatalogo.databinding.FragmentRegistroSecondPageBinding


class RegistroCorreoVerificacion : Fragment() {

    private var _binding: FragmentCorreoVerificacionBinding? = null

    private val binding get() = _binding!!

    private val args: RegistroCorreoVerificacionArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCorreoVerificacionBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener{

        }

        }

}