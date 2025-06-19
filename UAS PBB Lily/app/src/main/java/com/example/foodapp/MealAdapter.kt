package com.example.foodapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.model.Meal
import com.example.foodapp.databinding.ItemMealBinding

class MealAdapter : RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    var meals = emptyList<Meal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClickListener: ((Meal) -> Unit)? = null
    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    inner class ViewHolder(private val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvMealName.text = meal.strMeal
            Glide.with(binding.root.context)
                .load(meal.strMealThumb)
                .centerCrop()
                .into(binding.ivMealImage)

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(meal)
            }
        }
    }
}