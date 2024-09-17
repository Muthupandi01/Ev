package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.VehicleItem

// VehicleItemCheckBoxAdapter.kt
class VehicleItemCheckBoxAdapter(
    private val vehicleList: List<VehicleItem>,
    private val onCheckboxChecked: (Boolean) -> Unit
) : RecyclerView.Adapter<VehicleItemCheckBoxAdapter.VehicleViewHolder>() {

    private val checkedItems = mutableSetOf<Int>() // Track checked item positions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehicletype_reccard_checkbox, parent, false)
        return VehicleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val currentItem = vehicleList[position]
        holder.motorTypeImg.setImageResource(currentItem.motorTypeImgRes)
        holder.vehicleType.text = currentItem.vehicleType

        holder.checkBox.isChecked = checkedItems.contains(position)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkedItems.add(position)
            } else {
                checkedItems.remove(position)
            }
            onCheckboxChecked(checkedItems.isNotEmpty())
        }
    }

    override fun getItemCount() = vehicleList.size

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val motorTypeImg: ImageView = itemView.findViewById(R.id.motortypeimg)
        val vehicleType: TextView = itemView.findViewById(R.id.vehicletype)
        val checkBox: CheckBox = itemView.findViewById(R.id.checbox)
    }
}

