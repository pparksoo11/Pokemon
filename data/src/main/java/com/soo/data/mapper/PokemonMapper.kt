package com.soo.data.mapper

import com.soo.data.db.entity.PokemonEntity
import com.soo.data.db.entity.PokemonTypeEntity
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

fun PokemonInfo.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        weight = weight,
        height = height,
        types = types.toEntityList(id),
    )
}

fun PokemonType.toEntity(pokemonId: Int): PokemonTypeEntity {
    return PokemonTypeEntity(
        id = pokemonId,
        name = this.name
    )
}

fun List<PokemonType>.toEntityList(pokemonId: Int): List<PokemonTypeEntity> {
    return this.map { it.toEntity(pokemonId) }
}

fun List<PokemonEntity>.toDomain(): List<PokemonInfo> {
    return this.map { entity ->
        PokemonInfo(
            id = entity.id,
            name = entity.name,
            weight = entity.weight,
            height = entity.height,
            types = entity.types.map { typeEntity ->
                PokemonType(
                    name = typeEntity.name,
                )
            }
        )
    }
}