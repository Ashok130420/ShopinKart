package com.app.shopinkarts.response

data class ManufacturerResponse(
    val message: String,
    val products: List<Product>,
    val status: Boolean
)