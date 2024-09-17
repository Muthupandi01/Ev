package com.example.heptotech.bean_dataclass

import java.io.Serializable

data class Vehicle(
    val make: String,
    val brand: String,
    val model: String,
    val licensePlate: String,
    val year: String,
    val imageUrl: String // URL or resource ID for the image
) : Serializable
