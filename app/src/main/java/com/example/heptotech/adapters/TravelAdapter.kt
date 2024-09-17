package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.TravelItem





class TravelAdapter(private var trips: List<TravelItem>) : RecyclerView.Adapter<TravelAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_card, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]

        if (position == 0) {
            holder.status.text = trip.status
            holder.status.setBackgroundResource(R.drawable.rectangle_34624201_ev)

        } else {
            holder.status.text = "Cancelled"
            holder.status.setBackgroundResource(R.drawable.rectangle_red_background_ev)
        }

        holder.txtDate.text = trip.date
        holder.txtStart.text = trip.startLocation
        holder.txtEnd.text = trip.endLocation
    }

    override fun getItemCount(): Int = trips.size

    fun updateTrips(newTrips: List<TravelItem>) {
        trips = newTrips
        notifyDataSetChanged()
    }

    class TripViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDate: TextView = view.findViewById(R.id.txt_date)
        val status: TextView = view.findViewById(R.id.status)
        val txtStart: TextView = view.findViewById(R.id.txt_start)
        val txtEnd: TextView = view.findViewById(R.id.txt_end)
    }
}

