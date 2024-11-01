// VehicleAdapter.kt
package com.example.heptotech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.bean_dataclass.OperatorVechicleData

class OperatorVechicleAdapter(
    private val vehicleItems: List<OperatorVechicleData>
) : RecyclerView.Adapter<OperatorVechicleAdapter.VehicleViewHolder>() {

    inner class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.car_img1)
        val text1: TextView = itemView.findViewById(R.id.tesela1)
        val text2: TextView = itemView.findViewById(R.id.licene1)
        val checkBox: CheckBox = itemView.findViewById(R.id.checbox1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.operators_vechicleitem_recycle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleItems[position]
        holder.imageView.setImageResource(vehicle.imageResId)
        holder.text1.text = vehicle.text1
        holder.text2.text = vehicle.text2
        holder.checkBox.isChecked = vehicle.isChecked

        // Handle checkbox state change
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            vehicle.isChecked = isChecked
        }
    }

    override fun getItemCount(): Int = vehicleItems.size
}
