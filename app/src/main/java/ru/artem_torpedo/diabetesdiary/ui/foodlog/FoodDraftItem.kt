package ru.artem_torpedo.diabetesdiary.ui.foodlog

data class FoodDraftItem(
    val productId: Long,
    val productName: String,

    val caloriesPer100g: Float,
    val proteinPer100g: Float,
    val fatPer100g: Float,
    val carbsPer100g: Float,

    var grams: Float,
    var comment: String?
)