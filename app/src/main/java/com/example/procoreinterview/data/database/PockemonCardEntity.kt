package com.example.procoreinterview.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pockemon_cards")
data class PockemonCardEntity(
    @PrimaryKey val id: String,
    val name: String,
    val hp:String,
    val imageUrl: String,
    val imageUrlLarge: String, // added
    val superType: String?, // added
    val types: String, // comma-separated string
    val subTypes: String, // comma-separated string


)
