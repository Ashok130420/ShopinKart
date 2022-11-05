package com.app.shopinkarts.response

data class ProductOrder(
    var productId: String,
    val productImage: String,
    val productName: String,
    val qty: Int,
    val totalAmount: Double,
    val variantId: String
)