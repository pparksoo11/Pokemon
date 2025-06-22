package com.soo.data.service

import com.soo.data.model.PokemonInfoDto
import com.soo.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): PokemonInfoDto
}