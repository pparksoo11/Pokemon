package com.soo.domain.usecase

import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePokemonIdsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Flow<List<Int>> {
        return pokemonRepository.getFavoritePokemonIds()
    }
}