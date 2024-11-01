package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.RestaurantData
import com.example.heptotech.customclass.CircleRatingBar

class RestaurantAdapter(private val items: List<RestaurantData>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    // Define the ViewHolder class inside the adapter
    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img)
        val restaurantNameTextView: TextView = view.findViewById(R.id.restaurent_text)
        val harlTextView: TextView = view.findViewById(R.id.harl_text)
        val numberTextView: TextView = view.findViewById(R.id.numbers_one)
        val openHoursTextView: TextView = view.findViewById(R.id.ten)
        val mobileNumberTextView: TextView = view.findViewById(R.id.mobile_text)
        val descriptionTextView: TextView = view.findViewById(R.id.description_text)
        val circleRatingBar: CircleRatingBar = view.findViewById(R.id.ratingBar)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        // Inflate the custom layout for each item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restarunt_recycle_, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        // Get the data model for the current position
        val item = items[position]

        // Bind the data to the ViewHolder views
        holder.imageView.setImageResource(item.imageResId)
        holder.restaurantNameTextView.text = item.restaurantName
        holder.harlTextView.text = item.harlText
        holder.numberTextView.text = item.number
        holder.openHoursTextView.text = item.openHours
        holder.mobileNumberTextView.text = item.mobileNumber
        holder.descriptionTextView.text = item.description
        holder.circleRatingBar.setRating(item.rating) // Set rating between 0 and 5
    }

    override fun getItemCount(): Int {
        // Return the number of items in the list
        return items.size
    }
}
