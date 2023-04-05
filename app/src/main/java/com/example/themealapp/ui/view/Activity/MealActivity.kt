package com.example.themealapp.ui.view.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.themealapp.R
import com.example.themealapp.data.model.Meal
import com.example.themealapp.databinding.ActivityMealBinding
import com.example.themealapp.ui.view.Fragment.MainFragment
import com.example.themealapp.ui.viewmodel.MainFragmentViewModel
import com.example.themealapp.ui.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youtubeLink: String
    private lateinit var viewModel : MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel= ViewModelProvider(this).get(MealViewModel::class.java)

        loadingCase()
        getMealInfoFromIntent()
        setInfoInMeals()
        viewModel.getMealDetail(mealId)
        observerMealDetailsLiveData()
        onYoutubeImageClicked()
    }

    private fun onYoutubeImageClicked() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        viewModel.observerMealDetailLiveData().observe(this) { value ->

            onResponseCase()
            val meal = value
            binding.tvCategoryInfo.text = "Category: ${meal.strCategory}"
            binding.tvAreaInfo.text =  "Location: ${meal.strArea}"
            binding.tvContent.text = meal.strInstructions
            youtubeLink = meal.strYoutube
        }
    }

    private fun setInfoInMeals() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MainFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(MainFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MainFragment.MEAL_THUMB)!!
    }
    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonAddToFavorites.visibility = View.INVISIBLE
        binding.tvContent.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }
    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.buttonAddToFavorites.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}