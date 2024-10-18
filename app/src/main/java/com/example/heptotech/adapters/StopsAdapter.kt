package com.example.heptotech.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.actvity_view.IphoneRouteplan

class StopsAdapter(private val context: Context) : RecyclerView.Adapter<StopsAdapter.StopViewHolder>() {

    private val stopsList = mutableListOf<String>()

    inner class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editText: EditText = itemView.findViewById(R.id.edit)
        val deleteImage: ImageView = itemView.findViewById(R.id.delete_stop)
        val imageView: ImageView = itemView.findViewById(R.id.location_svg)

        fun bind(stopText: String, position: Int) {
            editText.setText(stopText)

            // Set the hint based on the position (e.g., Stop 1, Stop 2, etc.)
            editText.hint = "Stop ${position + 1}"

            // Handle delete click
            deleteImage.setOnClickListener {
                // Handle delete click
                deleteImage.setOnClickListener {
                    // Prevent deleting Stop 1 if Stop 2 has no input
                    if (position == 0 && stopsList.size > 1 && stopsList[1].isEmpty()) {
                        Toast.makeText(context, "Please enter text in Stop 2 before deleting Stop 1", Toast.LENGTH_SHORT).show()
                    } else {
                        removeStop(position)
                    }
                }
            }

            // Update the stop text when the user types
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (position < stopsList.size) {
                        stopsList[position] = s.toString()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_stop, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        if (position < stopsList.size) {
            holder.bind(stopsList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return stopsList.size
    }

    fun addStop() {
        stopsList.add("") // Add a new empty stop
        notifyItemInserted(stopsList.size - 1)
    }

    fun removeStop(position: Int) {
        if (position >= 0 && position < stopsList.size) {
            stopsList.removeAt(position) // Remove the stop
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, stopsList.size) // Update the positions and hints

            // Notify MainActivity to check stops
            (context as IphoneRouteplan).checkStops() // Call checkStops method from MainActivity
        }
    }

    // Method to get the text of a specific stop
    fun getStopText(position: Int): String {
        return if (position < stopsList.size) {
            stopsList[position]
        } else {
            ""
        }
    }
}