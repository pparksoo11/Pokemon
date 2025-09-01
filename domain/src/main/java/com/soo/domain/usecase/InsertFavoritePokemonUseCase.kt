package com.soo.domain.usecase

import com.soo.domain.model.FavoritePokemonInsertResult
import com.soo.domain.model.PokemonInfo
import com.soo.domain.repository.PokemonRepository
import javax.inject.Inject

class InsertFavoritePokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(pokemonInfo: PokemonInfo): FavoritePokemonInsertResult {
        // 개수 초과
        if(pokemonRepository.getFavoritePokemonCount() >= 10) {
            return FavoritePokemonInsertResult.OverLimit
        }

        // 중복
        if(pokemonRepository.isPokemonExists(pokemonInfo.id)) {
            return FavoritePokemonInsertResult.AlreadyExists
        }

        val result = pokemonRepository.insertFavoritePokemon(pokemonInfo)
        return if(result > 0) {
            FavoritePokemonInsertResult.Success
        } else {
            FavoritePokemonInsertResult.Failure()
        }
    }
}