package com.example.heptotech.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Brand
import com.example.heptotech.bean_dataclass.Filter

class AllinOneAdapter(
    private var brandList: List<Filter>,
    private val context: Context,
    private val onSelectionChanged: (Int, List<Filter>) -> Unit // Callback to update selection count and selected items
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectedItems = mutableSetOf<Filter>() // Track selected items

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (brandList[position].isHeader) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.header_layout, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.allinonecard, parent, false)
            BrandViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val brand = brandList[position]
        if (holder is HeaderViewHolder) {
            holder.bind(brand)
        } else if (holder is BrandViewHolder) {
            holder.bind(brand)
        }
    }

    override fun getItemCount() = brandList.size

    fun getSelectedItems(): List<Filter> = selectedItems.toList() // To retrieve selected items

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.image)
        private val brandTextView: TextView = itemView.findViewById(R.id.type)
        private val checkImageView: ImageView = itemView.findViewById(R.id.check)

        fun bind(brand: Filter) {
            brandImageView.setImageResource(brand.imageResId)
            brandTextView.text = brand.name

            // Toggle selection
            itemView.setOnClickListener {
                if (selectedItems.contains(brand)) {
                    selectedItems.remove(brand)
                    checkImageView.visibility = View.INVISIBLE
                } else {
                    selectedItems.add(brand)
                    checkImageView.visibility = View.VISIBLE
                }

                // Update selection count and selected items via callback
                onSelectionChanged(selectedItems.size, selectedItems.toList())
            }

            // Update UI for selected state
            checkImageView.visibility = if (selectedItems.contains(brand)) View.VISIBLE else View.INVISIBLE
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.header_text)

        fun bind(brand: Filter) {
            headerTextView.text = brand.name
        }
    }
}
