package com.soo.data.di

import com.soo.data.local.datasource.PokemonLocalDataSource
import com.soo.data.local.datasourceImpl.PokemonLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindPokemonLocalDataSource(
        impl: PokemonLocalDataSourceImpl
    ): PokemonLocalDataSource
}