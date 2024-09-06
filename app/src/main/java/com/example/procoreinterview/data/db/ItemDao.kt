package com.example.procoreinterview.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.procoreinterview.data.model.ItemDetailsEntity
import com.example.procoreinterview.data.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemEntity>)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<ItemEntity>>


}