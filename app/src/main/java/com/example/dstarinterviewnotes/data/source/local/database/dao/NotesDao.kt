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

    @Query("delete from Note where id = :id")
    suspend fun deleteNote(id: Int)

    /*
    В Room есть поддрежка UPDATE запросов, в которых нужно создать новую сущность для обновлений, типа
    UpdateNote(*values to udpate*), но я понимаю это так, что при этом нужно заранее знать,
    какие значение будут обновляться всегда при вызове этой подсущности, что не рабоатет в моём случае,
    поэтому для каждого поля свой метод для обновления
    */
    @Query("UPDATE NOTE SET title = :newTitle where id = :id")
    suspend fun updateNoteTitle(id : Int, newTitle : String?)

    @Query("Update note set content = :newContent where id = :id")
    suspend fun updateNoteContent(id : Int, newContent: String?)

    @Query("UPDATE NOTE SET imageURI = :newUri where id = :id")
    suspend fun updateNoteImageUri(id: Int, newUri : String?)
}