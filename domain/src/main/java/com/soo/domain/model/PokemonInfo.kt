package com.soo.domain.model

data class PokemonInfo(
    val id: Int = -1,
    val name: String = "",
    val weight: Int = -1,
    val height: Int = -1,
    val types: List<PokemonType>,
) {
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}
