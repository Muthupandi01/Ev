package com.example.heptotech.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.customclass.GradientView

class TimeAdapter(
    private val timeList: List<String>,
    private val onTimeClick: (String) -> Unit
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    // Track the currently selected position (initially no selection)
    private var selectedPosition: Int = -1

    // ViewHolder class to represent each item
    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.time)
        val gradientView: GradientView = itemView.findViewById(R.id.anim2)
        val whitelnr: LinearLayout = itemView.findViewById(R.id.whitelnr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timeonly, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val time = timeList[position]
        holder.timeTextView.text = time

        // Determine if the current item is selected
        val isSelected = selectedPosition == position

        if (isSelected) {
            // Apply styles for the selected item (green background)
            holder.whitelnr.setBackgroundResource(R.drawable.ev)
            holder.itemView.setBackgroundResource(R.drawable.rectangle__3_ev)
            holder.timeTextView.setTextColor(Color.WHITE)
            holder.gradientView.stopAnimation()  // Stop gradient animation for selected item
        } else {
            // Apply styles for unselected items
            if (position in 0..3) {
                // Always start gradient animation for positions 0-3 when unselected
                holder.whitelnr.setBackgroundResource(R.drawable.edgecurved_bgev)
                holder.timeTextView.setTextColor(Color.BLACK)
                holder.gradientView.animateGradient(2000L)  // Start or restart gradient animation
            } else {
                // Stop gradient animation for other positions
                holder.gradientView.stopAnimation()
                holder.itemView.setBackgroundResource(R.drawable.rectangle__1_ev)
                holder.timeTextView.setTextColor(Color.BLACK)
            }
        }

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                // Track the previously selected position
                val previousSelectedPosition = selectedPosition
                selectedPosition = position

                // Refresh the previously selected item to restore gradient if needed
                notifyItemChanged(previousSelectedPosition)
                // Update the newly selected item
                notifyItemChanged(position)

                // Call the click callback
                onTimeClick(time)
            }
        }
    }

    override fun getItemCount(): Int = timeList.size
}
