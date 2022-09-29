package com.example.shopinkarts.model

import com.example.shopinkarts.response.VariantsArr

data class CreateProduct(
    val productId: String,
    val productImage: String,
    val productName: String,
    val qty: Int,
    val totalAmount: Double,
    val variantId: String,
    val variantsArr: ArrayList<VariantsArr>
)