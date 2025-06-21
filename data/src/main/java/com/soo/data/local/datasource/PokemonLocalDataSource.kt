package com.soo.data.local.datasource

import com.soo.data.db.entity.PokemonEntity

interface PokemonLocalDataSource {
    suspend fun insertFavoritePokemon(pokemonEntity: PokemonEntity)
}