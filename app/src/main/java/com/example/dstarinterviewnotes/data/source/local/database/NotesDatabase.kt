package com.example.dstarinterviewnotes.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dstarinterviewnotes.data.source.local.database.dao.NotesDao
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getDao():NotesDao
    companion object {
        @Volatile
        private var INSTANCE : NotesDatabase? = null


        fun getDataBase(context: Context) : NotesDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "NotesDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}