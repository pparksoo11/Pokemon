package com.soo.data.local.datasource

import com.soo.data.db.entity.PokemonEntity

interface PokemonLocalDataSource {
    suspend fun insertFavoritePokemon(pokemonEntity: PokemonEntity): Long

    suspend fun getFavoritePokemonCount(): Int

    suspend fun isFavoritePokemonExists(id: Int): Boolean

    suspend fun getFavoritePokemonList(): List<PokemonEntity>

    suspend fun getFavoritePokemonIds(): List<Int>
}