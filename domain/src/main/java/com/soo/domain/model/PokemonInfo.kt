package com.soo.domain.model

data class PokemonInfo(
    val id: Int = -1,
    val name: String = "",
    val weight: String = "",
    val height: String = "",
    val types: List<PokemonType>,
) {
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}
