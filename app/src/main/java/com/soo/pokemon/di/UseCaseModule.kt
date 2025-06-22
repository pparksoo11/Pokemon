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

//TODO UseCase가 계속 추가될 경우, 도메인 모듈에 Hilt를 도입하여 의존성 주입을 자동화하는 방안 고려
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