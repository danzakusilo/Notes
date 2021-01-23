package com.example.dstarinterviewnotes

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dstarinterviewnotes.databinding.ActivityMainBinding
import com.example.dstarinterviewnotes.utils.setDarkMode
import com.example.dstarinterviewnotes.utils.setLocale
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sharedPref : SharedPreferences? = null
    private var language : String? = ""
    private var nightMode : Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        nightMode = sharedPref!!.getInt("NIGHT_MODE", 1)
        language = sharedPref!!.getString("LANGUAGE", "en")
        setLocale(language, this)
        AppCompatDelegate.setDefaultNightMode(nightMode)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_notes, R.id.navigation_settings,
        )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


       binding.container.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.container.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.container.rootView.height
            val keyPadHeight = screenHeight - rect.bottom
            val navbar : BottomNavigationView = binding.navView
            if (keyPadHeight > screenHeight * 0.15){
                navbar.visibility = View.GONE
            }else{
                navbar.visibility = View.VISIBLE
            }
        }

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