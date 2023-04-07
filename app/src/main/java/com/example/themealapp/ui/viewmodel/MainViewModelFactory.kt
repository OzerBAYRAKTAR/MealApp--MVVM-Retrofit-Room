package com.example.themealapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themealapp.data.RoomDb.MealDataBase


class MainViewModelFactory(
    private val mealDataBase: MealDataBase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(mealDataBase) as T
    }
}