package com.example.heptotech.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Brand
import com.example.heptotech.bean_dataclass.Filter
import com.example.heptotech.bean_dataclass.FilterConnecter

class AllinOneAdapterConnecter(
    private var brandList: List<FilterConnecter>,
    private val context: Context,
    private val onSelectionChanged: (Int, List<FilterConnecter>) -> Unit // Callback to update selection count and selected items
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectedItems = mutableSetOf<FilterConnecter>() // Track selected items

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    init {
        // Load selected items from SharedPreferences and update the selection state
        selectedItems.addAll(loadSelectedItems(context))
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

    fun getSelectedItems(): List<FilterConnecter> = selectedItems.toList() // To retrieve selected items

    private fun loadSelectedItems(context: Context): List<FilterConnecter> {
        val sharedPref = context.getSharedPreferences("USER_SELECTIONS", Context.MODE_PRIVATE)
        val selectedNames = sharedPref.getStringSet("SELECTED_CONNECTOR", emptySet()) ?: emptySet()

        // Retrieve the selected items based on the names (or IDs)
        val selectedItems = mutableListOf<FilterConnecter>()
        for (name in selectedNames) {
            selectedItems.add(brandList.find { it.name == name } ?: FilterConnecter(name = name)) // Modify as needed
        }

        return selectedItems
    }

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.image)
        private val brandTextView: TextView = itemView.findViewById(R.id.type)
        private val checkImageView: ImageView = itemView.findViewById(R.id.check)

        fun bind(brand: FilterConnecter) {
            brandImageView.setImageResource(brand.imageResId)
            brandTextView.text = brand.name

            // Set the initial visibility of the checkmark based on the current selection
            checkImageView.visibility = if (selectedItems.contains(brand)) View.VISIBLE else View.INVISIBLE

            // Toggle selection on click
            itemView.setOnClickListener {
                if (selectedItems.contains(brand)) {
                    selectedItems.remove(brand)  // Deselect the item
                    checkImageView.visibility = View.INVISIBLE  // Hide the checkmark
                } else {
                    selectedItems.add(brand)  // Select the item
                    checkImageView.visibility = View.VISIBLE  // Show the checkmark
                }

                // Update the selection count and selected items via the callback
                onSelectionChanged(selectedItems.size, selectedItems.toList())

                // Save the updated selection to SharedPreferences
                saveSelectedItems(context, selectedItems.toList())
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.header_text)

        fun bind(brand: FilterConnecter) {
            headerTextView.text = "    "+brand.name
        }
    }

    // Save selected items to SharedPreferences
    private fun saveSelectedItems(context: Context, selectedItems: List<FilterConnecter>) {
        val sharedPref = context.getSharedPreferences("USER_SELECTIONS", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Convert selected items to a set of names or IDs
        val selectedNames = selectedItems.map { it.name }.toSet()
        editor.putStringSet("SELECTED_CONNECTOR", selectedNames)
        editor.putInt("SELECTED_COUNT", selectedItems.size)
        editor.apply()
    }
}
