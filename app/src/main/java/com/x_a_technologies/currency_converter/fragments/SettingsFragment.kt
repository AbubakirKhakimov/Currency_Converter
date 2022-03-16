package com.x_a_technologies.currency_converter.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x_a_technologies.currency_converter.adapters.SettingsAdapter
import com.x_a_technologies.currency_converter.datas.SettingsData
import com.x_a_technologies.currency_converter.databinding.FragmentSettingsBinding
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import com.x_a_technologies.currency_converter.adapters.CallBackSettings
import com.x_a_technologies.currency_converter.datas.DataLists
import com.x_a_technologies.currency_converter.activities.MainActivity
import com.x_a_technologies.currency_converter.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.collections.ArrayList

import android.widget.TextView


class SettingsFragment : Fragment(), CallBackSettings {

    lateinit var binding: FragmentSettingsBinding
    val itemsDataList = ArrayList<SettingsData>()
    lateinit var pref: SharedPreferences
    private lateinit var main: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        main = activity as MainActivity
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = context?.getSharedPreferences("SettingsFile", MODE_PRIVATE)!!

        itemsDataList.add(SettingsData(getString(R.string.languageSettings),analisLanguage()))
        itemsDataList.add(
            SettingsData(getString(R.string.mainFavoritesSettings)
            ,selectToString(DataLists.favoritesSelectedList))
        )
        itemsDataList.add(
            SettingsData(getString(R.string.converterFavoritesSettings)
            ,selectToString(DataLists.convFavoritesSelectedList))
        )

        binding.settingsRecyclerView.adapter = SettingsAdapter(itemsDataList, this)

        binding.call.setOnClickListener(){
            val arrEmail = arrayOf("developer.abubakir2002@gmail.com")
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL,arrEmail)
                putExtra(Intent.EXTRA_SUBJECT, "Currency converter app")
            }
            startActivity(Intent.createChooser(intent, getString(R.string.emailSelectMenu)))
        }

        binding.putStar.setOnClickListener(){
//            val callUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//            startActivity(callUrlIntent)
        }

    }

    fun analisLanguage():String{
        val language = pref.getString("languageCode","empty")

        return if (language=="empty"){
            when(Locale.getDefault().language){
                "uz" -> getString(R.string.uzbekLanguage)
                "ru" -> getString(R.string.russianLanguage)
                else -> getString(R.string.englishLanguage)
            }
        }else{
            when(language){
                "uz" -> getString(R.string.uzbekLanguage)
                "ru" -> getString(R.string.russianLanguage)
                else -> getString(R.string.englishLanguage)
            }
        }
    }

    fun selectToString(list: ArrayList<Boolean>):String{
        return if (DataLists.currancyDataList.size==0){
            "[ ]"
        }else {
            var selectedItems = "["
            for (i in list.indices) {
                if (list[i]) {
                    selectedItems += DataLists.currancyDataList[i].Ccy + ","
                }
            }

            selectedItems.substring(0, selectedItems.length - 1) + "]"
        }
    }

    fun replaceFragmentSettings(fragment:Fragment){
        main.replaceFragment(fragment)
    }

    override fun onClick(position: Int) {
        when(position){
            0->showLanguageDialog()
            1->replaceFragmentSettings(FavoritesSelectionFragment("home",true))
            2->replaceFragmentSettings(FavoritesSelectionFragment("converter",true))
        }
    }

    fun showLanguageDialog(){
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog!!.setContentView(R.layout.language_dialog_layout)

        bottomSheetDialog.findViewById<TextView>(R.id.english)!!.setOnClickListener(){
            setLanguage("en")
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.findViewById<TextView>(R.id.russian)!!.setOnClickListener(){
            setLanguage("ru")
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.findViewById<TextView>(R.id.uzbek)!!.setOnClickListener(){
            setLanguage("uz")
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    fun setLanguage(language: String){
        main.setLocale(language)
    }
}