package com.app.shopinkarts.response

data class LoginResponse(
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)