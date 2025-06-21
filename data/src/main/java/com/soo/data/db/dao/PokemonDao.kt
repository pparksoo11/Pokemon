package com.soo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.soo.data.db.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    // 전체 포켓몬 정보 조회
    @Transaction
    @Query("SELECT * FROM pokemon")
    fun getFavoritePokemonList(): Flow<List<PokemonEntity>>

    // 특정 포켓몬 조회
    @Transaction
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getFavoritePokemon(id: Int): PokemonEntity?

    // 포켓몬 정보 저장
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoritePokemon(pokemon: PokemonEntity): Long

    // 즐겨찾기에 추가된 포켓몬 개수 조회
    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getFavoritePokemonCount(): Int

    // 포켓몬이 즐겨찾기에 있는지 여부 조회
    @Query("SELECT EXISTS(SELECT 1 FROM pokemon WHERE id = :id)")
    suspend fun isFavoritePokemonExists(id: Int): Boolean

    // 즐겨찾기에 있는 포켓몬 ID 목록 조회
    @Query("SELECT id FROM pokemon")
    fun getFavoritePokemonIds(): Flow<List<Int>>

    // 즐겨찾기에서 특정 포켓몬 id 제거
    @Query("DELETE FROM pokemon WHERE id = :id")
    suspend fun deletePokemon(id: Int): Int
}