package com.example.procoreinterview.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_cards")
data class PockemonCardEntity(
    @PrimaryKey val id: String,
    val name: String,
    val hp: String,
    val imageUrl: String
)
