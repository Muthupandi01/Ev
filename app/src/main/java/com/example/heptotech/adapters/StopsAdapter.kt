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
        // Set the existing stop name in the EditText
        val stopName = stops[position]
        holder.stopEditText.hint = stopName // Set the hint as the stop name
        holder.stopEditText.setText("")

        // Handle editing the stop name
        holder.stopEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Update the stop name in the list if focus is lost
                stops[position] = holder.stopEditText.text.toString()
            }
        }

        // Handle stop deletion
        holder.stopDelete.setOnClickListener {
            onStopDeleted(position) // Notify the listener to delete the stop
        }
    }

    override fun getItemCount() = stops.size
}
