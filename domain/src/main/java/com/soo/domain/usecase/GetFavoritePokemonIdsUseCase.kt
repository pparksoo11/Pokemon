package com.soo.domain.usecase

import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritePokemonIdsUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Flow<List<Int>> {
        return pokemonRepository.getFavoritePokemonIds()
    }
}