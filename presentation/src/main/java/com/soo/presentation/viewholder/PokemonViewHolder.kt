package com.soo.presentation.viewholder

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.soo.domain.model.Pokemon
import com.soo.presentation.activity.PokemonDetailActivity
import com.soo.presentation.databinding.ItemPokemonBinding

class PokemonViewHolder(private val binding: ItemPokemonBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: Pokemon) {
        binding.pokemon = pokemon
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

        binding.cardView.setOnClickListener {
            val intent = Intent(it.context, PokemonDetailActivity::class.java).apply {
                putExtra("pokemonName", pokemon.name)
            }
            it.context.startActivity(intent)
        }

        binding.executePendingBindings() // 즉시 바인딩 반영
    }
}