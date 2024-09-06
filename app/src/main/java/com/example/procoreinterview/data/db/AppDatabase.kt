package com.example.procoreinterview.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.procoreinterview.data.model.ItemDetailsEntity
import com.example.procoreinterview.data.model.ItemEntity

@Database(
    entities = [ItemEntity::class,ItemDetailsEntity::class], version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun itemDao(): ItemDao
    abstract fun itemDetailsDao():ItemDetailsDao

//    companion object
//    {
//        val MIGRATION_1_2 = object : Migration(1, 2)
//        {
//            override fun migrate(db: SupportSQLiteDatabase)
//            {
//                // Add the new column to the existing table
//                db.execSQL("ALTER TABLE items ADD COLUMN description TEXT DEFAULT '' NOT NULL")
//            }
//        }
//    }
}