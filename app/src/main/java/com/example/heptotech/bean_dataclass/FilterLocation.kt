package com.example.heptotech.bean_dataclass
import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable

data class FilterLocation(
    val imageResId: Int = 0,
    val name: String,
    val isHeader: Boolean = false,
    var isSelected: Boolean = false
) : Serializable



