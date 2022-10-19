package com.example.shopinkarts.model



data class CreateProduct(
    val productId: String,
    val productImage: String,
    val productName: String,
    val qty: Int,
    val totalAmount: Double,
    val variants: List<Variant>,
//    val variantsArr: List<VariantsArr>

)