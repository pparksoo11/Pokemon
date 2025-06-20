package com.soo.presentation.activity

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.soo.presentation.R
import com.soo.presentation.base.BaseActivity
import com.soo.presentation.databinding.ActivityPokemonDetailBinding
import com.soo.presentation.viewmodel.PokemonInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailActivity : BaseActivity<ActivityPokemonDetailBinding>(R.layout.activity_pokemon_detail) {

    private val pokemonInfoViewModel by viewModels<PokemonInfoViewModel>()

    override fun initView() {
        pokemonInfoViewModel.getPokemonInfo(name = intent.getStringExtra("pokemonName") ?: "")
    }

    override fun observeViewModel() {
        super.observeViewModel()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonInfoViewModel.pokemonInfo.collect { info ->
                    info?.let {
                        binding.pokemonInfo = info

                        Glide.with(this@PokemonDetailActivity).load(info.getImageUrl()).into(binding.imgPokemon)
                        binding.executePendingBindings()
                    }
                }
            }
        }
    }
}