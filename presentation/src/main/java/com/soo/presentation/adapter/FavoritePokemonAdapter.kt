package com.soo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.soo.presentation.databinding.ItemPokemonBinding
import com.soo.presentation.model.PokemonUiModel
import com.soo.presentation.viewholder.FavoritePokemonViewHolder

class FavoritePokemonAdapter: ListAdapter<PokemonUiModel, FavoritePokemonViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritePokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritePokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
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