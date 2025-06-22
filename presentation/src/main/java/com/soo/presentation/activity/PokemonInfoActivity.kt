package com.soo.presentation.activity

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.soo.presentation.R
import com.soo.presentation.base.BaseActivity
import com.soo.presentation.base.BaseViewModel
import com.soo.presentation.databinding.ActivityPokemonInfoBinding
import com.soo.presentation.model.PokemonInfoUiModel
import com.soo.presentation.state.UiState
import com.soo.presentation.viewmodel.PokemonInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonInfoActivity : BaseActivity<ActivityPokemonInfoBinding>(R.layout.activity_pokemon_info) {

    private val pokemonInfoViewModel by viewModels<PokemonInfoViewModel>()
    private var isFavorite: Boolean = false

    override fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
         initPokemonInfo()
    }

    // 뒤로가기 버튼 클릭 시 activity finish
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * isFavorite이 true인 경우 즐겨찾기 > Info / false인 경우 리스트 > Info
     * */
    private fun initPokemonInfo() {
        isFavorite = intent.getBooleanExtra("isFavorite", false)

        if(isFavorite) {
            supportActionBar?.title = "Favorite Detail"
            val id = intent.getIntExtra("pokemonId", -1)
            pokemonInfoViewModel.getFavoritePokemon(id)
        } else {
            supportActionBar?.title = "Detail"
            val name = intent.getStringExtra("pokemonName") ?: ""
            pokemonInfoViewModel.getPokemonInfo(name)
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonInfoViewModel.pokemonInfoState.collect { state ->
                    when(state) {
                        is UiState.Loading -> {
                            showLoading()
                        }
                        is UiState.Success -> {
                            renderPokemonInfo(state.data)
                        }
                        is UiState.Error -> {
                            showError(state.type.message ?: "알 수 없는 오류가 발생했습니다.")
                        }
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

    // 로딩 화면 표시
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.overlayLayout.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
    }

    // 에러 화면 표시
    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.overlayLayout.visibility = View.VISIBLE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
    }

    // 포켓몬 정보 UI 표시
    private fun renderPokemonInfo(info: PokemonInfoUiModel) {
        binding.progressBar.visibility = View.GONE
        binding.overlayLayout.visibility = View.GONE
        binding.tvError.visibility = View.GONE

        binding.pokemonInfo = info
        Glide.with(this).load(info.imageUrl).into(binding.imgPokemon)

        renderTypeChips(info.types)
        setupFavoriteButton(info)
        binding.executePendingBindings()
    }

    // 타입 UI 추가
    private fun renderTypeChips(types: List<String>) {
        binding.typeContainer.removeAllViews() // 중복 방지
        types.forEach { type ->
            val chip = TextView(this).apply {
                text = type
                setPadding(30, 10, 30, 10)
                layoutParams = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 10, 0)
                }
                setBackgroundResource(R.drawable.bg_type_chip)
                textSize = 16f
                setTextColor(resources.getColor(R.color.black, null))
            }
            binding.typeContainer.addView(chip)
        }
    }

    /**
     * 즐겨찾기 버튼 UI 구현
     * 리스트 화면에서 접근한 경우 즐겨 찾기 추가 버튼 노출
     * 즐겨찾기 화면에서 접근한 경우 즐겨찾기 삭제 버튼 노출
     */
    private fun setupFavoriteButton(info: PokemonInfoUiModel) {
        if (isFavorite) {
            binding.btnFavorite.apply {
                text = "X 즐겨찾기 삭제"
                setBackgroundColor(ContextCompat.getColor(this@PokemonInfoActivity, R.color.red_500))
                setOnClickListener { pokemonInfoViewModel.deleteFavoritePokemon(info.id) }
            }
        } else {
            binding.btnFavorite.apply {
                text = "+ 즐겨찾기 추가"
                setBackgroundColor(ContextCompat.getColor(this@PokemonInfoActivity, R.color.green_500))
                setOnClickListener { pokemonInfoViewModel.insertFavoritePokemon(info) }
            }
        }
    }
}