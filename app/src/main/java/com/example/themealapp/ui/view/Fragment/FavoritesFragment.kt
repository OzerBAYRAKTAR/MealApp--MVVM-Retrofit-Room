package com.example.themealapp.ui.view.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themealapp.R
import com.example.themealapp.databinding.FragmentFavoritesBinding
import com.example.themealapp.ui.adapter.FavoritesMealsAdapter
import com.example.themealapp.ui.view.Activity.MainActivity
import com.example.themealapp.ui.viewmodel.MainFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewModel : MainFragmentViewModel
    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavorites()
        prepareRecyclerview()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesMealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG)
                    .setAction("Undo",View.OnClickListener {
                        viewModel.insertMeal(favoritesMealsAdapter.differ.currentList[position])
                    }).show()
              }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

    }

    private fun prepareRecyclerview() {
        favoritesMealsAdapter = FavoritesMealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesMealsAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealsLiveData().observe(viewLifecycleOwner, Observer { meals ->
           favoritesMealsAdapter.differ.submitList(meals)
        })
    }
}