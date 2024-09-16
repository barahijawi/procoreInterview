package com.example.procoreinterview.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PockemonCardDao {
    @Query("SELECT * FROM pokemon_cards  LIMIT  (:page - 1) * :pageSize,:pageSize")
    suspend fun getAll(page : Int, pageSize: Int): List<PockemonCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<PockemonCardEntity>)

    @Query("SELECT COUNT(id) from pokemon_cards")
     fun getCount() : Int
}