package com.example.dstarinterviewnotes.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatDelegate
import com.example.dstarinterviewnotes.MainActivity
import java.util.*

/*
    В это методе есть утсаревшие вызовы, но чтобы от них избавиться, как я прочитал, нужно
    написать свой ContextWrapper или делать лишние неудобные и нерелевантные для этого проекта шаги,
    так что останется в таком виде.
 */
fun setLocale(lang: String?, context: Context) {
    val myLocale = Locale(lang)
    val res: Resources = context.resources
    val dm: DisplayMetrics = res.displayMetrics
    val conf: Configuration = res.configuration
    conf.setLocale(myLocale)
    res.updateConfiguration(conf, dm)
}




