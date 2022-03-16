package com.x_a_technologies.currency_converter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.x_a_technologies.currency_converter.R
import com.x_a_technologies.currency_converter.activities.MainActivity
import com.x_a_technologies.currency_converter.adapters.FavoritesAdapter
import com.x_a_technologies.currency_converter.databinding.FragmentFavoritesSelectionBinding
import com.x_a_technologies.currency_converter.datas.DataLists

class FavoritesSelectionFragment(val callFragmentName:String,val settingsMode:Boolean=false) : Fragment() {

    lateinit var binding: FragmentFavoritesSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (callFragmentName == "home") {
            binding.favoritesName.text = getText(R.string.mainFavoritesSettings)
            binding.recyclerCurrencyAllData.adapter =
                FavoritesAdapter(DataLists.favoritesSelectedList,callFragmentName)
        }else if (callFragmentName == "converter"){
            binding.favoritesName.text = getText(R.string.converterFavoritesSettings)
            binding.recyclerCurrencyAllData.adapter =
                FavoritesAdapter(DataLists.convFavoritesSelectedList,callFragmentName)
        }

        binding.back.setOnClickListener(){
            when {
                settingsMode -> {
                    replaceFragmentFavorites(SettingsFragment())
                }
                callFragmentName == "home" -> {
                    replaceFragmentFavorites(HomeFragment())
                }
                callFragmentName == "converter" -> {
                    replaceFragmentFavorites(ConverterFragment())
                }
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    settingsMode -> {
                        replaceFragmentFavorites(SettingsFragment())
                    }
                    callFragmentName == "home" -> {
                        replaceFragmentFavorites(HomeFragment())
                    }
                    callFragmentName == "converter" -> {
                        replaceFragmentFavorites(ConverterFragment())
                    }
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    fun replaceFragmentFavorites(fragment:Fragment){
        val mainActivityView = activity as MainActivity
        mainActivityView.replaceFragment(fragment)
    }
}