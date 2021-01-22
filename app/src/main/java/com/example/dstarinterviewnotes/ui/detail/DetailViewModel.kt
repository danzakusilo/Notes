package com.example.dstarinterviewnotes.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.repository.NoteRepository
import com.example.dstarinterviewnotes.utils.MResult
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(private val repository: NoteRepository) : ViewModel() {
    val note = MutableLiveData<NoteEntity>()
    val errorMessage = MutableLiveData<String>()

    fun getNote(id : Int){
        viewModelScope.launch {
            when(val result = repository.getNoteById(id)){
                is MResult.Success ->{
                    if (result.data != null){
                        note.value = result.data
                    }else{
                        errorMessage.value = "Is null"
                    }
                }
                is MResult.Error ->
                    errorMessage.value = result.exception.toString()
            }

        }
    }

    fun saveNote(noteEntity: NoteEntity){
        viewModelScope.launch {
            repository.insertNote(noteEntity)
        }
    }

    fun editTitle(id : Int, newTitle : String?){
        viewModelScope.launch {
            repository.changeNoteTitle(id ,newTitle)
        }
    }

    fun editContent(id : Int, newContent : String?){
        viewModelScope.launch {
            repository.changeNoteContent(id, newContent)
        }
    }

    fun editCategory(id : Int, newCategory : String){
        viewModelScope.launch {
            repository.changeNoteCategory(id, newCategory)
        }
    }
}