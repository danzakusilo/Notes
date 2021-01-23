package com.example.dstarinterviewnotes.data.source.local

import androidx.room.Query
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteUpdate

interface LocalSource {

    suspend fun changeNoteImageUri(id: Int, newUri : String?)

    suspend fun insertNote(note : NoteEntity)

    suspend fun getNoteById(id : Int) : NoteEntity?

    suspend fun getNotes() : List<NoteEntity>?

    suspend fun getByCategory(category : String) : List<NoteEntity>?

    suspend fun deleteNote(id : Int)

    suspend fun changeNoteTitle(id : Int, newTitle : String?)

    suspend fun changeNoteContent(id : Int, newContent: String?)

    suspend fun changeNoteCategory(id : Int, newCategory : String?)
}