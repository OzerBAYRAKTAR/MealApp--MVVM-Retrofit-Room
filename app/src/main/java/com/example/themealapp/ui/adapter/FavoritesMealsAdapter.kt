package com.example.themealapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.data.model.Meal
import com.example.themealapp.databinding.CategoriRowBinding
import com.example.themealapp.databinding.FavoritesRowBinding
import com.example.themealapp.databinding.FragmentFavoritesBinding

class FavoritesMealsAdapter: RecyclerView.Adapter<FavoritesMealsAdapter.FavoritesCardHolder>() {

    inner class FavoritesCardHolder(val binding: FavoritesRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesCardHolder {
        return FavoritesCardHolder(
            FavoritesRowBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesCardHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imageCategoryRow)
        holder.binding.textCategoryRow.text = meal.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}