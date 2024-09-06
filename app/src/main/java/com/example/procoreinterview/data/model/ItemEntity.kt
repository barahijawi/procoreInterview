package com.example.procoreinterview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id :Int,
    val name: String,
    val description: String,
    val image :String
)
