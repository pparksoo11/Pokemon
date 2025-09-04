package com.soo.data.remote.datasourceImpl

import com.soo.data.di.IoDispatcher
import com.soo.data.remote.model.PokemonInfoDto
import com.soo.data.remote.model.PokemonResponse
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.data.remote.util.safeApiCall
import com.soo.data.service.PokemonService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokeService: PokemonService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): PokemonDataSource {
    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonResponse {
        return pokeService.getPokemonList(limit, offset)
    }

    override suspend fun getPokemonInfo(name: String) = withContext(dispatcher) {
        safeApiCall {
            pokeService.getPokemonInfo(name)
        }
    }
}