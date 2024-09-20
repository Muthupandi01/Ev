package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.VechicleUserItem

class VechicleUserAdapter (private val items: List<VechicleUserItem>) : RecyclerView.Adapter<VechicleUserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.car_img)
        val textView: TextView = itemView.findViewById(R.id.tesela)
        val textView1: TextView = itemView.findViewById(R.id.licene)
        val checkBox: CheckBox = itemView.findViewById(R.id.checbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vechiclelist_recycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textView.text = item.text1
        holder.textView1.text = item.text2
        holder.checkBox.isChecked = item.isChecked

        // Handle CheckBox state changes
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            // Optionally notify or handle state changes
        }
    }

    override fun getItemCount(): Int = items.size
}
