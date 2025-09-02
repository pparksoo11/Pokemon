package com.soo.data.di

import android.content.Context
import androidx.room.Room
import com.soo.data.local.AppDataBase
import com.soo.data.local.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "pokemon.db"
        ).build()
    }

    @Provides
    fun providePokemonDao(
        database: AppDataBase
    ): PokemonDao = database.pokemonDao()
}