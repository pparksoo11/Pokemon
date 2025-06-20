package com.soo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.soo.domain.model.PokemonInfo
import com.soo.domain.usecase.GetPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase
): ViewModel() {

    private val _pokemonInfo = MutableStateFlow<PokemonInfo?>(null)
    val pokemonInfo: StateFlow<PokemonInfo?> = _pokemonInfo

    fun getPokemonInfo(name: String) = viewModelScope.launch {
        getPokemonInfoUseCase(name).collect { info ->
            _pokemonInfo.value = info
        }
    }
}