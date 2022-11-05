package com.app.shopinkarts.response

data class CategoriesResponse(
    val categories: List<Category>,
    val message: String,
    val status: Boolean
)