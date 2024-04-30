package com.example.appcatalogo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(private val image: String): RecyclerView.Adapter<GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameViewHolder(layoutInflater.inflate(R.layout.item_game, parent, false))
    }

    override fun getItemCount(): Int {
        return 1 // Solo hay una imagen, por lo que el recuento de elementos es 1
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(image) // Pasamos la única imagen al método bind
    }
}


