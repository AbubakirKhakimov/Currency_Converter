package com.example.currencyconverter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.datas.SettingsData
import com.example.currencyconverter.databinding.ActivitySettingsItemLayoutBinding
interface CallBackSettings{
    fun onClick(position: Int)
}

class SettingsAdapter(val itemsDataList:ArrayList<SettingsData>,val callBackSettings: CallBackSettings):RecyclerView.Adapter<SettingsAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ActivitySettingsItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ActivitySettingsItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = itemsDataList[position]
        holder.binding.settingName.text = item.settingName
        holder.binding.currentValue.text = item.currentValue

        holder.binding.cardSettings.setOnClickListener(){
            callBackSettings.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return itemsDataList.size
    }
}