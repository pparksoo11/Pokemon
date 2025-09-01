package com.soo.data.di

import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.data.remote.datasourceImpl.PokemonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindPokemonDataSource(
        impl: PokemonDataSourceImpl
    ): PokemonDataSource
}