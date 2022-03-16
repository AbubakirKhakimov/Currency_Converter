package com.x_a_technologies.currency_converter.api

import com.x_a_technologies.currency_converter.datas.CurrencyData
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("json")
    fun getCurrencyData():Call<ArrayList<CurrencyData>>
}