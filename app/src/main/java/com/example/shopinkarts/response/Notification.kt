package com.example.shopinkarts.response

data class Notification(
    val _id: String,
    val message: String,
    val notificationDate: String,
    val title: String,
    val userId: String
)