package com.example.dstarinterviewnotes.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.data.source.local.database.entities.converters.NoteCategoryConverter

@TypeConverters(NoteCategoryConverter::class)
@Entity(tableName = "Note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String?,
    val content : String?,
    val category : NoteCategory,
    val creationTime : Long,
    val imageURI : String?
)

@Entity
data class NoteUpdate(
    val title: String?,
    val content: String?,
    val category: NoteCategory,
    val imageURI: String?
)
