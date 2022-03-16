package com.x_a_technologies.currency_converter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x_a_technologies.currency_converter.databinding.ActivityFavoritesItemLayoutBinding
import com.x_a_technologies.currency_converter.datas.CurrencyData
import com.x_a_technologies.currency_converter.datas.DataLists
import java.util.*

class FavoritesAdapter(val list:ArrayList<Boolean>, val fragmentName:String): RecyclerView.Adapter<FavoritesAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ActivityFavoritesItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var context:Context
    var language = ""
    var intefaceColor = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context=parent.context
        getThemeColor()
        return ItemHolder(ActivityFavoritesItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {


        holder.binding.cardView.setCardBackgroundColor(
            Color.parseColor(
                if (list[position]) "#5ACA5F"
                else intefaceColor
            )
        )


        val itemText = DataLists.currancyDataList[position]
        holder.binding.currencyName.text=analisLanguage(itemText)
        holder.binding.countryFlagIcon.setImageResource(DataLists.itemsIcon[position])

        holder.binding.cardView.setOnClickListener(){
            if (list[position]){
                list[position]=false
                holder.binding.cardView.setCardBackgroundColor(Color.parseColor(intefaceColor))
            }else{
                list[position]=true
                holder.binding.cardView.setCardBackgroundColor(Color.parseColor("#5ACA5F"))
            }

            if (fragmentName=="home") {
                DataLists.favoritesFileWrite(context)
            }else if (fragmentName == "converter"){
                DataLists.convFavoritesFileWrite(context)
            }
        }
    }

    override fun getItemCount(): Int {
        return DataLists.currancyDataList.size
    }

    fun getThemeColor() {
        val currentNightMode: Int = context.resources
            .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        intefaceColor = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> "#FFFFFFFF"
            Configuration.UI_MODE_NIGHT_YES -> "#202020"
            else -> "#FFFFFFFF"
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