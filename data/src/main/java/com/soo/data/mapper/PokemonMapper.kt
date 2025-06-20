package com.soo.data.mapper

import com.soo.data.model.PokemonResponse
import com.soo.domain.model.Pokemon

fun PokemonResponse.toDomain(): List<Pokemon> {
    return results?.map { pokemon ->
        Pokemon(
            name = pokemon.name ?: "",
            url = pokemon.url ?: ""
        )
    } ?: emptyList()
}