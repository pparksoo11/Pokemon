package com.soo.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.soo.domain.model.FavoritePokemonInsertResult
import com.soo.domain.model.PokemonInfo
import com.soo.domain.usecase.GetFavoritePokemonUseCase
import com.soo.domain.usecase.GetPokemonInfoUseCase
import com.soo.domain.usecase.InsertFavoritePokemonUseCase
import com.soo.presentation.base.BaseViewModel
import com.soo.presentation.mapper.toDetailUiModel
import com.soo.presentation.mapper.toDomain
import com.soo.presentation.model.PokemonInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
    private val insertFavoritePokemonUseCase: InsertFavoritePokemonUseCase,
    private val getFavoritePokemonUseCase: GetFavoritePokemonUseCase
): BaseViewModel() {

    private val _pokemonInfo = MutableStateFlow<PokemonInfoUiModel?>(null)
    val pokemonInfo: StateFlow<PokemonInfoUiModel?> = _pokemonInfo

    fun getPokemonInfo(name: String) = viewModelScope.launch {
        getPokemonInfoUseCase(name).collect { ui ->
            _pokemonInfo.value = ui.toDetailUiModel()
        }
    }

    fun insertFavoritePokemon(pokemonInfo: PokemonInfoUiModel) = viewModelScope.launch {
        val domainPokemonInfo = pokemonInfo.toDomain()
        when(insertFavoritePokemonUseCase(domainPokemonInfo)) {
            is FavoritePokemonInsertResult.Success -> showToast("저장 성공")
            is FavoritePokemonInsertResult.AlreadyExists -> showToast("이미 저장된 포켓몬입니다")
            is FavoritePokemonInsertResult.OverLimit -> showToast("최대 10마리까지 저장 가능합니다")
            is FavoritePokemonInsertResult.Failure -> showToast("저장 실패")
        }
    }

    fun getFavoritePokemon(id: Int) = viewModelScope.launch {
        val result = getFavoritePokemonUseCase(id)
        _pokemonInfo.value = result?.toDetailUiModel()
    }
}