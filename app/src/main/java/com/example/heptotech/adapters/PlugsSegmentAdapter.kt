package com.example.heptotech.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.CarInfo
import com.example.heptotech.bean_dataclass.PlugsSegmentClass
import java.util.Collections

class PlugsSegmentAdapter(
    private var carList: MutableList<PlugsSegmentClass>
) : RecyclerView.Adapter<PlugsSegmentAdapter.CarInfoViewHolder>() {

    class CarInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modalName: TextView = itemView.findViewById(R.id.modalName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.segmentcharge_availablepluggs, parent, false)
        return CarInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarInfoViewHolder, position: Int) {
        val carInfo = carList[position]

        holder.modalName.text = carInfo.modelName



    }


    override fun getItemCount(): Int {
        return carList.size
    }


}
