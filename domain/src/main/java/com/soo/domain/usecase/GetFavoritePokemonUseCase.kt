package com.soo.domain.usecase

import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Flow<PokemonInfo?> {
        return pokemonRepository.getFavoritePokemon(id)
    }
}