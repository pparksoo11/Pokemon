package com.soo.presentation.mapper

import com.soo.domain.model.Pokemon
import com.soo.domain.model.PokemonInfo
import com.soo.domain.model.PokemonType
import com.soo.presentation.model.PokemonInfoUiModel
import com.soo.presentation.model.PokemonUiModel

fun Pokemon.toListUiModel(favoriteIds: List<Int>): PokemonUiModel {
    return PokemonUiModel(
        id = getId(url),
        name = name,
        imageUrl = getImageUrl(getId(url)),
        isFavorite = favoriteIds.contains(getId(url))
    )
}

fun PokemonInfo.toFavoriteUiModel(): PokemonUiModel {
    return PokemonUiModel(
        id = id,
        name = name,
        imageUrl = getImageUrl(id),
        isFavorite = false, // Favorite List에서는 좋아요 버튼 노출이 필요없음
    )
}

fun PokemonInfo.toDetailUiModel(): PokemonInfoUiModel {
    return PokemonInfoUiModel(
        id = id,
        name = name,
        imageUrl = getImageUrl(id),
        weight = weight,
        height = height,
        types = types.map { it.name },
    )
}

fun PokemonInfoUiModel.toDomain(): PokemonInfo {
    return PokemonInfo(
        id = id,
        name = name,
        weight = weight,
        height = height,
        types = types.map { PokemonType(it) }
    )
}

fun Pokemon.getId(url: String): Int {
    return url.trimEnd('/').substringAfterLast('/', "").toIntOrNull() ?: -1
}

private fun getImageUrl(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}