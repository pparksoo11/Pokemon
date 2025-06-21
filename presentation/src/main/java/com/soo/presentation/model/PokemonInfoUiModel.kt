package com.soo.presentation.model

data class PokemonInfoUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val height: Int,
    val weight: Int,
)