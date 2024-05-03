package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView

class RegistroFirstPage : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registro_first_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        //Inputs del firstPage del registro
        val inputUsername = view.findViewById<EditText>(R.id.inputUsername)
        val inputPrimerNombre = view.findViewById<EditText>(R.id.inputPrimerNombre)
        val inputSegundoNombre = view.findViewById<EditText>(R.id.inputSegundoNombre)
        val inputPrimerApellido = view.findViewById<EditText>(R.id.inputPrimerApellido)
        val inputSegundoApellido = view.findViewById<EditText>(R.id.inputSegundoApellido)

        var textUsername:String
        var textPrimerNombre:String
        var textSegundoNombre:String
        var textPrimerApellido:String
        var textSegundoApellido:String


        buttonNext.setOnClickListener {
            textUsername = inputUsername.toString()
            textPrimerNombre = inputPrimerNombre.toString()
            textSegundoNombre = inputSegundoNombre.toString() ?: ""
            textPrimerApellido = inputPrimerApellido.toString()
            textSegundoApellido = inputSegundoApellido.toString()
            if (textUsername.isEmpty() && textPrimerNombre.isEmpty() && textPrimerApellido.isEmpty() && textSegundoApellido.isEmpty()){
                showError("Tiene que completar los campos obligatorios")
            }else{
                val action = RegistroFirstPageDirections.actionRegistroFirstPageToRegistroSecondPage(
                    userName = textUsername,
                    nombre = textPrimerNombre + textSegundoNombre,
                    apellido = textPrimerApellido + textSegundoApellido
                )
                findNavController().navigate(action)
            }

        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showError(message:String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}