package com.example.themealapp.ui.view.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.themealapp.R
import com.example.themealapp.data.model.CategoryPopularMeal
import com.example.themealapp.data.model.Meal
import com.example.themealapp.databinding.FragmentMainBinding
import com.example.themealapp.ui.adapter.PopularAdapter
import com.example.themealapp.ui.view.Activity.MealActivity
import com.example.themealapp.ui.viewmodel.MainFragmentViewModel


class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var randomMeal : Meal
    private lateinit var popularAdapter: PopularAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container,false)

        popularAdapter = PopularAdapter()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(requireActivity()).get(MainFragmentViewModel::class.java)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealclicked()

        viewModel.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemClicked()

    }

    private fun onPopularItemClicked() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularAdapter

        }
    }

    private fun observerPopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner){ popularList ->
            popularAdapter.setMeals(mealsList = popularList as ArrayList<CategoryPopularMeal>)

        }
    }

    private fun onRandomMealclicked() {
        binding.todayCardView.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner){ meal ->
            Glide.with(this@MainFragment)
                .load(meal.strMealThumb)
                .into(binding.todayFoodImage)

            this.randomMeal =  meal
        }
    }

    companion object{
        const val MEAL_ID = "com.example.themealapp.ui.view.Fragment.idMeal"
        const val MEAL_NAME = "com.example.themealapp.ui.view.Fragment.nameMeal"
        const val MEAL_THUMB = "com.example.themealapp.ui.view.Fragment.thumbMeal"
    }


}