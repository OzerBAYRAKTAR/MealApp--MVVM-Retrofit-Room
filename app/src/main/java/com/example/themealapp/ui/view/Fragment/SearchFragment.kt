package com.example.themealapp.ui.view.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themealapp.R
import com.example.themealapp.databinding.FragmentSearchBinding
import com.example.themealapp.ui.adapter.FavoritesMealsAdapter
import com.example.themealapp.ui.view.Activity.MainActivity
import com.example.themealapp.ui.viewmodel.MainFragmentViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var searchAdapter: FavoritesMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeSearchedMealsLiveData()

        binding.imgSearchArrow.setOnClickListener { searchMeals() }
        observeSearchedMealsLiveData()

    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { list ->
            searchAdapter.differ.submitList(list)
        })
    }


    private fun prepareRecyclerView() {
        searchAdapter = FavoritesMealsAdapter()
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }

}