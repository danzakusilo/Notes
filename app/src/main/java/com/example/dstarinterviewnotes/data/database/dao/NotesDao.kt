package com.example.dstarinterviewnotes.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.dstarinterviewnotes.data.database.entities.NoteEntity

@Dao
interface NotesDao {

    @Query("SELECT * FROM Note")
    suspend fun getAllNotes() : List<NoteEntity>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    //@Query("")

}