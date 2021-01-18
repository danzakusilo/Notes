package com.example.dstarinterviewnotes.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM Note")
    suspend fun getAllNotes() : List<NoteEntity>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("UPDATE NOTE SET title = :newTitle where id = :id")
    suspend fun changeNoteTitle(id : Int, newTitle : String?)

    @Query("Update note set content = :newContent where id = :id")
    suspend fun changeNoteContent(id : Int, newContent: String?)

    @Query("UPDATE NOTE SET category = :newCategory where id = :id")
    suspend fun changeNoteCategory(id : Int, newCategory : String)

}