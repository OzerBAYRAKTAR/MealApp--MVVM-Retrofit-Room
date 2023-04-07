package com.example.themealapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.themealapp.data.RoomDb.MealDataBase
import com.example.themealapp.data.api.ApiService
import com.example.themealapp.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragmentViewModel(
    private val mealDataBase: MealDataBase
): ViewModel(){
    private var randomMealLiveData = MutableLiveData<Meal>()

    private var popularItemsLiveData = MutableLiveData<List<CategoryPopularMeal>>()

    private var categoryItemsLiveData = MutableLiveData<List<Category>>()

    private var favoritesMealLiveData = mealDataBase.mealDao().getAllMeals()

    private var bottomSheetLiveData = MutableLiveData<Meal>()

    var searchMealsLiveData = MutableLiveData<List<Meal>>()




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
    fun getAllCategories() {
        ApiService.api.getallCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                   //alttakiyle aynı sonuç==-> categoryItemsLiveData.value = response.body()!!.categories
                    categoryItemsLiveData.postValue(categoryList.categories)
                }
            }
            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Main Fragment",t.message.toString())
            }
        })
    }

    fun searchMeals(query: String) {
        ApiService.api.searchMeal(query).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealsLiveData.postValue(it)
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
    fun observeCategoryItemsLiveData(): LiveData<List<Category>> {
        return  categoryItemsLiveData
    }
    //for favoritesFragment
    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> {
        return favoritesMealLiveData
    }
    fun observeSearchMealLiveData(): LiveData<List<Meal>> = searchMealsLiveData
    fun observeBottomSheetMealLiveData(): LiveData<Meal> = bottomSheetLiveData

    //delete meal from favoritesFragment
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    //insert meal to FavoritesFragment
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }

    fun getMealById(id: String) {
        ApiService.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    bottomSheetLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("MainFragmentviewModel", t.message.toString())
            }

        })
    }

}