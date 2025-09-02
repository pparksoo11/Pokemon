package com.soo.data.remote.datasource

import com.soo.data.remote.model.PokemonInfoDto
import com.soo.data.remote.model.PokemonResponse

interface PokemonDataSource {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonResponse

    suspend fun getPokemonInfo(name: String): PokemonInfoDto
}