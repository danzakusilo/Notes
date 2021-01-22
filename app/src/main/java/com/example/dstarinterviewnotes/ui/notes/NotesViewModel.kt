package com.example.dstarinterviewnotes.ui.notes

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.data.source.repository.NoteRepository
import com.example.dstarinterviewnotes.utils.MResult
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.jvm.Throws

class NotesViewModel @ViewModelInject constructor(private val repository: NoteRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val notes = MutableLiveData<List<NoteEntity>?>()
    val errorMessage = MutableLiveData<String?>()

    fun getNotes(){
        isLoading.postValue(true)
        viewModelScope.launch {
            when(val result = repository.getNotes()){
                is MResult.Success -> {
                    isLoading.value = false
                    if (result.data != null) {
                        notes.value = result.data.reversed()
                    } else {
                        errorMessage.value = "No notes yet"
                    }
                }
                is MResult.Error -> {
                    isLoading.value = false
                    errorMessage.value = result.exception.toString()
                }
            }
        }
    }

    fun deleteNote(id : Int){
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

}