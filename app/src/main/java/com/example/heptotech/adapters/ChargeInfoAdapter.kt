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
import com.example.heptotech.bean_dataclass.ChatData

class ChargeInfoAdapter(
    private var chargeList: MutableList<ChatData>
) : RecyclerView.Adapter<ChargeInfoAdapter.ChargeInfoViewHolder>() {

    class ChargeInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCharge: ImageView = itemView.findViewById(R.id.img_chrge)
        val muthuText: TextView = itemView.findViewById(R.id.muthu_text)
        val timeText: TextView = itemView.findViewById(R.id.two_text)
        val successfulText: TextView = itemView.findViewById(R.id.succesful_teext)
        val typeText: TextView = itemView.findViewById(R.id.type2)
        val developerText: TextView = itemView.findViewById(R.id.developer_text)
        val replyText: TextView = itemView.findViewById(R.id.reply)
        val flagTextview: TextView = itemView.findViewById(R.id.flag)
        val uknTextview: TextView = itemView.findViewById(R.id.ukn_text)
        val deImage: ImageView = itemView.findViewById(R.id.de_img)
        val chiImage: ImageView = itemView.findViewById(R.id.chimg)
        val likeImage: ImageView = itemView.findViewById(R.id.likeimg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargeInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_recycle, parent, false)
        return ChargeInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChargeInfoViewHolder, position: Int) {
        val chargeData = chargeList[position]

        holder.imgCharge.setImageResource(chargeData.imageResId)
        holder.muthuText.text = chargeData.name
        holder.timeText.text = chargeData.timeAgo
        holder.successfulText.text = chargeData.successfulMessage
        holder.typeText.text = chargeData.type
        holder.developerText.text = chargeData.developerText
        holder.replyText.text = chargeData.replyText
        holder.flagTextview.text=chargeData.flagText
        holder.uknTextview.text=chargeData.uknText
        holder.deImage.setImageResource(chargeData.deImg)


        if(position == 0)
        {
            holder.successfulText.isVisible=false
            holder.likeImage.isVisible=false
            holder.deImage.isVisible=false
            holder.chiImage.isVisible=false
            holder.uknTextview.isVisible=false
            holder.typeText.isVisible=false


        }
       else if(position == 1)
        {

            holder.successfulText.isVisible=true
            holder.likeImage.isVisible=true
            holder.deImage.isVisible=true
            holder.chiImage.isVisible=true
            holder.uknTextview.isVisible=true
            holder.typeText.isVisible=true

        }
        else if(position == 2)
        {
            holder.successfulText.isVisible=false
            holder.likeImage.isVisible=false
            holder.deImage.isVisible=false
            holder.chiImage.isVisible=false
            holder.uknTextview.isVisible=false
            holder.typeText.isVisible=false

        }
        else if(position == 3)
        {
            holder.successfulText.isVisible=true
            holder.likeImage.isVisible=true
            holder.deImage.isVisible=true
            holder.chiImage.isVisible=true
            holder.uknTextview.isVisible=true
            holder.typeText.isVisible=true

        }


    }

    override fun getItemCount(): Int {
        return chargeList.size
    }
}
