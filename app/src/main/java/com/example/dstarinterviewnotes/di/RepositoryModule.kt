package com.example.dstarinterviewnotes.di

import com.example.dstarinterviewnotes.data.source.local.LocalSource
import com.example.dstarinterviewnotes.data.source.local.LocalSourceImpl
import com.example.dstarinterviewnotes.data.source.repository.NoteRepository
import com.example.dstarinterviewnotes.data.source.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLocalSource(
        localSourceImpl: LocalSourceImpl
    ) : LocalSource

    @Binds
    abstract fun bindRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ) : NoteRepository

}