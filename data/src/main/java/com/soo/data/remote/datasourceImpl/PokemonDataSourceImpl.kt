package com.soo.data.remote.datasourceImpl

import com.soo.data.remote.model.PokemonInfoDto
import com.soo.data.remote.model.PokemonResponse
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.data.service.PokemonService
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokeService: PokemonService
): PokemonDataSource {
    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonResponse {
        return pokeService.getPokemonList(limit, offset)
    }

    override suspend fun getPokemonInfo(name: String): PokemonInfoDto {
        return pokeService.getPokemonInfo(name)
    }
}