package com.example.dstarinterviewnotes.ui.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.dstarinterviewnotes.data.source.repository.NoteRepository

class NotesViewModel @ViewModelInject constructor(private val repository: NoteRepository) : ViewModel() {
}