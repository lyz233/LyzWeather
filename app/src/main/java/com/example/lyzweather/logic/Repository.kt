package com.example.lyzweather.logic

import Place
import androidx.lifecycle.liveData
import com.example.lyzweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.Exception
import kotlin.RuntimeException

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status =="ok"){
                val place = placeResponse.places
                Result.success(place)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}