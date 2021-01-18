package com.example.dstarinterviewnotes.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dstarinterviewnotes.data.source.local.database.entities.converters.NoteCategoryConverter

@TypeConverters(NoteCategoryConverter::class)
@Entity(tableName = "Note")
data class NoteEntity(
    @PrimaryKey val id : Int,
    val title : String?,
    val content : String?,
    val category : String,
    val creationTime : Long
)