package com.example.shopinkarts.model

import com.example.shopinkarts.response.Variants


data class CreateProduct(
    val productId: String,
    val productImage: String,
    val productName: String,
    val qty: Int,
    val totalAmount: Double,
    val variants: List<Variants>,
//    val variantsArr: List<VariantsArr>

)