package com.example.foodapp.model

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?
) {
    fun getIngredientsList(): List<String> {
        val ingredients = mutableListOf<String>()
        val ingredientFields = listOf(
            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
            strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10
        )
        val measureFields = listOf(
            strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
            strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10
        )

        for (i in ingredientFields.indices) {
            val ingredient = ingredientFields[i]
            val measure = measureFields[i]
            if (!ingredient.isNullOrBlank()) {
                val fullIngredient = if (!measure.isNullOrBlank()) {
                    "$measure $ingredient"
                } else {
                    ingredient
                }
                ingredients.add(fullIngredient)
            }
        }
        return ingredients
    }
}

data class MealDetailResponse(
    val meals: List<MealDetail>?
)