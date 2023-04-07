package com.example.themealapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.data.model.CategoryPopularMeal
import com.example.themealapp.databinding.CategorymealsRowBinding


class CategoryMealsAdapter() : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsCardholder>() {
    private var categoryMealsList = ArrayList<CategoryPopularMeal>()
    var onItemClicked :((CategoryPopularMeal) -> Unit)? = null

    fun setCategoryMealsList(categoryMealsList: List<CategoryPopularMeal>) {
        this.categoryMealsList  = categoryMealsList as ArrayList<CategoryPopularMeal>
        notifyDataSetChanged()
    }

    inner class CategoryMealsCardholder(val binding: CategorymealsRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsCardholder {
        return CategoryMealsCardholder(CategorymealsRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: CategoryMealsCardholder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryMealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = categoryMealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClicked!!.invoke(categoryMealsList[position])
        }

    }

    override fun getItemCount(): Int {
        return categoryMealsList.size
    }
}