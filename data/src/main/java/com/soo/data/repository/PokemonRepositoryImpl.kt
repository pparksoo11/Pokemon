package com.soo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.soo.data.paging.PokemonListPagingSource
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.domain.model.Pokemon
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
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
}