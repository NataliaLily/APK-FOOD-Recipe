package com.example.foodapp.model

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String? = null
)

data class MealResponse(
    val meals: List<Meal>?
)
