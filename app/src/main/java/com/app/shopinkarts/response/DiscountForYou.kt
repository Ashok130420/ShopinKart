package com.app.shopinkarts.response

data class DiscountForYou(
    val _id: String,
    val attributes: List<Attribute>,
    val avgRating: Double,
    val categoryId: String,
    val creationTimeStamp: String,
    val deliveryInstructions: List<String>,
    val description: String,
    val discount: Int,
    val discountType: Int,
    val dispatchDetails: List<String>,
    val isFreeDelivery: Int,
    val price: Int,
    val productCheckList: ProductCheckList,
    val productImages: List<String>,
    val productName: String,
    val reviews: List<Any>,
    val stock: Int,
    val subCategoryId: String,
    val tax: Int,
    val taxType: Int,
    val variantsArr: List<VariantsArr>
)