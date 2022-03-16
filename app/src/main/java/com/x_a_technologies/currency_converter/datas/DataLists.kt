package com.x_a_technologies.currency_converter.datas

import android.content.Context
import java.io.*
import java.lang.Exception

object DataLists {
    var runProgram = false
    val cashFileName = "SettingsFile"
    //All items data lists
    var currancyDataList = ArrayList<CurrencyData>()
    var itemsIcon = ArrayList<Int>()
    //Favorites data list
    val favoritesSelectedList = ArrayList<Boolean>()
    //Converter data list
    val itemsValueList = ArrayList<String>()
    val convFavoritesSelectedList = ArrayList<Boolean>()

    fun favoritesFileRead(context: Context){
        try {
            val reader = DataInputStream(context.openFileInput("favoritesFile.txt"))

            repeat(74) {
                favoritesSelectedList.add(reader.readBoolean())
            }

            reader.close()
        }catch (e:Exception) {
            repeat(74) {
                if (it in 0..3) {
                    favoritesSelectedList.add(true)
                } else {
                    favoritesSelectedList.add(false)
                }
            }
            favoritesFileWrite(context)
        }
    }

    fun favoritesFileWrite(context: Context){
        val writer = DataOutputStream(context.openFileOutput("favoritesFile.txt", Context.MODE_PRIVATE))

        for (i in favoritesSelectedList){
            writer.writeBoolean(i)
        }

        writer.close()
    }

    fun convFavoritesFileRead(context: Context){
        try {
            val reader = DataInputStream(context.openFileInput("converterFavoritesFile.txt"))

            repeat(74) {
                convFavoritesSelectedList.add(reader.readBoolean())
            }

            reader.close()
        }catch (e:Exception) {
            repeat(74) {
                if (it in 0..3) {
                    convFavoritesSelectedList.add(true)
                } else {
                    convFavoritesSelectedList.add(false)
                }
            }
            convFavoritesFileWrite(context)
        }
    }

    fun convFavoritesFileWrite(context: Context){
        val writer = DataOutputStream(context.openFileOutput("converterFavoritesFile.txt", Context.MODE_PRIVATE))

        for (i in convFavoritesSelectedList){
            writer.writeBoolean(i)
        }

        writer.close()
    }

}