package com.example.heptotech.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.actvity_view.PubicstationCard
import com.example.heptotech.bean_dataclass.BookmarkEv


class BookmarkEvAdapter(private val bookmarkList: List<BookmarkEv>, private val onItemClick: () -> Unit) :
    RecyclerView.Adapter<BookmarkEvAdapter.BookmarkEvViewHolder>() {

    // ViewHolder for each item in the RecyclerView
    inner class BookmarkEvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val akibuaTextView: TextView = itemView.findViewById(R.id.Akibua_text)
        val distanceTextView: TextView = itemView.findViewById(R.id.busstand_text)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val availabilityTextView: TextView = itemView.findViewById(R.id.two_text)
        val type1TextView: TextView = itemView.findViewById(R.id.type_Ac)
        val type2TextView: TextView = itemView.findViewById(R.id.type_Ac1)
        val type3TextView: TextView = itemView.findViewById(R.id.type_Ac2)
        val meter: TextView = itemView.findViewById(R.id.twohundres_text)
        val evImage: ImageView = itemView.findViewById(R.id.ev)

        val heartImageView: ImageView = itemView.findViewById(R.id.heart)
        init {
            itemView.setOnClickListener {
                onItemClick() // Pass the clicked item
            }
        }

    }

    // Create the ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkEvViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_ev_recycle, parent, false)
        return BookmarkEvViewHolder(itemView)
    }

    // Bind the data to the views
    override fun onBindViewHolder(holder: BookmarkEvViewHolder, position: Int) {
        val bookmarkEv = bookmarkList[position]
        holder.akibuaTextView.text = bookmarkEv.title
        holder.distanceTextView.text = bookmarkEv.distance
        holder.ratingBar.rating = bookmarkEv.rating
        holder.availabilityTextView.text = bookmarkEv.availability
        holder.type1TextView.text = bookmarkEv.type1
        holder.type2TextView.text = bookmarkEv.type2
        holder.type3TextView.text = bookmarkEv.type3
        holder.meter.text = bookmarkEv.meter
        holder.evImage.setImageResource(bookmarkEv.evImg)
        holder.heartImageView.setImageResource(bookmarkEv.heartImg)


    }


    // Return the size of the data list
    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}
