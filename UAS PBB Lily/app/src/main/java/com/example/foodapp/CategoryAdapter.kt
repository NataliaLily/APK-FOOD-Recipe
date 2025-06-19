package com.example.foodapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCategoryBinding
import com.example.foodapp.model.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var categories = emptyList<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClickListener: ((Category) -> Unit)? = null
    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    inner class ViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategoryName.text = category.strCategory
            Glide.with(binding.root.context)
                .load(category.strCategoryThumb)
                .centerCrop()
                .into(binding.ivCategoryImage)

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(category)
            }
        }
    }
}