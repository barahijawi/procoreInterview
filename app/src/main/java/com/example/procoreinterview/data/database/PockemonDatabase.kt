package com.example.procoreinterview.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PockemonCardEntity::class], version = 1)
@TypeConverters(Convertors::class)
abstract class PockemonDatabase : RoomDatabase()
{
    abstract fun pockemonCardDao(): PockemonCardDao

    companion object {
        @Volatile
        private var INSTANCE: PockemonDatabase? = null

        fun getDatabase(context: Context): PockemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PockemonDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}