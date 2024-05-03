package com.example.appcatalogo.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcatalogo.R
import android.content.Context
import android.text.TextWatcher
import android.text.Editable
import androidx.navigation.fragment.navArgs
import com.example.appcatalogo.databinding.FragmentRegistroThirdPageBinding

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


        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        val editText = view.findViewById<EditText>(R.id.invisibleEditText)
        val textView1 = view.findViewById<TextView>(R.id.Codigo1Text)
        val textView2 = view.findViewById<TextView>(R.id.Codigo2Text)
        val textView3 = view.findViewById<TextView>(R.id.Codigo3Text)
        val textView4 = view.findViewById<TextView>(R.id.Codigo4Text)
        editText.post {
        binding.invisibleEditText.post {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No es necesario hacer nada aquí
            }
        })

        binding.buttonNext.setOnClickListener {
            val action = RegistroThirdPageDirections.actionRegistroThirdPageToRegistroFourthPage(
                userName = args.userName,
                nombre = args.nombre,
                apellido = args.apellido,
                correoElectronico = args.correoElectronico,
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
}