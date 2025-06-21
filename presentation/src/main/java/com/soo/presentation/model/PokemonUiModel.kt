package com.soo.presentation.model

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)