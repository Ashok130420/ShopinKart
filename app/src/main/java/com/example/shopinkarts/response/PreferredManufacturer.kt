package com.example.shopinkarts.response

data class PreferredManufacturer(
    val _id: String,
    val brandImage: String,
    val brandName: String,
    val brandUrl: String,
    val categoryId: String,
    val categoryName: String,
    val creationTimeStamp: String,
    val isActive: Int,
    val subCategoryId: String,
    val subCategoryName: String
)