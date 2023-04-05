package com.example.themealapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.data.model.CategoryPopularMeal
import com.example.themealapp.databinding.PopularRowBinding

class PopularAdapter() : RecyclerView.Adapter<PopularAdapter.PopularCardholder>() {
    private var mealsList = ArrayList<CategoryPopularMeal>()
    lateinit var onItemClick: ((CategoryPopularMeal) -> Unit)

    fun setMeals(mealsList: ArrayList<CategoryPopularMeal>) {
        this.mealsList  = mealsList
        notifyDataSetChanged()
    }

    class PopularCardholder(val binding: PopularRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCardholder {
       return PopularCardholder(PopularRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: PopularCardholder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imagePopular)
        holder.binding.textPopular.text = mealsList[position].strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}