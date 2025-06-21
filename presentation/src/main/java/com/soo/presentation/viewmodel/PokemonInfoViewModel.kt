package com.soo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soo.domain.model.PokemonInfo
import com.soo.domain.usecase.GetPokemonInfoUseCase
import com.soo.domain.usecase.InsertFavoritePokemonUseCase
import com.soo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
    private val insertFavoritePokemonUseCase: InsertFavoritePokemonUseCase
): BaseViewModel() {

    private val _pokemonInfo = MutableStateFlow<PokemonInfo?>(null)
    val pokemonInfo: StateFlow<PokemonInfo?> = _pokemonInfo

    fun getPokemonInfo(name: String) = viewModelScope.launch {
        getPokemonInfoUseCase(name).collect { info ->
            _pokemonInfo.value = info
        }
    }

    fun insertFavoritePokemon(pokemonInfo: PokemonInfo) = viewModelScope.launch {
        insertFavoritePokemonUseCase(pokemonInfo)
        showToast("저장 성공")
    }
}