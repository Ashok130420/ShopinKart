package com.example.shopinkarts.model

data class CreateProduct(
    val productId: String,
    val productImage: String,
    val productName: String,
    val qty: Int,
    val totalAmount: Double,
    val variantId: String
)