package com.app.shopinkarts.response

data class ParticularItemResponse(
    val message: String,
    val products: List<Product>,
    val status: Boolean
)