package com.example.heptotech.itemlistener

import com.example.heptotech.bean_dataclass.Vehicle

interface ItemListenervehicle {
    fun onItemDeleted(position: Int)
    fun onItemClicked(position: Int, vehicle: Vehicle)
}
