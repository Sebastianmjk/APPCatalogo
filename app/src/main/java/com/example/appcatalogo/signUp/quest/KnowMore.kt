package com.example.appcatalogo.signUp.quest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView

class KnowMore : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_know_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val buttonComenzar = view.findViewById<Button>(R.id.buttonComenzar)
        buttonComenzar.setOnClickListener {
             findNavController().navigate(R.id.action_knowMore_to_selectionDesarrolladoras)
        }
    }
}