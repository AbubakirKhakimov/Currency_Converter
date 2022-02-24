package com.example.currencyconverter.fragments

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.currencyconverter.adapters.ConverterAdapter
import com.example.currencyconverter.adapters.ConverterAdapterCallBack
import com.example.currencyconverter.datas.DataLists
import com.example.currencyconverter.activities.MainActivity
import com.example.currencyconverter.databinding.FragmentConverterBinding
import java.text.DecimalFormat

class ConverterFragment : Fragment(), ConverterAdapterCallBack {

    lateinit var binding: FragmentConverterBinding
    lateinit var adapter: ConverterAdapter
    var intefaceColor = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getThemeColor()
        binding = FragmentConverterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (DataLists.currancyDataList.size==0){
            binding.cardViewUzb.visibility = View.GONE
            binding.notConnectionLayout.visibility = View.VISIBLE
        }else{
            binding.cardViewUzb.visibility = View.VISIBLE
            binding.notConnectionLayout.visibility = View.GONE

            adapter = ConverterAdapter(this)
            binding.converterRecyclerView.adapter = adapter
        }

        binding.cardViewUzb.setOnClickListener{
            binding.numberEnterUzb.isFocusableInTouchMode = true
            if (!binding.numberEnterUzb.isFocused || adapter.selectedItemPosition!=-1){
                adapter.selectedItemPosition=-1
                DataLists.itemsValueList.clear()
                binding.numberEnterUzb.text.clear()

                adapter.notifyDataSetChanged()
            }

            binding.numberEnterUzb.setHintTextColor(Color.parseColor("#4CAF50"))
            binding.numberEnterUzb.setTextColor(Color.parseColor("#4CAF50"))
            binding.numberEnterUzb.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.numberEnterUzb, InputMethodManager.SHOW_IMPLICIT)
            binding.numberEnterUzb.isFocusableInTouchMode = false
        }

        binding.numberEnterUzb.setOnClickListener{
            binding.numberEnterUzb.isFocusableInTouchMode = true
            if (!binding.numberEnterUzb.isFocused || adapter.selectedItemPosition!=-1){
                adapter.selectedItemPosition=-1
                DataLists.itemsValueList.clear()
                binding.numberEnterUzb.text.clear()

                adapter.notifyDataSetChanged()
            }

            if (binding.numberEnterUzb.isFocused){
                binding.numberEnterUzb.setSelection(binding.numberEnterUzb.text.length)
            }

            binding.numberEnterUzb.setHintTextColor(Color.parseColor("#4CAF50"))
            binding.numberEnterUzb.setTextColor(Color.parseColor("#4CAF50"))
            binding.numberEnterUzb.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.numberEnterUzb, InputMethodManager.SHOW_IMPLICIT)
            binding.numberEnterUzb.isFocusableInTouchMode = false
        }

        binding.numberEnterUzb.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if (binding.numberEnterUzb.isFocused && adapter.selectedItemPosition==-1) {
                    when (s.toString()){
                        "0" -> {
                            binding.numberEnterUzb.text.clear()
                        }
                        "." -> {
                            binding.numberEnterUzb.text.clear()
                            binding.numberEnterUzb.text = Editable.Factory.getInstance().newEditable("0.")
                            binding.numberEnterUzb.setSelection(binding.numberEnterUzb.text.length)
                        }
                        else -> {
                            DataLists.itemsValueList.clear()
                            if (s.toString() != "") {
                                for (i in DataLists.convFavoritesSelectedList.indices) {
                                    if (DataLists.convFavoritesSelectedList[i]) {
                                        val valueOtherCurrency = s.toString()
                                            .toDouble() / DataLists.currancyDataList[i].Rate.toDouble()
                                        DataLists.itemsValueList.add(
                                            DecimalFormat("#,##0.00").format(valueOtherCurrency))
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
                }else if (binding.numberEnterUzb.isFocused && adapter.selectedItemPosition!=-1){
                    binding.numberEnterUzb.clearFocus()
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.changeFavorites.setOnClickListener(){
            DataLists.itemsValueList.clear()
            val mainActivityView = activity as MainActivity
            mainActivityView.replaceFragment(FavoritesSelectionFragment("converter"))
        }

    }

    override fun itemOnClick() {
        binding.numberEnterUzb.clearFocus()
        binding.numberEnterUzb.text.clear()
        binding.numberEnterUzb.setHintTextColor(Color.parseColor(intefaceColor))
        binding.numberEnterUzb.setTextColor(Color.parseColor(intefaceColor))
    }

    override fun itemValue(value: String) {
        if (value == ""){
            binding.numberEnterUzb.text.clear()
        }else{
            binding.numberEnterUzb.text = Editable.Factory.getInstance().newEditable(value)
        }
    }

    fun getThemeColor() {
        val currentNightMode: Int = this.resources
            .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        intefaceColor = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> "#FF000000"
            Configuration.UI_MODE_NIGHT_YES -> "#FFFFFFFF"
            else -> "#FF000000"
        }
    }
}