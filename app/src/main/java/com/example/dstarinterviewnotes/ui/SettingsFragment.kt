package com.example.dstarinterviewnotes.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.dstarinterviewnotes.MainActivity
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.databinding.FragmentSettingsBinding
import com.example.dstarinterviewnotes.utils.setLocale
import com.example.dstarinterviewnotes.utils.viewBinding
import kotlinx.android.synthetic.main.fragment_note_detail.*
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding : FragmentSettingsBinding
    private var sharedPref : SharedPreferences? = null
    private var language : String? = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.themeSwitch.isChecked =
                when(AppCompatDelegate.getDefaultNightMode()){
                    AppCompatDelegate.MODE_NIGHT_YES -> true
                    AppCompatDelegate.MODE_NIGHT_NO -> false
                    else -> false
                }
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.languageSpinner.adapter = arrayAdapter
        }




        binding.settingsConfirm.setOnClickListener {
            setLocale(binding.languageSpinner.selectedItem.toString(), requireActivity())
            with(sharedPref!!.edit()){
                putString("LANGUAGE", binding.languageSpinner.selectedItem.toString())
                apply()
            }
            setLocale(sharedPref!!.getString("LANGUAGE", "ru"), requireActivity())
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    with(sharedPref!!.edit()){
                        putInt("NIGHT_MODE", 2)
                        apply()
                    }
                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    with(sharedPref!!.edit()){
                        putInt("NIGHT_MODE", 1)
                        apply()

                    }

                }
            }
        }
    }


//    fun setLocal(lang : String?){
//        val myLocale = Locale(lang)
//        val conf = requireActivity().resources.configuration
//        conf.setLocale(myLocale)
//        requireActivity().createConfigurationContext(conf)
//        val refresh = Intent(requireActivity(), MainActivity::class.java)
//        requireActivity().finish()
//        startActivity(refresh)
//    }




}