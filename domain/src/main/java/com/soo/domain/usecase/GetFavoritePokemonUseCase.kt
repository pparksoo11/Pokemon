package com.soo.domain.usecase

import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository

class GetFavoritePokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): PokemonInfo? {
        return pokemonRepository.getFavoritePokemon(id)
    }
}