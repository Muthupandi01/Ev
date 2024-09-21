package com.example.heptotech.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.SiteAdd

class SiteAddAdapter(private val siteList: List<SiteAdd>) :
    RecyclerView.Adapter<SiteAddAdapter.SiteViewHolder>() {

    private var selectedPosition = -1  // Track the selected item position (-1 means no selection)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.siterec_cardbottomsheet, parent, false)
        return SiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val site = siteList[position]
        holder.stationName.text = site.stationname
        holder.address.text = site.Adderess

        // Show check for selected item, hide for others
        if (position == selectedPosition) {
            holder.check.visibility = View.VISIBLE
            holder.imgview.visibility = View.GONE
        } else {
            holder.check.visibility = View.GONE
            holder.imgview.visibility = View.VISIBLE
        }

        // Handle click on card view
        holder.cardView.setOnClickListener {
            // Update selected position
            val previousSelectedPosition = selectedPosition
            selectedPosition = position

            // Notify adapter to refresh both old and new selected items
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return siteList.size
    }

    inner class SiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: LinearLayout = itemView.findViewById(R.id.cardView)
        val stationName: TextView = itemView.findViewById(R.id.stationname)
        val address: TextView = itemView.findViewById(R.id.addresses)
        val check: ImageView = itemView.findViewById(R.id.check)
        val imgview: ImageView = itemView.findViewById(R.id.imgviews)
    }
}

