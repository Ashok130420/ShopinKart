package com.app.shopinkarts.model

import com.app.shopinkarts.response.Variants
import com.app.shopinkarts.response.VariantsArr


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
    var variantsArr: ArrayList<VariantsArr>,
    var variants: ArrayList<Variants>,
//    var variants: ArrayList<Variant>

)