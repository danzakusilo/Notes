package com

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.data.source.local.database.NotesDatabase
import com.example.dstarinterviewnotes.data.source.local.database.dao.NotesDao
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DatabaseTest {
    private lateinit var dao : NotesDao
    private lateinit var db : NotesDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NotesDatabase::class.java).build()
        dao = db.getDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        db.close()
    }


    @Test
    @Throws(IOException::class)
        fun writeNoteAndReadAllNotes() = runBlockingTest {
        val note: NoteEntity = NoteEntity(
            1,
            "TestTitle",
            "TestContent",
            NoteCategory.DEFAULT.toString(),
            System.currentTimeMillis()
        )
        dao.insertNote(note)
        val notes = dao.getAllNotes()
        assert(notes[0].equals(note))
    }

    @Test
    @Throws(IOException::class)
    fun readWriteAssertDoesntEqualToWrongNote() = runBlockingTest {
        val fakeNote : NoteEntity = NoteEntity(
                2,
                "FakeTitle",
                "FakeContent",
                NoteCategory.DEFAULT.toString(),
                System.currentTimeMillis()
        )
        val trueNote = NoteEntity(
        1,
        "TestTitle",
        "TestContent",
        NoteCategory.DEFAULT.toString(),
        System.currentTimeMillis()
        )
        dao.insertNote(trueNote)
        val notes = dao.getAllNotes()
        assert(notes[0] != fakeNote)
    }
    @Test
    @Throws(IOException::class)
    fun testChangeNoteTitle() = runBlockingTest {
        val note: NoteEntity = NoteEntity(
                1,
                "TestTitle",
                "TestContent",
                NoteCategory.DEFAULT.toString(),
                System.currentTimeMillis()
        )
        dao.insertNote(note)
        dao.changeNoteTitle(1, "ChangedTestTitle")
        val notes = dao.getAllNotes()
        assert(notes[0].title == "ChangedTestTitle")
    }

}