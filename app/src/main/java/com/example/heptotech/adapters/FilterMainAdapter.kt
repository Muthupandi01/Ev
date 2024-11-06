package com.example.heptotech.adapters

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
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FilterMainAdapter.BrandViewHolder>() {

    private val countMap = mutableMapOf<String, Int>()

    interface OnItemClickListener {
        fun onItemClicked(brandName: String, countTextView: TextView)
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

    fun updateCount(brandName: String?, count: Int) {
        // Update count in map if it's non-zero
        if (brandName != null && count > 0) {
            countMap[brandName] = count
            val index = brandList.indexOfFirst { it.name == brandName }
            if (index != -1) {
                notifyItemChanged(index)
            }
        }
    }

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.image)
        private val brandTextView: TextView = itemView.findViewById(R.id.type)
        private val countTextView: TextView = itemView.findViewById(R.id.count)

        fun bind(brand: Filter) {
            brandImageView.setImageResource(brand.imageResId)
            brandTextView.text = brand.name

            // Display count only if available in the map and greater than zero
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
