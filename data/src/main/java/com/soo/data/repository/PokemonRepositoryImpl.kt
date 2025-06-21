package com.soo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.soo.data.common.mapSuccess
import com.soo.data.local.datasource.PokemonLocalDataSource
import com.soo.data.mapper.toDomain
import com.soo.data.mapper.toEntity
import com.soo.data.paging.PokemonListPagingSource
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.domain.model.Pokemon
import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getPokemonInfo(name: String): Flow<PokemonInfo> {
        return flow {
            pokemonDataSource.getPokemonInfo(name).mapSuccess { emit(it.toDomain()) }
        }
    }

    override suspend fun insertFavoritePokemon(pokemon: PokemonInfo): Long {
        return pokemonLocalDataSource.insertFavoritePokemon(pokemon.toEntity())
    }

    override suspend fun getFavoritePokemonCount(): Int = pokemonLocalDataSource.getFavoritePokemonCount()

    override suspend fun isPokemonExists(id: Int): Boolean = pokemonLocalDataSource.isFavoritePokemonExists(id)

    override fun getFavoritePokemonList(): Flow<List<PokemonInfo>> {
        return flow {
            emit(
                (pokemonLocalDataSource.getFavoritePokemonList()).toDomain()
            )
        }
    }

    override suspend fun getFavoritePokemonIds(): Flow<List<Int>> {
        return pokemonLocalDataSource.getFavoritePokemonIds()
    }
}