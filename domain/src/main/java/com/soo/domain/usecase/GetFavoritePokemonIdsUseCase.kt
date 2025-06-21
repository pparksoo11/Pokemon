package com.soo.domain.usecase

import com.soo.domain.repository.PokemonRepository

class GetFavoritePokemonIdsUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): List<Int> {
        return pokemonRepository.getFavoritePokemonIds()
    }
}