package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R

class StopsAdapter(
    private val stops: MutableList<String>,
    private val onStopDeleted: (Int) -> Unit,
) : RecyclerView.Adapter<StopsAdapter.StopViewHolder>() {

    class StopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stopEditText: EditText = view.findViewById(R.id.edit) // Access the EditText for each stop
        val locationImageView: ImageView = view.findViewById(R.id.location_svg)
        val stopDelete: ImageView = view.findViewById(R.id.delete_stop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stop, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stopName = stops[position]

        // Set the hint and clear text for new stops (when text is not saved)
        if (stopName.startsWith("Stop")) {
            holder.stopEditText.hint = stopName
            holder.stopEditText.setText("") // Make sure no text is shown for new stops
        } else {
            holder.stopEditText.setText(stopName) // Restore entered text for edited stops
        }

        // Handle focus change to update the stop name
        holder.stopEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val enteredText = holder.stopEditText.text.toString().trim()
                if (enteredText.isNotEmpty()) {
                    stops[position] = enteredText // Update the list with entered text
                } else {
                    stops[position] = holder.stopEditText.hint.toString() // Restore hint if empty
                }
            }
        }

        // Handle stop deletion
        holder.stopDelete.setOnClickListener {
            holder.stopEditText.clearFocus() // Clear focus before deleting
            onStopDeleted(position)
        }
    }


    override fun getItemCount() = stops.size
}
