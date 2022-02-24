package com.example.currencyconverter.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyconverter.datas.DataLists
import com.example.currencyconverter.R
import java.util.*

class SettingsApplyActivity : AppCompatActivity() {

    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_apply)
        pref = getSharedPreferences(DataLists.cashFileName, MODE_PRIVATE)

        checkLanguage()

        startActivity(Intent(this, LoadingActivity()::class.java))
        finish()
    }

    fun checkLanguage(){
        val language = pref.getString("languageCode","empty")

        if (language!="empty"){
            setLocale(language!!)
        }
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
    }
}