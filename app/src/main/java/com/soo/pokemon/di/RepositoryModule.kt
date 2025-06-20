package com.soo.pokemon.di

import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.data.repository.PokemonRepositoryImpl
import com.soo.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        dataSource: PokemonDataSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(dataSource)
    }
}