package com.soo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.soo.domain.model.Pokemon
import com.soo.presentation.databinding.ItemPokemonBinding
import com.soo.presentation.viewholder.PokemonViewHolder

class PokemonListAdapter: PagingDataAdapter<Pokemon, PokemonViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position) ?: return

        holder.bind(item)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(old: Pokemon, new: Pokemon) = old.name == new.name
            override fun areContentsTheSame(old: Pokemon, new: Pokemon) = old == new
        }
    }
}