package com.example.procoreinterview.data.database

import androidx.room.TypeConverter

class Convertors
{
    @TypeConverter
    fun fromListToString(list: List<String> ): String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(value: String) :List<String> {
        return value.split(",")
    }
}