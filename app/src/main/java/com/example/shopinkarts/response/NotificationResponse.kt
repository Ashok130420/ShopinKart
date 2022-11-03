package com.example.shopinkarts.response

data class NotificationResponse(
    val message: String,
    val notifications: List<Notification>,
    val status: Boolean
)