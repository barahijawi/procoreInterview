package com.example.procoreinterview.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PockemonCardDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<PockemonCardEntity>)

    @Query("SELECT * FROM pockemon_cards")
    fun getAllPockemonCards():Flow<List<PockemonCardEntity>>

    @Query("SELECT MIN(CAST(hp AS INTEGER)) AS minHp, MAX(CAST(hp AS INTEGER)) AS maxHp FROM pockemon_cards")
    suspend fun getMinMaxHP(): MinMaxHp
}