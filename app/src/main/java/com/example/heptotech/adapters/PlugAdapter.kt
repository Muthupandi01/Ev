package com.example.heptotech.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Plugs

class PlugAdapter(
    private var plugList: MutableList<Plugs>
) : RecyclerView.Adapter<PlugAdapter.CarInfoViewHolder>() {

    class CarInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topTextView: TextView = itemView.findViewById(R.id.topTextView)
        val bottomTextView: TextView = itemView.findViewById(R.id.bottomTextView)
        val newImageView: ImageView = itemView.findViewById(R.id.imageView)
        val sub: ConstraintLayout = itemView.findViewById(R.id.sub)
        val container: View = itemView // Reference to the whole card layout (if necessary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dynamicplug_card, parent, false)
        return CarInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarInfoViewHolder, position: Int) {
        val plug = plugList[position]

        // Set the regular content
        holder.topTextView.text = plug.topTextView
        holder.bottomTextView.text = plug.bottomTextView
        holder.newImageView.setImageResource(plug.imageView)

        // Check if this is the last item in the list
        if (position == plugList.size - 1) {
            holder.sub.isEnabled=false
            // Change the background and text color to black for the last item

            holder.topTextView.isInvisible=true
            holder.bottomTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.disable))
            holder.sub.setBackgroundResource(R.drawable.blackrec)
            holder.newImageView.imageTintList = ContextCompat.getColorStateList(holder.itemView.context, R.color.disable)
        } else {
            // Reset to default colors for other items
            holder.topTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.bottomTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.greentxt))
        }
    }

    override fun getItemCount(): Int {
        return plugList.size
    }
}
