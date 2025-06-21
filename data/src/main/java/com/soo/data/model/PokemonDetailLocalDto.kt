package com.soo.data.model

import com.soo.data.db.entity.PokemonEntity
import com.soo.data.db.entity.PokemonTypeEntity

data class PokemonDetailLocalDto(
    val pokemon: PokemonEntity,
    val types: List<PokemonTypeEntity>
)
