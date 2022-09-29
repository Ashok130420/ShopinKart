package com.example.shopinkarts.model

import com.example.shopinkarts.response.VariantsArr


data class CartModel(
    var pId: String,
    var vId: String,
    var itemName: String,
    var discountedPrice: String,
    var actualPrice: Int,
    var color: String,
    var size: String,
    var quantity: Int,
    var totalAmount: Int,
    var imageUrl: String,
    var stock: Int,
    var variantsArr: ArrayList<VariantsArr>

)