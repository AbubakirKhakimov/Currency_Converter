package com.example.currencyconverter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyconverter.*
import com.example.currencyconverter.adapters.HomeFavoritesAdapter
import com.example.currencyconverter.datas.CurrencyData
import com.example.currencyconverter.datas.DataLists
import com.example.currencyconverter.activities.MainActivity
import com.example.currencyconverter.api.Api
import com.example.currencyconverter.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!DataLists.runProgram) {
            giveIcons()
            context?.let { DataLists.favoritesFileRead(it) }
            context?.let { DataLists.convFavoritesFileRead(it) }
            DataLists.runProgram=true
        }

        connecting()

        binding.swipeLayoutHomeFrag.setOnRefreshListener {
            binding.notConnectionLayout.visibility = View.GONE
            connecting()
        }

        binding.returnConnection.setOnClickListener() {
            binding.notConnectionLayout.visibility = View.GONE
            connecting()
        }

        binding.changeFavorites.setOnClickListener(){
          val mainActivityView = activity as MainActivity
            mainActivityView.replaceFragment(FavoritesSelectionFragment("home"))
        }
    }

    fun successful(){
        binding.updateDate.text = DataLists.currancyDataList[0].Date
        binding.notConnectionLayout.visibility = View.GONE
        binding.recyclerCurrencyFavotitesData.adapter = HomeFavoritesAdapter()
        binding.swipeLayoutHomeFrag.isRefreshing = false
    }

    fun failure(){
        DataLists.currancyDataList.clear()
        binding.updateDate.text = ""
        binding.notConnectionLayout.visibility = View.VISIBLE
        binding.recyclerCurrencyFavotitesData.adapter = HomeFavoritesAdapter()
        binding.swipeLayoutHomeFrag.isRefreshing = false
    }

    fun connecting(){
        binding.swipeLayoutHomeFrag.isRefreshing = true

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

    fun giveIcons() {
        DataLists.itemsIcon.add(R.drawable.usa)
        DataLists.itemsIcon.add(R.drawable.european_union)
        DataLists.itemsIcon.add(R.drawable.ru)
        DataLists.itemsIcon.add(R.drawable.gb)
        DataLists.itemsIcon.add(R.drawable.jp)
        DataLists.itemsIcon.add(R.drawable.az)
        DataLists.itemsIcon.add(R.drawable.bd)
        DataLists.itemsIcon.add(R.drawable.bg)
        DataLists.itemsIcon.add(R.drawable.bh)
        DataLists.itemsIcon.add(R.drawable.bn)
        DataLists.itemsIcon.add(R.drawable.br)
        DataLists.itemsIcon.add(R.drawable.by)
        DataLists.itemsIcon.add(R.drawable.ca)
        DataLists.itemsIcon.add(R.drawable.ch)
        DataLists.itemsIcon.add(R.drawable.cn)
        DataLists.itemsIcon.add(R.drawable.cu)
        DataLists.itemsIcon.add(R.drawable.cz)
        DataLists.itemsIcon.add(R.drawable.dk)
        DataLists.itemsIcon.add(R.drawable.dz)
        DataLists.itemsIcon.add(R.drawable.eg)
        DataLists.itemsIcon.add(R.drawable.af)
        DataLists.itemsIcon.add(R.drawable.ar)
        DataLists.itemsIcon.add(R.drawable.ge)
        DataLists.itemsIcon.add(R.drawable.hk)
        DataLists.itemsIcon.add(R.drawable.hu)
        DataLists.itemsIcon.add(R.drawable.id)
        DataLists.itemsIcon.add(R.drawable.il)
        DataLists.itemsIcon.add(R.drawable.inr)
        DataLists.itemsIcon.add(R.drawable.iq)
        DataLists.itemsIcon.add(R.drawable.ir)
        DataLists.itemsIcon.add(R.drawable.isk)
        DataLists.itemsIcon.add(R.drawable.jo)
        DataLists.itemsIcon.add(R.drawable.au)
        DataLists.itemsIcon.add(R.drawable.kg)
        DataLists.itemsIcon.add(R.drawable.kh)
        DataLists.itemsIcon.add(R.drawable.kr)
        DataLists.itemsIcon.add(R.drawable.kw)
        DataLists.itemsIcon.add(R.drawable.kz)
        DataLists.itemsIcon.add(R.drawable.la)
        DataLists.itemsIcon.add(R.drawable.lb)
        DataLists.itemsIcon.add(R.drawable.ly)
        DataLists.itemsIcon.add(R.drawable.ma)
        DataLists.itemsIcon.add(R.drawable.md)
        DataLists.itemsIcon.add(R.drawable.mm)
        DataLists.itemsIcon.add(R.drawable.mn)
        DataLists.itemsIcon.add(R.drawable.mx)
        DataLists.itemsIcon.add(R.drawable.my)
        DataLists.itemsIcon.add(R.drawable.no)
        DataLists.itemsIcon.add(R.drawable.nz)
        DataLists.itemsIcon.add(R.drawable.om)
        DataLists.itemsIcon.add(R.drawable.ph)
        DataLists.itemsIcon.add(R.drawable.pk)
        DataLists.itemsIcon.add(R.drawable.pl)
        DataLists.itemsIcon.add(R.drawable.qa)
        DataLists.itemsIcon.add(R.drawable.ro)
        DataLists.itemsIcon.add(R.drawable.rs)
        DataLists.itemsIcon.add(R.drawable.am)
        DataLists.itemsIcon.add(R.drawable.sa)
        DataLists.itemsIcon.add(R.drawable.sd)
        DataLists.itemsIcon.add(R.drawable.se)
        DataLists.itemsIcon.add(R.drawable.sg)
        DataLists.itemsIcon.add(R.drawable.sy)
        DataLists.itemsIcon.add(R.drawable.th)
        DataLists.itemsIcon.add(R.drawable.tj)
        DataLists.itemsIcon.add(R.drawable.tm)
        DataLists.itemsIcon.add(R.drawable.tn)
        DataLists.itemsIcon.add(R.drawable.tr)
        DataLists.itemsIcon.add(R.drawable.ua)
        DataLists.itemsIcon.add(R.drawable.ae)
        DataLists.itemsIcon.add(R.drawable.uy)
        DataLists.itemsIcon.add(R.drawable.ve)
        DataLists.itemsIcon.add(R.drawable.vn)
        DataLists.itemsIcon.add(R.drawable.ye)
        DataLists.itemsIcon.add(R.drawable.za)
    }
}