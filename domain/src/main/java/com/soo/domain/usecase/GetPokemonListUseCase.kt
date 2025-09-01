package com.soo.domain.usecase

import androidx.paging.PagingData
import com.soo.domain.model.Pokemon
import com.soo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokeRepository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return pokeRepository.getPokemonList()
    }
}