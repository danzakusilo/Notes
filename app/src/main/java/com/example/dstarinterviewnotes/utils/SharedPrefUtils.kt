package com.example.dstarinterviewnotes.utils

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatDelegate
import com.example.dstarinterviewnotes.MainActivity
import java.util.*


fun setLocale(lang: String?, context: Context) {
    val myLocale = Locale(lang)
    val res: Resources = context.resources
    val dm: DisplayMetrics = res.displayMetrics
    val conf: Configuration = res.configuration
    conf.locale = myLocale
    res.updateConfiguration(conf, dm)
}


fun setDarkMode(darkMode : Boolean){
    when(darkMode){
        true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}


