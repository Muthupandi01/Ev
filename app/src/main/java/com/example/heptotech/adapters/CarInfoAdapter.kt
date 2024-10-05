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
import java.util.Collections

class CarInfoAdapter(
    private var carList: MutableList<CarInfo>
) : RecyclerView.Adapter<CarInfoAdapter.CarInfoViewHolder>() {

    class CarInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modalName: TextView = itemView.findViewById(R.id.modalName)
        val addressTxt: TextView = itemView.findViewById(R.id.AddressTxt)
        val batteryTxt: TextView = itemView.findViewById(R.id.batterytxt)
        val newImageView: ImageView = itemView.findViewById(R.id.newImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.segment_chargesingleimgcard, parent, false)
        return CarInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarInfoViewHolder, position: Int) {
        val carInfo = carList[position]

        holder.modalName.text = carInfo.modelName
        holder.addressTxt.text = carInfo.address
        holder.batteryTxt.text = carInfo.batteryPercentage
        holder.newImageView.setImageResource(carInfo.imageResource)


    }


    override fun getItemCount(): Int {
        return carList.size
    }


}
