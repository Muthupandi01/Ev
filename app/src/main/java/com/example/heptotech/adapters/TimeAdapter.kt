package com.example.heptotech.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.customclass.GradientView

class TimeAdapter(
    private val timeList: List<String>,
    private val onTimeClick: (String) -> Unit
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    // List to keep track of selected positions
    private val selectedPositions = mutableListOf<Int>()
    private var previousSelectedPosition: Int? = null // Track the last selected position

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
           // Toast.makeText(holder.itemView.context, "Selctedposition - "+position, Toast.LENGTH_SHORT).show()

            if (position == 0) {
                holder.gradientView.setBackgroundResource(R.color.whiteTextColor)
                holder.itemView.setBackgroundResource(R.drawable.rectangle__1_ev)
                holder.timeTextView.setTextColor(Color.BLACK)
                holder.gradientView.stopAnimation()
            } else {
                holder.whitelnr.setBackgroundResource(R.drawable.ev)
                holder.itemView.setBackgroundResource(R.drawable.rectangle__3_ev)
                holder.timeTextView.setTextColor(Color.WHITE)
                holder.gradientView.stopAnimation() // Stop animation on selection
            }
        } else {
            // Handle unselected states
            //Toast.makeText(holder.itemView.context, "Unselctedposition - "+position, Toast.LENGTH_SHORT).show()
            when (position) {
                0 -> {
                    holder.itemView.setBackgroundResource(R.drawable.rectangle__3_ev)
                    holder.timeTextView.setTextColor(Color.WHITE)
                    holder.gradientView.stopAnimation()
                }
                1,2,3 -> {
                    holder.whitelnr.setBackgroundResource(R.drawable.edgecurved_bgev)
                    holder.timeTextView.setTextColor(Color.BLACK)
                    holder.gradientView.animateGradient(2000L) // Start gradient animation for unselected items
                }
                else -> {
                    holder.itemView.setBackgroundResource(R.drawable.rectangle__1_ev)
                    holder.timeTextView.setTextColor(Color.BLACK)
                    holder.gradientView.stopAnimation()
                }
            }
        }

        // Set a click listener for the time item
        holder.itemView.setOnClickListener {
            if (isSelected) {
                // Deselect the item
                selectedPositions.remove(position)
                holder.gradientView.animateGradient(2000L) // Start animation on deselect
            } else {
                // Select the item
                selectedPositions.add(position)
                holder.gradientView.stopAnimation() // Stop animation on select
            }

            // Update previous selected position
            notifyItemChanged(previousSelectedPosition ?: -1)
            previousSelectedPosition = if (isSelected) null else position

            // Notify the adapter to update the item view
            notifyItemChanged(position)
            onTimeClick(time)
        }
    }

    override fun getItemCount(): Int = timeList.size
}
