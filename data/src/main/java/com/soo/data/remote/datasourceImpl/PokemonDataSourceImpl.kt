package com.soo.data.remote.datasourceImpl

import com.soo.data.common.ApiResult
import com.soo.data.common.mapSuccess
import com.soo.data.model.PokemonResponse
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.data.service.PokemonServcie
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokeService: PokemonServcie
): PokemonDataSource {
    override suspend fun getPokemonList(limit: Int, offset: Int): ApiResult<PokemonResponse> {
        return pokeService.getPokemonList(limit, offset).mapSuccess { it }
    }
}