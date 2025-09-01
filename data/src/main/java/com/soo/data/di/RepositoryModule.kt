package com.soo.data.di

import com.soo.data.local.datasource.PokemonLocalDataSource
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providePokemonRepository(
        dataSource: PokemonDataSource,
        localDataSource: PokemonLocalDataSource
    ): PokemonRepository
}