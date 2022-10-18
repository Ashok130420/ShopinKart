package com.example.shopinkarts.model

data class VariantsSortingModel(
    var pId: String,
    var vId: String,
    var itemName: String,
    var discountedPrice: String,
    var actualPrice: Int,
    var totalAmount: Int,
    var imageUrl: String,
    var arrayList: ArrayList<Variant>
)