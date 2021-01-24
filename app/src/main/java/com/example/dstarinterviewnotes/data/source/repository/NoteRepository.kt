package com.example.dstarinterviewnotes.data.source.repository

import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteUpdate
import com.example.dstarinterviewnotes.utils.MResult

interface NoteRepository {


    suspend fun insertNote(note : NoteEntity)

    suspend fun getNotes() : MResult<List<NoteEntity>?>

    suspend fun getNoteById(id : Int) : MResult<NoteEntity?>

    suspend fun deleteNote(id : Int)

    suspend fun updateNoteTitle(id : Int, newTitle : String?)

    suspend fun updateNoteContent(id : Int, newContent: String?)

    suspend fun updateNoteImageUri(id: Int, newUri : String?)
}