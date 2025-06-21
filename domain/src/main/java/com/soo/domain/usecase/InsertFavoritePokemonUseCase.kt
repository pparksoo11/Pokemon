package com.soo.domain.usecase

import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository

class InsertFavoritePokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(pokemonInfo: PokemonInfo) {
        pokemonRepository.insertFavoritePokemon(pokemonInfo)
    }
}