package com.example.heptotech.bean_dataclass

data class User(
    val userImageResId: Int,    // Resource ID for the image
    val username: String,       // Name of the user
    val relationStatus: String, // Relation status (e.g., Brother)
    val cardType: String,
    val userImage: Int// Type of card (e.g., RFID Card)
)

