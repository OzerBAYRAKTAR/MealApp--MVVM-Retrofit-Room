package com.example.themealapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themealapp.data.api.ApiService
import com.example.themealapp.data.model.Meal
import com.example.themealapp.data.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(): ViewModel() {

    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        ApiService.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailLiveData.value = response.body()!!.meals[0]
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }


        })
    }

    fun observerMealDetailLiveData(): LiveData<Meal> {
        return mealDetailLiveData
    }


}