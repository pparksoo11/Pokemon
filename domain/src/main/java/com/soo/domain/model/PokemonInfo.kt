package com.soo.domain.model

data class PokemonInfo(
    val id: Int = -1,
    val name: String = "",
    val weight: Int = -1,
    val height: Int = -1,
    val types: List<PokemonType>,
)
