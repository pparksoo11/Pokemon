package com.soo.pokemon.di

import com.soo.domain.repository.PokemonRepository
import com.soo.domain.usecase.GetPokemonInfoUseCase
import com.soo.domain.usecase.GetPokemonListUseCase
import com.soo.domain.usecase.InsertFavoritePokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonListUseCase(
        repository: PokemonRepository
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }

    @Provides
    fun provideGetPokemonInfoUseCase(
        repository: PokemonRepository
    ): GetPokemonInfoUseCase {
        return GetPokemonInfoUseCase(repository)
    }

    @Provides
    fun provideInsertFavoritePokemonUseCase(
        repository: PokemonRepository
    ): InsertFavoritePokemonUseCase {
        return InsertFavoritePokemonUseCase(repository)
    }
}