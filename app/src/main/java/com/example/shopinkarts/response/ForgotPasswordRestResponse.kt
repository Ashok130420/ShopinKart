package com.example.shopinkarts.response

data class ForgotPasswordRestResponse(
    val message: String,
    val status: Boolean,
    val user: User
)