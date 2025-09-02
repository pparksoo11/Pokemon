package com.soo.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soo.data.local.entity.PokemonTypeEntity

class PokemonConverters {

    @TypeConverter
    fun fromPokemonTypeList(value: List<PokemonTypeEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPokemonTypeList(value: String): List<PokemonTypeEntity> {
        val type = object : TypeToken<List<PokemonTypeEntity>>() {}.type
        return Gson().fromJson(value, type)
    }
}