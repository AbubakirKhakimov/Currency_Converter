package com.x_a_technologies.currency_converter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x_a_technologies.currency_converter.R
import com.x_a_technologies.currency_converter.databinding.ActivityItemLayoutBinding
import com.x_a_technologies.currency_converter.datas.CurrencyData
import com.x_a_technologies.currency_converter.datas.DataLists
import java.util.*

class HomeFavoritesAdapter: RecyclerView.Adapter<HomeFavoritesAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ActivityItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    val favoritesDataList = ArrayList<CurrencyData>()
    val favoritesItemsIconList = ArrayList<Int>()
    lateinit var context:Context
    var language = ""

    init {
        sortFavorites()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        return ItemHolder(ActivityItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemText = favoritesDataList[position]
        holder.binding.currencyName.text = analisLanguage(itemText)
        holder.binding.currencySum.text = "${itemText.Rate} ${context.getString(R.string.uzs)}"
        holder.binding.countryFlagIcon.setImageResource(favoritesItemsIconList[position])
    }

    override fun getItemCount(): Int {
        return favoritesDataList.size
    }

    fun sortFavorites(){
        favoritesDataList.clear()
        favoritesItemsIconList.clear()

        for (i in DataLists.currancyDataList.indices){
            if (DataLists.favoritesSelectedList[i]){
                favoritesDataList.add(DataLists.currancyDataList[i])
                favoritesItemsIconList.add(DataLists.itemsIcon[i])
            }
        }
    }

    fun analisLanguage(item: CurrencyData):String{
        if (language.isEmpty()){
            read()
        }

        return if (language=="empty"){
            when(Locale.getDefault().language){
                "uz" -> item.CcyNm_UZ
                "ru" -> item.CcyNm_RU
                else -> item.CcyNm_EN
            }
        }else{
            when(language){
                "uz" -> item.CcyNm_UZ
                "ru" -> item.CcyNm_RU
                else -> item.CcyNm_EN
            }
        }
    }

    fun read(){
        val pref = context.getSharedPreferences(DataLists.cashFileName, Context.MODE_PRIVATE)
        language = pref.getString("languageCode","empty")!!
    }
}