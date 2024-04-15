package com.example.appcatalogo.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R

class SelectionDesarrolladoras : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_desarrolladoras, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_selectionDesarrolladoras_to_selectionDispositivos)
        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}