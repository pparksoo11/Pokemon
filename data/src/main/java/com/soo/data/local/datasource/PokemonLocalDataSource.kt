package com.soo.data.local.datasource

import com.soo.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonLocalDataSource {
    suspend fun insertFavoritePokemon(pokemonEntity: PokemonEntity): Long

    suspend fun getFavoritePokemonCount(): Int

    suspend fun isFavoritePokemonExists(id: Int): Boolean

    fun getFavoritePokemonList(): Flow<List<PokemonEntity>>

    suspend fun getFavoritePokemonIds(): Flow<List<Int>>

    suspend fun getFavoritePokemon(id: Int): Flow<PokemonEntity?>

    suspend fun deleteFavoritePokemon(id: Int): Int
}