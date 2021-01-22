package com.example.dstarinterviewnotes.data.source.repository

import com.example.dstarinterviewnotes.data.source.local.LocalSource
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.utils.MResult
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val localSource: LocalSource) : NoteRepository {

    override suspend fun insertNote(note: NoteEntity) {
        localSource.insertNote(note)
    }

    override suspend fun getNotes(): MResult<List<NoteEntity>> {
        val notes = localSource.getNotes()
        return if (notes != null){
            MResult.Success(notes)
        }else{
            MResult.Success(null)
        }
    }

    override suspend fun getNoteById(id: Int): MResult<NoteEntity?> {
        val note = localSource.getNoteById(id)
        return if (note != null){
            MResult.Success(note)
        }else{
            MResult.Success(null)
        }
    }

    override suspend fun getByCategory(category: String): MResult<List<NoteEntity>?>{
        val notes = localSource.getByCategory(category)
        return MResult.Success(notes) ?: MResult.Success(null)
    }

    override suspend fun deleteNote(id: Int) {
        localSource.deleteNote(id)
    }

    override suspend fun changeNoteTitle(id: Int, newTitle: String?) {
        localSource.changeNoteTitle(id, newTitle)
    }

    override suspend fun changeNoteContent(id: Int, newContent: String?) {
        localSource.changeNoteContent(id, newContent)
    }

    override suspend fun changeNoteCategory(id: Int, newCategory: String) {
        localSource.changeNoteCategory(id, newCategory)
    }
}