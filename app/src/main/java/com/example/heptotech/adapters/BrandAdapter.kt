package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.actvity_view.MakeBrandModel
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Brand

class BrandAdapter(
    private var brandList: List<Brand>,
    private val clickListener: MakeBrandModel
) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    private var originalList: List<Brand> = brandList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_card, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brandList[position]
        holder.bind(brand)
    }

    override fun getItemCount() = brandList.size

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.brand_id_img)
        private val brandTextView: TextView = itemView.findViewById(R.id.brand_id_txt)

        fun bind(brand: Brand) {
            brandImageView.setImageResource(brand.imageResId)
            brandTextView.text = brand.name
            itemView.setOnClickListener {
                clickListener.onItemSelected(brand)
            }
        }
    }

    fun filter(query: String) {
        brandList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}
