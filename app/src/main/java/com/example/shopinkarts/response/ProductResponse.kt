package com.example.shopinkarts.response

data class ProductResponse(
    val message: String,
    val product: Product,
    val similarProducts: List<NewlyAdded>,
    val status: Boolean
)