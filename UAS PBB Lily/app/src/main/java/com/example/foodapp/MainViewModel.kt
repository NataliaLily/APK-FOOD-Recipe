package com.example.foodapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.model.Category
import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.Meal
import com.example.foodapp.model.MealDetail
import com.example.foodapp.model.MealDetailResponse
import com.example.foodapp.model.MealResponse
import com.example.foodapp.service.ApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val categories = MutableLiveData<List<Category>>()
    val meals = MutableLiveData<List<Meal>>()
    val mealDetail = MutableLiveData<MealDetail>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getCategories() {
        isLoading.postValue(true)
        val call = ApiClient.apiService.getCategories()
        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                isLoading.postValue(false)
                if (response.isSuccessful) {
                    categories.postValue(response.body()?.categories ?: emptyList())
                } else {
                    errorMessage.postValue("Failed to load categories")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message ?: "Unknown error")
                Log.e("API_ERROR", "Categories error: ${t.message}")
            }
        })
    }

    fun getMealsByCategory(category: String) {
        isLoading.postValue(true)
        val call = ApiClient.apiService.getMealsByCategory(category)
        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                isLoading.postValue(false)
                if (response.isSuccessful) {
                    meals.postValue(response.body()?.meals ?: emptyList())
                } else {
                    errorMessage.postValue("Failed to load meals")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message ?: "Unknown error")
                Log.e("API_ERROR", "Meals error: ${t.message}")
            }
        })
    }

    fun searchMeals(keyword: String) {
        isLoading.postValue(true)
        val call = ApiClient.apiService.searchMeals(keyword)
        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                isLoading.postValue(false)
                if (response.isSuccessful) {
                    meals.postValue(response.body()?.meals ?: emptyList())
                } else {
                    errorMessage.postValue("No meals found")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message ?: "Unknown error")
                Log.e("API_ERROR", "Search error: ${t.message}")
            }
        })
    }

    fun getMealDetail(mealId: String) {
        isLoading.postValue(true)
        val call = ApiClient.apiService.getMealDetail(mealId)
        call.enqueue(object : Callback<MealDetailResponse> {
            override fun onResponse(
                call: Call<MealDetailResponse>,
                response: Response<MealDetailResponse>
            ) {
                isLoading.postValue(false)
                if (response.isSuccessful) {
                    response.body()?.meals?.firstOrNull()?.let {
                        mealDetail.postValue(it)
                    }
                } else {
                    errorMessage.postValue("Failed to load meal details")
                }
            }

            override fun onFailure(call: Call<MealDetailResponse>, t: Throwable) {
                isLoading.postValue(false)
                errorMessage.postValue(t.message ?: "Unknown error")
                Log.e("API_ERROR", "Detail error: ${t.message}")
            }
        })
    }
}
