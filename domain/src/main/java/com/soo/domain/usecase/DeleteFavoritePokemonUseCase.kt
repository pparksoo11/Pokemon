package com.soo.domain.usecase

import com.soo.domain.repository.PokemonRepository
import javax.inject.Inject

class DeleteFavoritePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.deleteFavoritePokemon(id)
    }
}