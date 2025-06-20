package com.soo.domain.model

data class Pokemon(
    val name: String = "",
    val url: String = "",
) {
    fun getImageUrl(): String {
        val index = url.trimEnd('/').substringAfterLast('/', "")
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
    }
}