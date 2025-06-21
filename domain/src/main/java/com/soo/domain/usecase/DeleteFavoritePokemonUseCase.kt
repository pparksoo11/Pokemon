package com.soo.domain.usecase

import com.soo.domain.repository.PokemonRepository

class DeleteFavoritePokemonUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.deleteFavoritePokemon(id)
    }
}