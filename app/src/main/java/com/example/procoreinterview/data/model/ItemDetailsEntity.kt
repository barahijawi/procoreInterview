package com.example.procoreinterview.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "item_details",
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItemDetailsEntity(
   @PrimaryKey(autoGenerate = true,) val itemDetailsId :Int = 0,
   val itemId:Int,
   val additionalInfo:String,
   val moreDetails:String
)

