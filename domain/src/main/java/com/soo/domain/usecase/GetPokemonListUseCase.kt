package com.soo.domain.usecase

import androidx.paging.PagingData
import com.soo.domain.model.Pokemon
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val pokeRepository: PokemonRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Pokemon>> {
        return pokeRepository.getPokemonList()
    }
}