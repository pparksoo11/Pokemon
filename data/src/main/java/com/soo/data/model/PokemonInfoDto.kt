package com.soo.data.model

data class PokemonInfoDto(
    val id: Int?,
    var name: String?,
    val weight: Int?,
    val height: Int?,
    val types: List<PokemonTypeResponse>?,
)