package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import android.content.Context
import android.text.TextWatcher
import android.text.Editable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView

class RegistroThirdPage : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var navView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var coordinatorLayout: CoordinatorLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_third_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLayout = activity?.findViewById(R.id.drawlerLayout)!!
        navView = activity?.findViewById(R.id.nav_view)
        appBarLayout = activity?.findViewById(R.id.app_bar_layout)
        coordinatorLayout = activity?.findViewById(R.id.coordinator_layout)

        coordinatorLayout?.visibility = View.GONE
        appBarLayout?.visibility = View.GONE
        navView?.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        val editText = view.findViewById<EditText>(R.id.invisibleEditText)
        val textView1 = view.findViewById<TextView>(R.id.Codigo1Text)
        val textView2 = view.findViewById<TextView>(R.id.Codigo2Text)
        val textView3 = view.findViewById<TextView>(R.id.Codigo3Text)
        val textView4 = view.findViewById<TextView>(R.id.Codigo4Text)
        editText.post {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                // Actualiza los TextViews con los dígitos ingresados
                textView1.text = if (text.length > 0) text[0].toString() else ""
                textView4.text = if (text.length > 1) text[1].toString() else ""
                textView3.text = if (text.length > 2) text[2].toString() else ""
                textView2.text = if (text.length > 3) text[3].toString() else ""
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No es necesario hacer nada aquí
            }
        })

        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_registroThirdPage_to_registroFourthPage)
        }
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}