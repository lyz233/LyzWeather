package com.example.lyzweather.logic.network

import PlaceResponse
import com.example.lyzweather.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    //找不到API啊，有机会看看
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") query:String): Call<PlaceResponse>
}