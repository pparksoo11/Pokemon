package com.soo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.soo.data.db.entity.PokemonEntity

@Dao
interface PokemonDao {

    // 전체 포켓몬 정보와 타입 조회
    @Transaction
    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemonWithTypes(): List<PokemonEntity>

    // 특정 포켓몬 조회
    @Transaction
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonWithTypes(id: Int): PokemonEntity?

    // 포켓몬 정보 저장
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoritePokemon(pokemon: PokemonEntity): Long

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getFavoritePokemonCount(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM pokemon WHERE id = :id)")
    suspend fun isFavoritePokemonExists(id: Int): Boolean
}