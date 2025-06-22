package com.soo.data.local.datasourceImpl

import com.soo.data.db.dao.PokemonDao
import com.soo.data.db.entity.PokemonEntity
import com.soo.data.local.datasource.PokemonLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonLocalDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
): PokemonLocalDataSource {
    override suspend fun insertFavoritePokemon(pokemonEntity: PokemonEntity): Long {
        return pokemonDao.insertFavoritePokemon(pokemonEntity)
    }

    override suspend fun getFavoritePokemonCount(): Int = pokemonDao.getFavoritePokemonCount()

    override suspend fun isFavoritePokemonExists(id: Int): Boolean = pokemonDao.isFavoritePokemonExists(id)

    override fun getFavoritePokemonList(): Flow<List<PokemonEntity>> {
        return pokemonDao.getFavoritePokemonList()
    }

    override suspend fun getFavoritePokemonIds(): Flow<List<Int>> {
        return pokemonDao.getFavoritePokemonIds()
    }

    override suspend fun getFavoritePokemon(id: Int): Flow<PokemonEntity?> {
        return pokemonDao.getFavoritePokemon(id)
    }

    override suspend fun deleteFavoritePokemon(id: Int): Int {
        return pokemonDao.deletePokemon(id)
    }
}