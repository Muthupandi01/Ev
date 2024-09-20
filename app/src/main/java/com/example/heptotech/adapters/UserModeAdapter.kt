package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.UserMode


class UserModeAdapter(private val items: List<UserMode>, private val onItemSelected: (Int) -> Unit) : RecyclerView.Adapter<UserModeAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.vector_img)
        val textView: TextView = itemView.findViewById(R.id.chadme_text)
        val radioButton: RadioButton = itemView.findViewById(R.id.raiobutton)

        init {
            radioButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition)  // Unselect the previously selected item
                    selectedPosition = position
                    notifyItemChanged(selectedPosition)  // Select the new item
                    onItemSelected(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usemode_design_recycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textView.text = item.text
        holder.radioButton.isChecked = position == selectedPosition
    }

    override fun getItemCount(): Int = items.size
}