package com.example.dstarinterviewnotes.data.source.local.database.dao

import androidx.room.*
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteUpdate

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NOTE WHERE id = :id")
    suspend fun getNoteById(id : Int) : NoteEntity?

    @Query("SELECT * FROM Note ORDER BY creationTime")
    suspend fun getAllNotes() : List<NoteEntity>?

    @Query("SELECT * FROM NOTE WHERE category = :category")
    suspend fun getByCategory(category : String) : List<NoteEntity>?

    @Query("delete from Note where id = :id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE NOTE SET title = :newTitle where id = :id")
    suspend fun changeNoteTitle(id : Int, newTitle : String?)

    @Query("Update note set content = :newContent where id = :id")
    suspend fun changeNoteContent(id : Int, newContent: String?)

    @Query("UPDATE NOTE SET category = :newCategory where id = :id")
    suspend fun changeNoteCategory(id : Int, newCategory : String?)

    @Query("UPDATE NOTE SET imageURI = :newUri where id = :id")
    suspend fun changeNoteImageUri(id: Int, newUri : String?)
}