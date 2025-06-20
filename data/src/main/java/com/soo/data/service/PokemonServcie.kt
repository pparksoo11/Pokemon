package com.soo.data.service

import com.soo.data.common.ApiResult
import com.soo.data.model.PokemonInfoDto
import com.soo.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonServcie {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): ApiResult<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): ApiResult<PokemonInfoDto>
}