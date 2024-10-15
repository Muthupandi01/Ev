package com.example.heptotech.bean_dataclass

data class RestaurantData(
    val imageResId: Int,       // Resource ID for the image
    val restaurantName: String, // Restaurant name
    val harlText: String,      // Harl Text (e.g., "Harrodes")
    val rating: Float,         // Rating value for the RatingBar
    val number: String,        // Some number (e.g., "38731")
    val openHours: String,     // Open hours (e.g., "Open Hours: 10:00 - 17:00")
    val mobileNumber: String,  // Mobile number (e.g., "123456789")
    val description: String
)
