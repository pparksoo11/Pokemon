package com.soo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soo.data.local.PokemonConverters
import com.soo.data.local.dao.PokemonDao
import com.soo.data.local.entity.PokemonEntity

@Database(
    entities =  [PokemonEntity::class],
    version = 1
)
@TypeConverters(PokemonConverters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}