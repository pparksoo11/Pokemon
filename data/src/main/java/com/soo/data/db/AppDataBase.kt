package com.soo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soo.data.db.converter.PokemonConverters
import com.soo.data.db.dao.PokemonDao
import com.soo.data.db.entity.PokemonEntity

@Database(
    entities =  [PokemonEntity::class],
    version = 1
)
@TypeConverters(PokemonConverters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}