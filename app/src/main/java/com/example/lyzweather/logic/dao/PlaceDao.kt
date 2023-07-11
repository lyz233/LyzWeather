package com.example.lyzweather.logic.dao

import Place
import android.content.Context
import androidx.core.content.edit
import com.example.lyzweather.SunnyWeatherApplication
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place: Place){
        sharePreferences().edit {
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavePlace():Place{
        val placeJson = sharePreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharePreferences().contains("place")

    private fun sharePreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}