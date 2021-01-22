package com.example.dstarinterviewnotes.ui

import android.content.Intent
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
import com.example.dstarinterviewnotes.utils.viewBinding
import kotlinx.android.synthetic.main.fragment_note_detail.*
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding : FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.languageSpinner.adapter = arrayAdapter
        }



        binding.settingsConfirm.setOnClickListener {
            setLocale(binding.languageSpinner.selectedItem.toString())

        }

        binding.themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked){
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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

    //Метод с deprecated методами, но работает
    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().finish()
        startActivity(refresh)
    }



}