package com.example.currencyconverter.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyconverter.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.example.currencyconverter.datas.DataLists
import com.example.currencyconverter.R
import com.example.currencyconverter.fragments.ConverterFragment
import com.example.currencyconverter.fragments.HomeFragment
import com.example.currencyconverter.fragments.ListCurrencyFragment
import com.example.currencyconverter.fragments.SettingsFragment
import java.util.*

open class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            DataLists.itemsValueList.clear()

            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.converter -> replaceFragment(ConverterFragment())
                R.id.allItems -> replaceFragment(ListCurrencyFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
            }
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentConteiner, fragment)
        transaction.commit()
    }

    fun setLocale(language:String){
        val config = resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        writeSettingsFile("languageCode",language)

        startActivity(Intent(this, MainActivity()::class.java))
        finish()
    }

    fun writeSettingsFile(key:String,information:String){
        val pref = getSharedPreferences("SettingsFile", MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString(key,information)
        edit.apply()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        startActivity(Intent(this, SettingsApplyActivity()::class.java))
        finish()
    }
}