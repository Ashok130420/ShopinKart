package com.app.shopinkarts.model

import com.app.shopinkarts.response.Variants


data class CreateProduct(
    val productId: String,
    val pId: String,
    val productImage: String,
    val productName: String,
    val qty: Int,
    val totalAmount: Double,
    val variants: List<Variants>,
//    val variantsArr: List<VariantsArr>

)