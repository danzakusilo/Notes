package com.example.dstarinterviewnotes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dstarinterviewnotes.databinding.ActivityMainBinding
import com.example.dstarinterviewnotes.utils.setLocale
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sharedPref : SharedPreferences? = null
    private var language : String? = ""
    private var nightMode : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        restoreSettings()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_notes, R.id.navigation_settings))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun restoreSettings(){
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        nightMode = sharedPref!!.getInt("NIGHT_MODE", 1)
        language = sharedPref!!.getString("LANGUAGE", "en")
        setLocale(language, this)
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        nightMode = AppCompatDelegate.getDefaultNightMode()
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        language = sharedPref!!.getString("LANGUAGE", "en")
        with(sharedPref!!.edit()){
            putInt("NIGHT_MODE", nightMode)
            putString("LANGUAGE", language)
            apply()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}