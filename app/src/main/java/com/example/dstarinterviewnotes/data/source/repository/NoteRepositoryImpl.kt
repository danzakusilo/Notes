package com.example.dstarinterviewnotes.data.source.repository

import com.example.dstarinterviewnotes.data.source.local.database.dao.NotesDao
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.utils.MResult
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val dao: NotesDao) : NoteRepository {
    override suspend fun changeNoteImageUri(id: Int, newUri: String?) {
        dao.changeNoteImageUri(id, newUri)
    }

    override suspend fun insertNote(note: NoteEntity) {
        dao.insertNote(note)
    }

    override suspend fun getNotes(): MResult<List<NoteEntity>> {
        val notes = dao.getAllNotes()
        return if (notes != null){
            MResult.Success(notes)
        }else{
            MResult.Success(null)
        }
    }

    override suspend fun getNoteById(id: Int): MResult<NoteEntity?> {
        val note = dao.getNoteById(id)
        return if (note != null){
            MResult.Success(note)
        }else{
            MResult.Success(null)
        }
    }

    override suspend fun getByCategory(category: String): MResult<List<NoteEntity>?>{
        val notes = dao.getByCategory(category)
        return MResult.Success(notes) ?: MResult.Success(null)
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

    override suspend fun changeNoteCategory(id: Int, newCategory: String) {
        dao.changeNoteCategory(id, newCategory)
    }
}