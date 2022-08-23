package com.example.shopinkarts.response

data class SignUpResponse(
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)