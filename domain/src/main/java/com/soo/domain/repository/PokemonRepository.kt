package com.soo.domain.repository

import androidx.paging.PagingData
import com.soo.domain.model.Pokemon
import com.soo.domain.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<Pokemon>>

    fun getPokemonInfo(name: String): Flow<PokemonInfo>

    suspend fun insertFavoritePokemon(pokemon: PokemonInfo): Long

    suspend fun getFavoritePokemonCount(): Int

    suspend fun isPokemonExists(id: Int): Boolean

    fun getFavoritePokemonList(): Flow<List<PokemonInfo>>

    suspend fun getFavoritePokemonIds(): List<Int>
}