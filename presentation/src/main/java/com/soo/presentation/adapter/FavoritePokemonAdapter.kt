package com.soo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.soo.presentation.databinding.ItemPokemonBinding
import com.soo.presentation.model.PokemonUiModel
import com.soo.presentation.viewholder.PokemonViewHolder

class FavoritePokemonAdapter: ListAdapter<PokemonUiModel, PokemonViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), isFavorite = true)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonUiModel>() {
            override fun areItemsTheSame(oldItem: PokemonUiModel, newItem: PokemonUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PokemonUiModel, newItem: PokemonUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}