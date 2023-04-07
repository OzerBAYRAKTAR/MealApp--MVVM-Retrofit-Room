package com.example.themealapp.ui.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themealapp.R
import com.example.themealapp.data.model.Category
import com.example.themealapp.databinding.ActivityCategoryMealsBinding
import com.example.themealapp.ui.adapter.CategoryMealsAdapter
import com.example.themealapp.ui.view.Fragment.MainFragment
import com.example.themealapp.ui.viewmodel.CategoryMealsViewModel
import com.example.themealapp.ui.viewmodel.MealViewModel

class CategoryMealsActivity : AppCompatActivity() {
     lateinit var binding: ActivityCategoryMealsBinding
     lateinit var viewModel: CategoryMealsViewModel
     lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this).get(CategoryMealsViewModel::class.java)
        viewModel.getMealsByCategory(intent.getStringExtra(MainFragment.CATEGORY_NAME)!!)

        prepareRecyclerView()

        viewModel.observerMealsLiveData().observe(this, Observer { mealList ->
            binding.categoryMealCount.text = "Total Food: ${mealList.size.toString()}"

            categoryMealsAdapter.setCategoryMealsList(mealList)
        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvCategoryMeal.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}