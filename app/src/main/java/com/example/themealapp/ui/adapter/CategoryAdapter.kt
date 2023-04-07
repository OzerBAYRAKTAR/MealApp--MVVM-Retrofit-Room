package com.example.themealapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.data.model.Category
import com.example.themealapp.databinding.CategoriRowBinding



class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryCardholder>() {
    private var categoryList = ArrayList<Category>()
    var onItemClicked :((Category) -> Unit)? = null

    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList  = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryCardholder(val binding: CategoriRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCardholder {
        return CategoryCardholder(CategoriRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: CategoryCardholder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imageCategoryRow)
        holder.binding.textCategoryRow.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClicked!!.invoke(categoryList[position])
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}