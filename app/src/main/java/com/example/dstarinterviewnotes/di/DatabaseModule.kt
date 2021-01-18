package com.example.dstarinterviewnotes.di

import android.app.Application
import com.example.dstarinterviewnotes.data.source.local.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase(application: Application) = NotesDatabase.getDataBase(application)

    @Singleton
    @Provides
    fun getDao(database: NotesDatabase) = database.getDao()
}