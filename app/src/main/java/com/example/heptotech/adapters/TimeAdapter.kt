package com.example.heptotech.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R

class TimeAdapter(
    private val timeList: List<String>,
    private val onTimeClick: (String) -> Unit
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    // List to keep track of selected positions
    private val selectedPositions = mutableListOf<Int>()

    // ViewHolder class to represent each item
    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.time)
        val anim2: LinearLayout = itemView.findViewById(R.id.anim2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        // Inflate the item layout (timeonly.xml)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timeonly, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val time = timeList[position]
        holder.timeTextView.text = time

        // Determine if the current position is selected
        val isSelected = selectedPositions.contains(position)

        // Set background and text color based on selection state
        if (isSelected) {
            // For the 0th position, set the background to green
            if (position == 0) {
                holder.itemView.setBackgroundResource(R.drawable.rectangle__1_) // Background for remaining positions
                holder.timeTextView.setTextColor(Color.BLACK) // Text color for rem
            } else {
                // Use different background for other selected positions (if needed)
                holder.itemView.setBackgroundResource(R.drawable.rectangle__3_ev) // Background for other selected positions
                holder.timeTextView.setTextColor(Color.WHITE) // White text color for selected positions
            }
        } else {
            // Set different backgrounds for different ranges
            when (position) {
                0 -> {
                    holder.itemView.setBackgroundResource(R.drawable.rectangle__3_ev) // Default background for unselected 0th position
                    holder.timeTextView.setTextColor(Color.WHITE) // White text color for unselected 0th position
                }
                1, 2, 3 -> {
                    holder.itemView.setBackgroundResource(R.drawable.rectangle__2_ev) // Background for 1st, 2nd, and 3rd positions
                    holder.timeTextView.setTextColor(Color.BLACK) // Text color for 1st, 2nd, and 3rd positions
                }
                else -> {
                    holder.itemView.setBackgroundResource(R.drawable.rectangle__1_) // Background for remaining positions
                    holder.timeTextView.setTextColor(Color.BLACK) // Text color for remaining positions
                }
            }
        }

        // Set a click listener for the time item
        holder.itemView.setOnClickListener {
            if (isSelected) {
                // Deselect the item
                selectedPositions.remove(position)
            } else {
                // Select the item
                selectedPositions.add(position)
            }
            onTimeClick(time) // Pass the selected time back to the callback
            notifyItemChanged(position) // Notify the adapter to update the item view
        }
    }

    override fun getItemCount(): Int {
        return timeList.size // Return the total number of items in the list
    }
}
