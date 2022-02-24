package com.example.currencyconverter.api

import com.example.currencyconverter.datas.CurrencyData
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("json")
    fun getCurrencyData():Call<ArrayList<CurrencyData>>
}