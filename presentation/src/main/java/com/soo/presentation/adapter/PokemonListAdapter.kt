package com.soo.presentation.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.soo.domain.model.Pokemon
import com.soo.presentation.databinding.ItemPokemonBinding

class PokemonListAdapter: PagingDataAdapter<Pokemon, PokemonListAdapter.PokemonViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position) ?: return

        holder.bind(item)
    }

    class PokemonViewHolder(private val binding: ItemPokemonBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.tvName.text = pokemon.name
            binding.progressBar.visibility = View.VISIBLE
            binding.tvError.visibility = View.GONE

            val imageUrl = pokemon.getImageUrl()
            Glide.with(binding.imgPokemon.context)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // 에러 text 노출
                        binding.progressBar.visibility = View.GONE
                        binding.imgPokemon.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.imgPokemon.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imgPokemon)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(old: Pokemon, new: Pokemon) = old.name == new.name
            override fun areContentsTheSame(old: Pokemon, new: Pokemon) = old == new
        }
    }
}