package com.soo.data.remote.datasource

import com.soo.data.common.ApiResult
import com.soo.data.model.PokemonResponse

interface PokemonDataSource {
    suspend fun getPokemonList(limit: Int, offset: Int): ApiResult<PokemonResponse>
}