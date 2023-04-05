package com.example.themealapp.data.api

import com.example.themealapp.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {


    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(
        @Query("i") id : String) : Call<MealList>

    @GET("filter.php")
    fun getPopularıtems(
        @Query("c") categoryName: String): Call<CategoryPopularList>


}