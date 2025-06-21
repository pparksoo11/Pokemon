package com.soo.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.soo.domain.usecase.GetFavoritePokemonListUseCase
import com.soo.presentation.base.BaseViewModel
import com.soo.presentation.mapper.toFavoriteUiModel
import com.soo.presentation.model.PokemonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonFavoriteViewModel @Inject constructor(
    private val getFavoritePokemonListUseCase: GetFavoritePokemonListUseCase,
): BaseViewModel() {

    private val _favoritePokemonList = MutableStateFlow<List<PokemonUiModel>>(emptyList())
    val favoritePokemonList: StateFlow<List<PokemonUiModel>> = _favoritePokemonList

    fun getFavoritePokemonList() = viewModelScope.launch {
        getFavoritePokemonListUseCase()
            .map { domainList -> domainList.map { it.toFavoriteUiModel() } }
            .collect { uiList ->
                _favoritePokemonList.value = uiList
            }
    }
}