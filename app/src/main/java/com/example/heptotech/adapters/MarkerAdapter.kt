package com.example.heptotech.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.actvity_view.ActivityRestaurant
import com.example.heptotech.bean_dataclass.MarkerInfo

class MarkerAdapter (
    private val items: List<MarkerInfo>
) : RecyclerView.Adapter<MarkerAdapter.ViewHolder>() {
    private var selectedMarkerIndex: Int = -1

    // ViewHolder class to hold item views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val akibuaTextView: TextView = itemView.findViewById(R.id.Akibua_text)
        val distanceTextView: TextView = itemView.findViewById(R.id.busstand_text)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val availabilityTextView: TextView = itemView.findViewById(R.id.two_text)
        val type1TextView: TextView = itemView.findViewById(R.id.type_Ac)
        val type2TextView: TextView = itemView.findViewById(R.id.type_Ac1)
        val type3TextView: TextView = itemView.findViewById(R.id.type_Ac2)
        val meter: TextView = itemView.findViewById(R.id.twohundres_text)
    }

    // Inflate the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.marker_info_item, parent, false)
        return ViewHolder(view)
    }

    // Bind the data to the views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Set data to the views
        holder.akibuaTextView.text = item.title
        holder.distanceTextView.text = item.distance
        holder.ratingBar.rating = item.rating
        holder.availabilityTextView.text = item.availability
        holder.type1TextView.text = item.type1
        holder.type2TextView.text = item.type2
        holder.type3TextView.text = item.type3
        holder.meter.text=item.meter
        if (position == selectedMarkerIndex) {
            holder.itemView.visibility = View.VISIBLE // Show the selected marker's details
        }
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ActivityRestaurant::class.java)


            // You can pass data to the new activity if needed
            intent.putExtra("title", item.title)
            intent.putExtra("distance", item.distance)

            context.startActivity(intent)

        }
    }

    // Return the number of items
    override fun getItemCount(): Int {
        return items.size
    }
    fun setSelectedMarker(index: Int) {
        selectedMarkerIndex = index
        notifyDataSetChanged() // Notify the adapter to refresh the views
    }
}