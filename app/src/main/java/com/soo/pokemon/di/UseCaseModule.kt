package com.soo.pokemon.di

import com.soo.domain.repository.PokemonRepository
import com.soo.domain.usecase.DeleteFavoritePokemonUseCase
import com.soo.domain.usecase.GetFavoritePokemonIdsUseCase
import com.soo.domain.usecase.GetFavoritePokemonListUseCase
import com.soo.domain.usecase.GetFavoritePokemonUseCase
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

    @Provides
    fun provideGetFavoritePokemonListUseCase(
        repository: PokemonRepository
    ): GetFavoritePokemonListUseCase {
        return GetFavoritePokemonListUseCase(repository)
    }

    @Provides
    fun provideGetFavoritePokemonIdsUseCase(
        repository: PokemonRepository
    ): GetFavoritePokemonIdsUseCase {
        return GetFavoritePokemonIdsUseCase(repository)
    }

    @Provides
    fun provideGetFavoritePokemonUseCase(
        repository: PokemonRepository
    ): GetFavoritePokemonUseCase {
        return GetFavoritePokemonUseCase(repository)
    }

    @Provides
    fun provideDeleteFavoritePokemonUseCase(
        repository: PokemonRepository
    ): DeleteFavoritePokemonUseCase {
        return DeleteFavoritePokemonUseCase(repository)
    }
}