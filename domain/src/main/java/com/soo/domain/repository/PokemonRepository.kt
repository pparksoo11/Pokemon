package com.soo.domain.repository

import androidx.paging.PagingData
import com.soo.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<Pokemon>>
}