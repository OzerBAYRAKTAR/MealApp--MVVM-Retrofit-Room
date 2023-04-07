package com.example.themealapp.ui.view.Fragment.BottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.themealapp.R
import com.example.themealapp.databinding.FragmentMealBottomSheetBinding
import com.example.themealapp.ui.view.Activity.MainActivity
import com.example.themealapp.ui.view.Activity.MealActivity
import com.example.themealapp.ui.view.Fragment.MainFragment
import com.example.themealapp.ui.viewmodel.MainFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: MainFragmentViewModel

    private var mealId: String? = null
    private var mealName: String? = null
    private var mealThumb: String? = null
    //private var mealName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()
        onBottomSheetDialogClicked()
    }

    private fun onBottomSheetDialogClicked() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(MainFragment.MEAL_ID,mealId)
                    putExtra(MainFragment.MEAL_NAME,mealName)
                    putExtra(MainFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)

            }
        }
    }

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgCategory)
            binding.tvMealCountry.text = meal.strArea
            binding.tvMealCategory.text = meal.strCategory
            binding.tvMealNameInBtmsheet.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID,param1)
                }
            }
    }
}