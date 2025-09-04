package com.soo.data.remote.datasource

import com.soo.data.remote.model.PokemonInfoDto
import com.soo.data.remote.model.PokemonResponse
import com.soo.domain.util.result.ApiResult

interface PokemonDataSource {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonResponse

    suspend fun getPokemonInfo(name: String): ApiResult<PokemonInfoDto>
}