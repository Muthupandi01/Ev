package com.example.heptotech.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.customclass.GradientView

class TimeAdapter(
    private val timeList: List<String>,
    private val onTimeClick: (String) -> Unit
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    // List to keep track of selected positions
    private val selectedPositions = mutableListOf<Int>()

    // ViewHolder class to represent each item
    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.time)
        val gradientView: GradientView = itemView.findViewById(R.id.anim2)
        val whitelnr: LinearLayout = itemView.findViewById(R.id.whitelnr)
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
           // Toast.makeText(holder.itemView.context, isSelected.toString()+"--"+position, Toast.LENGTH_SHORT).show()
            // For the 0th position, set the background to green
            if (position == 0) {
                holder.gradientView.setBackgroundResource(R.color.whiteTextColor) // Background for remaining positions
                holder.itemView.setBackgroundResource(R.drawable.rectangle__1_ev) // Background for remaining positions
                holder.timeTextView.setTextColor(Color.BLACK) // Text color for rem
            }else {
                holder.whitelnr.setBackgroundResource(R.drawable.ev) // Default background for unselected 0th position
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
                    holder.whitelnr.setBackgroundResource(R.drawable.edgecurved_bgev) // Default background for unselected 0th position

                    // holder.gradientView.setBackgroundResource(R.drawable.rectangle__2_ev)
                    holder.timeTextView.setTextColor(Color.BLACK) // Text color for 1st, 2nd, and 3rd positions
                    holder.gradientView.animateGradient(3000L) // Set animation duration in milliseconds
                }
                else -> {
                    holder.itemView.setBackgroundResource(R.drawable.rectangle__1_ev) // Background for remaining positions
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
