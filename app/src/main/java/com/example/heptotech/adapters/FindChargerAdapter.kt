package com.example.heptotech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.FindChargerInfo

class FindChargerAdapter(
    private val dataList: List<FindChargerInfo >,private val onItemClick: () -> Unit
) : RecyclerView.Adapter<FindChargerAdapter.FindChargerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindChargerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_findevcharge_design, parent, false)
        return FindChargerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FindChargerViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = dataList.size

    inner class FindChargerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val akibuaTextView: TextView = itemView.findViewById(R.id.Akibua_text)
        val distanceTextView: TextView = itemView.findViewById(R.id.busstand_text)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val availabilityTextView: TextView = itemView.findViewById(R.id.two_text)
        val type1TextView: TextView = itemView.findViewById(R.id.type_Ac)
        val type2TextView: TextView = itemView.findViewById(R.id.type_Ac1)
        val type3TextView: TextView = itemView.findViewById(R.id.type_Ac2)
        val meter: TextView = itemView.findViewById(R.id.twohundres_text)
        val evImage: ImageView = itemView.findViewById(R.id.ev)
        init {
            itemView.setOnClickListener {
                onItemClick() // Pass the clicked item
            }
        }

        fun bind(item: FindChargerInfo) {
            akibuaTextView.text = item.title
            distanceTextView.text = item.distance
            ratingBar.rating = item.rating
            availabilityTextView.text = item.availability
            type1TextView.text = item.type1
            type2TextView.text = item.type2
            type3TextView.text = item.type3
            meter.text = item.meter
            evImage.setImageResource(item.evImg)


        }
    }
}
