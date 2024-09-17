package com.example.heptotech.adapters


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.siterec_cardbottomsheet, parent, false)
        return SiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        val site = siteList[position]
        holder.stationName.text = site.stationname
        holder.address.text = site.Adderess
        if (position == 0) {
            holder.check.visibility = View.VISIBLE
            holder.imgview.visibility = View.GONE
        } else {
            holder.check.visibility = View.GONE
            holder.imgview.visibility = View.VISIBLE
        }


        holder.cardView.setOnClickListener {
            if (holder.check.visibility == View.GONE) {
                holder.check.visibility = View.VISIBLE
                holder.imgview.visibility = View.GONE
            } else {
                holder.check.visibility = View.GONE
                holder.imgview.visibility = View.VISIBLE
            }
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
