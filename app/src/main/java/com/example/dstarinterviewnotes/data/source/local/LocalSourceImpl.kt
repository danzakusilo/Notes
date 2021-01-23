package com.example.dstarinterviewnotes.data.source.local

import com.example.dstarinterviewnotes.data.source.local.database.dao.NotesDao
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteUpdate
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val dao : NotesDao) : LocalSource {
    override suspend fun changeNoteImageUri(id: Int, newUri: String?) {
        dao.changeNoteImageUri(id, newUri)
    }

    override suspend fun insertNote(note: NoteEntity) {
        dao.insertNote(note)
    }

    override suspend fun getNoteById(id: Int): NoteEntity {
        return dao.getNoteById(id)
    }

    override suspend fun getNotes(): List<NoteEntity> {
        return dao.getAllNotes()
    }

    override suspend fun getByCategory(category: String): List<NoteEntity> {
        return dao.getByCategory(category)
    }

    override suspend fun deleteNote(id: Int) {
        dao.deleteNote(id)
    }

    override suspend fun changeNoteTitle(id: Int, newTitle: String?) {
        dao.changeNoteTitle(id, newTitle)
    }

    override suspend fun changeNoteContent(id: Int, newContent: String?) {
       dao.changeNoteContent(id, newContent)
    }

    override suspend fun changeNoteCategory(id: Int, newCategory: String?) {
        dao.changeNoteCategory(id, newCategory)
    }
}