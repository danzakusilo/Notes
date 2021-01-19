package com.example.dstarinterviewnotes.data.source.repository

import com.example.dstarinterviewnotes.data.source.local.LocalSource
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(localSource: LocalSource) : NoteRepository {
}