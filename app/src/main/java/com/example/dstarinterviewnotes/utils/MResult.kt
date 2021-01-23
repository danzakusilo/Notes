package com.example.dstarinterviewnotes.utils

import java.lang.Exception

sealed class MResult<out R> {


    data class Success<out T>(val data : T?) : MResult<T>()

    data class Error(val exception: Exception): MResult<Nothing>()

    object Loading : MResult<Nothing>()


    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception = $exception]"
            is Loading -> "Loading"
        }
    }
}