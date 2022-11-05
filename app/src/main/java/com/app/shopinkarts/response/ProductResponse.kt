package com.app.shopinkarts.response

data class ProductResponse(
    val message: String,
    val product: Product,
    val similarProducts: List<SimilarProduct>,
    val status: Boolean
)