package com.example.themealapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.themealapp.data.api.ApiService
import com.example.themealapp.data.model.CategoryPopularList
import com.example.themealapp.data.model.CategoryPopularMeal
import com.example.themealapp.data.model.Meal
import com.example.themealapp.data.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragmentViewModel(): ViewModel(){
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryPopularMeal>>()


    fun getRandomMeal() {
        ApiService.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

                Log.d("Main Fragment",t.message.toString())
            }

        })
    }

    //we created private randomMealLiveData at the top. we dont want to any of other clasess to change it
    //and we created new fun to use it from other classes
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }
    fun observePopularItemsLiveData(): LiveData<List<CategoryPopularMeal>> {
        return popularItemsLiveData
    }

    fun getPopularItems() {
        ApiService.api.getPopularıtems("SeaFood").enqueue(object : Callback<CategoryPopularList> {
            override fun onResponse(
                call: Call<CategoryPopularList>,
                response: Response<CategoryPopularList>
            ) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryPopularList>, t: Throwable) {
                Log.d("Main Fragment",t.message.toString())
            }

        })
    }





}