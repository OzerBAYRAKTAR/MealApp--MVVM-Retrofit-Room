package com.example.themealapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themealapp.data.api.ApiService
import com.example.themealapp.data.model.Meal
import com.example.themealapp.data.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel : ViewModel() {
    private var searchedMealLiveData = MutableLiveData<Meal>()


    fun searchMealDetail(name: String,context: Context?) {
        ApiService.api.searchMeal(name).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()?.meals == null)
                    Toast.makeText(context?.applicationContext, "No such a meal", Toast.LENGTH_SHORT).show()
                else
                    searchedMealLiveData.value = response.body()!!.meals[0]
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    fun observeSearchLiveData(): LiveData<Meal> {
        return searchedMealLiveData
    }
}