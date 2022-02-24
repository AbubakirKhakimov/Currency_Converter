package com.example.currencyconverter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyconverter.adapters.ActionAdapter
import com.example.currencyconverter.datas.CurrencyData
import com.example.currencyconverter.datas.DataLists
import com.example.currencyconverter.api.Api
import com.example.currencyconverter.databinding.FragmentListCurrencyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListCurrencyFragment: Fragment() {

    lateinit var binding: FragmentListCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListCurrencyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (DataLists.currancyDataList.size == 0){
            binding.notConnectionLayout.visibility=View.VISIBLE
        }else {
            binding.updateDate.text = DataLists.currancyDataList[0].Date
            binding.recyclerCurrencyAllData.adapter = ActionAdapter()
        }

        binding.swipeLayoutListFrag.setOnRefreshListener {
            binding.notConnectionLayout.visibility = View.GONE
            connecting()
        }

        binding.returnConnection.setOnClickListener(){
            binding.notConnectionLayout.visibility = View.GONE
            connecting()
        }
    }

    fun successful(){
        binding.updateDate.text = DataLists.currancyDataList[0].Date
        binding.notConnectionLayout.visibility = View.GONE
        binding.recyclerCurrencyAllData.adapter = ActionAdapter()
        binding.swipeLayoutListFrag.isRefreshing = false
    }

    fun failure(){
        DataLists.currancyDataList.clear()
        binding.updateDate.text = ""
        binding.notConnectionLayout.visibility = View.VISIBLE
        binding.recyclerCurrencyAllData.adapter = ActionAdapter()
        binding.swipeLayoutListFrag.isRefreshing = false
    }

    fun connecting(){
        binding.swipeLayoutListFrag.isRefreshing = true

        val retrofit = Retrofit.Builder()
            .baseUrl("https://cbu.uz/uzc/arkhiv-kursov-valyut/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)

        api.getCurrencyData().enqueue(object: Callback<ArrayList<CurrencyData>> {
            override fun onResponse(
                call: Call<ArrayList<CurrencyData>>,
                response: Response<ArrayList<CurrencyData>>
            ) {
                if (response.isSuccessful){
                    DataLists.currancyDataList = response.body()!!
                    DataLists.currancyDataList.removeAt(72)
                    successful()
                }else{
                    failure()
                }
            }

            override fun onFailure(call: Call<ArrayList<CurrencyData>>, t: Throwable) {
                failure()
            }
        })
    }
}