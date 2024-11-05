package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.ChargeData

class ChargeAdapter(private val cardList: List<ChargeData>) : RecyclerView.Adapter<ChargeAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val acTextView: TextView = itemView.findViewById(R.id.ac_text)
        val idTextView: TextView = itemView.findViewById(R.id.Id)
        val availabilityTextView: TextView = itemView.findViewById(R.id.avail)
        val typeTextView: TextView = itemView.findViewById(R.id.type) // Set correct ID if necessary
        val priceTextView: TextView = itemView.findViewById(R.id.kw) // Set correct ID if necessary
        val onlineStatus: ImageView = itemView.findViewById(R.id.online_green)
        val chdmeImage: ImageView = itemView.findViewById(R.id.chdme)
        val angleUpImage: ImageView = itemView.findViewById(R.id.angle_up)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publicstationcard_charges_recycle, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val data = cardList[position]
        holder.acTextView.text = data.acText
        holder.idTextView.text = data.id
        holder.availabilityTextView.text = data.availability
        holder.typeTextView.text = data.type
        holder.priceTextView.text = data.price

        // Set visibility for the online status image based on availability
        holder.onlineStatus.visibility = if (data.isAvailable) View.VISIBLE else View.GONE

    }


    override fun getItemCount() = cardList.size
}