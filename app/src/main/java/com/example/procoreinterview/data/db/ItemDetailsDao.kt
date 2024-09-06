package com.example.procoreinterview.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.procoreinterview.data.model.ItemDetailsEntity

@Dao
interface ItemDetailsDao{
    @Insert
    suspend fun insert(itemDetails:ItemDetailsEntity)

    @Query("SELECT * FROM item_details WHERE itemId = :itemId  LIMIT 1 ")
    fun getItemDetails(itemId: String): ItemDetailsEntity


}