package com.soo.domain.usecase

import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonInfoUseCase(
    private val pokeRepository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<PokemonInfo> {
        return pokeRepository.getPokemonInfo(name)
    }
}