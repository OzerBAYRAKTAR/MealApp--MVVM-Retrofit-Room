package com.example.themealapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themealapp.data.api.ApiService
import com.example.themealapp.data.model.CategoryPopularList
import com.example.themealapp.data.model.CategoryPopularMeal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {

    val mealsLiveData = MutableLiveData<List<CategoryPopularMeal>>()

    fun getMealsByCategory(categoryName: String) {
        ApiService.api.getMealsByCategory(categoryName).enqueue(object : Callback<CategoryPopularList> {
            override fun onResponse(
                call: Call<CategoryPopularList>,
                response: Response<CategoryPopularList>
            ) {
                response.body()?.let { mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<CategoryPopularList>, t: Throwable) {
                Log.d("CategoryMealsViewModel",t.message.toString())
            }

        })
    }

    fun observerMealsLiveData(): LiveData<List<CategoryPopularMeal>> {
        return mealsLiveData
    }
}