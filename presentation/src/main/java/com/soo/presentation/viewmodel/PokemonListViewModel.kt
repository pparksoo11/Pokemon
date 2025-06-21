package com.soo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.soo.domain.usecase.GetFavoritePokemonIdsUseCase
import com.soo.domain.usecase.GetPokemonListUseCase
import com.soo.presentation.mapper.toListUiModel
import com.soo.presentation.model.PokemonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getFavoritePokemonIdsUseCase: GetFavoritePokemonIdsUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<PagingData<PokemonUiModel>>(PagingData.empty())
    val pokemonList: StateFlow<PagingData<PokemonUiModel>> = _pokemonList

    private val cachedPagingFlow = getPokemonListUseCase().cachedIn(viewModelScope)

    fun getPokemonList() = viewModelScope.launch {
        combine(
            cachedPagingFlow,
            getFavoritePokemonIdsUseCase()
        ) { pagingData, favoriteIds ->
            pagingData.map { it.toListUiModel(favoriteIds) }
        }.collect {
            _pokemonList.value = it
        }
    }
}