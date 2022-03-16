package com.x_a_technologies.currency_converter.adapters

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Configuration
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.x_a_technologies.currency_converter.databinding.ActivityConverterItemLayoutBinding
import com.x_a_technologies.currency_converter.datas.CurrencyData
import com.x_a_technologies.currency_converter.datas.DataLists
import java.text.DecimalFormat
import java.util.*

interface ConverterAdapterCallBack{
    fun itemOnClick()
    fun itemValue(value:String)
}

class ConverterAdapter(val callBack: ConverterAdapterCallBack):
    RecyclerView.Adapter<ConverterAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ActivityConverterItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    var convFavoritesDataList = ArrayList<CurrencyData>()
    var convFavoritesItemsIconList = ArrayList<Int>()
    private lateinit var context: Context
    var selectedItemPosition = -1
    var language = ""
    var intefaceColor = ""

    init {
        sortConvFavorites()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        getThemeColor()
        return ItemHolder(ActivityConverterItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val item = convFavoritesDataList[position]
        holder.binding.currencySymbol.text = item.Ccy
        holder.binding.currencyName.text = analisLanguage(item)
        holder.binding.countryFlagIcon.setImageResource(convFavoritesItemsIconList[position])

        if (selectedItemPosition==position){
            holder.binding.numberEnter.setHintTextColor(Color.parseColor("#4CAF50"))
            holder.binding.numberEnter.setTextColor(Color.parseColor("#4CAF50"))
        }else{
            holder.binding.numberEnter.setHintTextColor(Color.parseColor(intefaceColor))
            holder.binding.numberEnter.setTextColor(Color.parseColor(intefaceColor))
        }

        if (DataLists.itemsValueList.size == 0) {
            holder.binding.numberEnter.text.clear()
        }else{
            holder.binding.numberEnter.text =
                Editable.Factory.getInstance().newEditable(DataLists.itemsValueList[position])
        }

        holder.binding.cardView.setOnClickListener() {
            holder.binding.numberEnter.isFocusableInTouchMode = true
            selectedItemPosition=position

            if (!holder.binding.numberEnter.isFocused) {
                DataLists.itemsValueList.clear()
                holder.binding.numberEnter.text.clear()
                callBack.itemOnClick()

                for (i in convFavoritesDataList.indices) {
                    if (i != position) {
                        notifyItemChanged(i)
                    }
                }
            }

            holder.binding.numberEnter.setHintTextColor(Color.parseColor("#4CAF50"))
            holder.binding.numberEnter.setTextColor(Color.parseColor("#4CAF50"))

            holder.binding.numberEnter.requestFocus()
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(holder.binding.numberEnter, InputMethodManager.SHOW_IMPLICIT)
            holder.binding.numberEnter.isFocusableInTouchMode = false
        }

        holder.binding.numberEnter.setOnClickListener() {
            holder.binding.numberEnter.isFocusableInTouchMode = true
            selectedItemPosition=position

            if (!holder.binding.numberEnter.isFocused) {
                DataLists.itemsValueList.clear()
                holder.binding.numberEnter.text.clear()
                callBack.itemOnClick()

                for (i in convFavoritesDataList.indices) {
                    if (i != position) {
                        notifyItemChanged(i)
                    }
                }
            }

            if (holder.binding.numberEnter.isFocused){
                holder.binding.numberEnter.setSelection(holder.binding.numberEnter.text.length)
            }

            holder.binding.numberEnter.setHintTextColor(Color.parseColor("#4CAF50"))
            holder.binding.numberEnter.setTextColor(Color.parseColor("#4CAF50"))

            holder.binding.numberEnter.requestFocus()
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(holder.binding.numberEnter, InputMethodManager.SHOW_IMPLICIT)
            holder.binding.numberEnter.isFocusableInTouchMode = false
        }

        holder.binding.numberEnter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (holder.binding.numberEnter.isFocused) {
                    when (s.toString()){
                        "0" -> {
                            holder.binding.numberEnter.text.clear()
                        }
                        "." -> {
                            holder.binding.numberEnter.text.clear()
                            holder.binding.numberEnter.text = Editable.Factory.getInstance().newEditable("0.")
                            holder.binding.numberEnter.setSelection(holder.binding.numberEnter.text.length)
                        }
                        else -> {
                            var valueUzb = ""
                            DataLists.itemsValueList.clear()

                            if (s.toString() != "") {
                                //O'zbekiston so'mi uchun qiymat
                                val value =
                                    convFavoritesDataList[selectedItemPosition].Rate.toDouble() * s.toString().toDouble()
                                valueUzb = DecimalFormat("#,##0.00").format(value)

                                //Boshqa davlat valyutalari uchun qiymat
                                for (i in convFavoritesDataList.indices){
                                    if (i==selectedItemPosition){
                                        DataLists.itemsValueList.add(s.toString())
                                    }else {
                                        val valueOtherCurrency = s.toString().toDouble() *
                                                convFavoritesDataList[selectedItemPosition].Rate.toDouble() /
                                                convFavoritesDataList[i].Rate.toDouble()
                                        DataLists.itemsValueList.add(DecimalFormat("#,##0.00").format(valueOtherCurrency))
                                    }
                                }
                            }

                            callBack.itemValue(valueUzb)
                            for (i in convFavoritesDataList.indices) {
                                if (i != selectedItemPosition) {
                                    notifyItemChanged(i)
                                }
                            }
                        }
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    override fun getItemCount(): Int {
        return convFavoritesDataList.size
    }

    fun sortConvFavorites(){
        convFavoritesDataList.clear()
        convFavoritesItemsIconList.clear()

        for (i in DataLists.currancyDataList.indices){
            if (DataLists.convFavoritesSelectedList[i]){
                convFavoritesDataList.add(DataLists.currancyDataList[i])
                convFavoritesItemsIconList.add(DataLists.itemsIcon[i])
            }
        }
    }

    fun getThemeColor() {
        val currentNightMode: Int = context.resources
            .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        intefaceColor = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> "#FF000000"
            Configuration.UI_MODE_NIGHT_YES -> "#FFFFFFFF"
            else -> "#FF000000"
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