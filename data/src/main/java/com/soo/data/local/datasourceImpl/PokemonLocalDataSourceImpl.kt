package com.soo.data.local.datasourceImpl

import com.soo.data.db.dao.PokemonDao
import com.soo.data.db.entity.PokemonEntity
import com.soo.data.local.datasource.PokemonLocalDataSource
import javax.inject.Inject

class PokemonLocalDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
): PokemonLocalDataSource {
    override suspend fun insertFavoritePokemon(pokemonEntity: PokemonEntity): Long {
        return pokemonDao.insertFavoritePokemon(pokemonEntity)
    }

    override suspend fun getFavoritePokemonCount(): Int = pokemonDao.getFavoritePokemonCount()

    override suspend fun isFavoritePokemonExists(id: Int): Boolean = pokemonDao.isFavoritePokemonExists(id)
}