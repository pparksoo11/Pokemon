package com.soo.data.mapper

import com.soo.data.model.PokemonInfoDto
import com.soo.data.model.PokemonResponse
import com.soo.domain.model.Pokemon
import com.soo.domain.model.PokemonInfo
import com.soo.domain.model.PokemonType

fun PokemonResponse.toDomain(): List<Pokemon> {
    return results?.map { pokemon ->
        Pokemon(
            name = pokemon.name ?: "",
            url = pokemon.url ?: ""
        )
    } ?: emptyList()
}

fun PokemonInfoDto.toDomain(): PokemonInfo {
    val types = this.types?.mapNotNull { pokemonType ->
        pokemonType.type?.let { type ->
            PokemonType(
                name = type.name.orEmpty(),
                url = type.url.orEmpty()
            )
        }
    } ?: emptyList()

    return PokemonInfo(
        id = id ?: -1,
        name = name ?: "",
        weight = weight ?: -1,
        height = height ?: -1,
        types = types
    )
}