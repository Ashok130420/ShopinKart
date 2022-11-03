package com.example.shopinkarts.response

data class ForgotPasswordSendOtpResponse(
    val message: String,
    val otp: String,
    val status: Boolean
)