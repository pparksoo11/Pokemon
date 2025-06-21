package com.soo.domain.usecase

import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritePokemonListUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<List<PokemonInfo>> {
        return pokemonRepository.getFavoritePokemonList()
    }
}