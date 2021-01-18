package com.example.dstarinterviewnotes.data.database.entities.converters

import androidx.room.TypeConverter
import com.example.dstarinterviewnotes.data.database.NoteCategory

object NoteCategoryConverter {

    @TypeConverter
    @JvmStatic
    fun toString(value : NoteCategory?) : String{
        return value.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toCategory(string : String) : NoteCategory {
        return NoteCategory.valueOf(string)
    }
}