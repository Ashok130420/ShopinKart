package com.app.shopinkarts.response

data class User(
    val _id: String,
    val creationTimeStamp: String,
    val deviceId: String,
    val email: String,
    val password: String,
    val phone: String,
    val profilePicture: String,
    val userType: Int
)