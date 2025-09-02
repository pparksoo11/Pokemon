package com.soo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.soo.data.local.datasource.PokemonLocalDataSource
import com.soo.data.mapper.toDomain
import com.soo.data.mapper.toEntity
import com.soo.data.remote.paging.PokemonListPagingSource
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.domain.model.Pokemon
import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource
): PokemonRepository {
    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = androidx.paging.PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonListPagingSource(pokemonDataSource) }
        ).flow
    }

    override fun getPokemonInfo(name: String): Flow<PokemonInfo> = flow {
        val result = pokemonDataSource.getPokemonInfo(name)
        emit(result.toDomain())
    }.flowOn(Dispatchers.IO)

    override suspend fun insertFavoritePokemon(pokemon: PokemonInfo): Long {
        return pokemonLocalDataSource.insertFavoritePokemon(pokemon.toEntity())
    }

    override suspend fun getFavoritePokemonCount(): Int = pokemonLocalDataSource.getFavoritePokemonCount()

    override suspend fun isPokemonExists(id: Int): Boolean = pokemonLocalDataSource.isFavoritePokemonExists(id)

    override fun getFavoritePokemonList(): Flow<List<PokemonInfo>> {
        return pokemonLocalDataSource.getFavoritePokemonList().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getFavoritePokemonIds(): Flow<List<Int>> {
        return pokemonLocalDataSource.getFavoritePokemonIds()
    }

    override suspend fun getFavoritePokemon(id: Int): Flow<PokemonInfo?> {
        return pokemonLocalDataSource.getFavoritePokemon(id).map {
            it?.toDomain()
        }
    }

    override suspend fun deleteFavoritePokemon(id: Int): Boolean {
        return pokemonLocalDataSource.deleteFavoritePokemon(id) > 0
    }
}