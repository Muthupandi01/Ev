package com.example.heptotech.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Filter

class FilterMainAdapter(
    private var brandList: List<Filter>,
    private val itemClickListener: OnItemClickListener,
    private val context: Context // Pass context to access SharedPreferences
) : RecyclerView.Adapter<FilterMainAdapter.BrandViewHolder>() {

    private val countMap = mutableMapOf<String, Int>()

    interface OnItemClickListener {
        fun onItemClicked(brandName: String, countTextView: TextView)
    }

    init {
        // Load counts from SharedPreferences
        loadCountsFromSharedPreferences()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filterconnectertype_card, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brandList[position]
        holder.bind(brand)
    }

    override fun getItemCount() = brandList.size

    // Update count in the map and SharedPreferences
    fun updateCount(brandName: String?, count: Int) {
        if (brandName != null && count >= 0) {
            countMap[brandName] = count
            // Save updated count to SharedPreferences
            saveCountToSharedPreferences(brandName, count)

            val index = brandList.indexOfFirst { it.name == brandName }
            if (index != -1) {
                notifyItemChanged(index)
            }
        }
    }

    // Save the count to SharedPreferences
    private fun saveCountToSharedPreferences(brandName: String, count: Int) {
        val sharedPref = context.getSharedPreferences("USER_SELECTIONS", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(brandName, count) // Store count with the brand name as the key
        editor.apply()
    }

    // Load the count from SharedPreferences
    private fun loadCountsFromSharedPreferences() {
        val sharedPref = context.getSharedPreferences("USER_SELECTIONS", Context.MODE_PRIVATE)
        brandList.forEach { brand ->
            val count = sharedPref.getInt(brand.name, 0) // Default count is 0 if not found
            countMap[brand.name] = count
        }
    }

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.image)
        private val brandTextView: TextView = itemView.findViewById(R.id.type)
        private val countTextView: TextView = itemView.findViewById(R.id.count)

        fun bind(brand: Filter) {
            brandImageView.setImageResource(brand.imageResId)
            brandTextView.text = brand.name

            // Display count from the map (or default to 0)
            val count = countMap[brand.name] ?: 0

            if (count > 0) {
               countTextView.text = " ($count)"
                countTextView.visibility = View.VISIBLE
            } else {
                countTextView.visibility = View.GONE
            }

            itemView.setOnClickListener {
                itemClickListener.onItemClicked(brand.name, countTextView)
            }
        }
    }
}
