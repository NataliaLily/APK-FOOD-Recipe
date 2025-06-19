package com.example.foodapp.service

import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.MealDetailResponse
import com.example.foodapp.model.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<MealResponse>

    @GET("search.php")
    fun searchMeals(@Query("s") keyword: String): Call<MealResponse>

    @GET("lookup.php")
    fun getMealDetail(@Query("i") mealId: String): Call<MealDetailResponse>
}