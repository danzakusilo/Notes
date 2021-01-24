package com.example.dstarinterviewnotes.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


fun Fragment.hideKeyboard(){
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard(){
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view : View){
    val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}