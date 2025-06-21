package com.soo.presentation.activity

import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.soo.presentation.R
import com.soo.presentation.base.BaseActivity
import com.soo.presentation.base.BaseViewModel
import com.soo.presentation.databinding.ActivityPokemonInfoBinding
import com.soo.presentation.viewmodel.PokemonInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonInfoActivity : BaseActivity<ActivityPokemonInfoBinding>(R.layout.activity_pokemon_info) {

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

                        Glide.with(this@PokemonInfoActivity).load(info.imageUrl).into(binding.imgPokemon)

                        // TODO type에 따라 배경 다르게 구현하려면 추가 구현 필요
                        info.types.forEach { type ->
                            val tvType = TextView(this@PokemonInfoActivity).apply {
                                text = type
                                setPadding(30, 10, 30, 10)
                                
                                val layoutParams = FlexboxLayout.LayoutParams(
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    setMargins(0, 0, 10, 0) // 왼쪽, 위, 오른쪽, 아래 마진
                                }
                                this.layoutParams = layoutParams
                                setBackgroundResource(R.drawable.bg_type_chip)
                                textSize = 16f
                                setTextColor(resources.getColor(R.color.black, null))
                            }
                            binding.typeContainer.addView(tvType)
                        }

                        binding.btnAddFavorite.setOnClickListener {
                            pokemonInfoViewModel.insertFavoritePokemon(info)
                            setResult(RESULT_OK)
                        }

                        binding.executePendingBindings()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonInfoViewModel.uiEvent.collect { event ->
                    when(event) {
                        is BaseViewModel.UiEvent.ShowToast -> {
                            Toast.makeText(this@PokemonInfoActivity, event.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}