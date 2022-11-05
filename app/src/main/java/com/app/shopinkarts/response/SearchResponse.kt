package com.app.shopinkarts.response

data class SearchResponse(
    val message: String,
    val product: List<Product>,
    val status: Boolean
)