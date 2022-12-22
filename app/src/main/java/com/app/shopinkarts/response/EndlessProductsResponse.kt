package com.app.shopinkarts.response

data class EndlessProductsResponse(
    val message: String,
    val products: List<Product>,
    val status: Boolean
)