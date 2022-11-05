package com.app.shopinkarts.response

data class SubCategoriesResponse(
    val message: String,
    val status: Boolean,
    val subCategories: List<SubCategory>
)