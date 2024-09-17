package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.SheduledItemcarddata

class ShedduledAdapter(
    private val itemList: List<SheduledItemcarddata>,
    private val onItemClick: (SheduledItemcarddata) -> Unit
) : RecyclerView.Adapter<ShedduledAdapter.ItemCardViewHolder>() {

    inner class ItemCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgview)
        val stationName: TextView = itemView.findViewById(R.id.stationname)
        val date: TextView = itemView.findViewById(R.id.date)
        val location: TextView = itemView.findViewById(R.id.location)
        val kWh: TextView = itemView.findViewById(R.id.kwhchild)
        val duration: TextView = itemView.findViewById(R.id.durationchild)
        val fee: TextView = itemView.findViewById(R.id.feechild)
       // val cardView: LinearLayout = itemView.findViewById(R.id.parent)
        val edit_img: ImageView = itemView.findViewById(R.id.edit_img)
        val delete_img: ImageView = itemView.findViewById(R.id.delete_img)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(itemList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scheduled_orders, parent, false)
        return ItemCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCardViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.imageView.setImageResource(currentItem.imgview)
        holder.stationName.text = currentItem.stationname
        holder.date.text = currentItem.date
        holder.location.text = currentItem.location
        holder.kWh.text = currentItem.kwhChild
        holder.duration.text = currentItem.durationChild
        holder.fee.text = currentItem.feeChild
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
