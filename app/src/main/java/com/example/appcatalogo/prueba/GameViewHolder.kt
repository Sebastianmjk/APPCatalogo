package com.example.appcatalogo.prueba

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatalogo.databinding.ItemGameBinding
import com.squareup.picasso.Picasso

class GameViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemGameBinding.bind(view)

    fun bind(image:String) {
        Picasso.get().load(image).into(binding.ivGame)
    }
}