package com.example.dstarinterviewnotes.data.source.repository

import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteUpdate
import com.example.dstarinterviewnotes.utils.MResult

interface NoteRepository {

    suspend fun changeNoteImageUri(id: Int, newUri : String?)

    suspend fun insertNote(note : NoteEntity)

    suspend fun getNotes() : MResult<List<NoteEntity>?>

    suspend fun getNoteById(id : Int) : MResult<NoteEntity?>

    suspend fun getByCategory(category : String) : MResult<List<NoteEntity>?>

    suspend fun deleteNote(id : Int)

    suspend fun changeNoteTitle(id : Int, newTitle : String?)

    suspend fun changeNoteContent(id : Int, newContent: String?)

    suspend fun changeNoteCategory(id : Int, newCategory : String)
}