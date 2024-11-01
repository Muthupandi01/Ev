package com.example.heptotech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.bean_dataclass.OperatorData

class OperatorAdapter(
    private val operatorList: List<OperatorData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<OperatorAdapter.OperatorViewHolder>() {

    inner class OperatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ImageView = itemView.findViewById(R.id.user_img1)
        val username: TextView = itemView.findViewById(R.id.username_txt1)
        val relationStatus: TextView = itemView.findViewById(R.id.relationstatus1)
        val cardType: TextView = itemView.findViewById(R.id.typecard1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.operator_recycle, parent, false)
        return OperatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: OperatorViewHolder, position: Int) {
        val operator = operatorList[position]
        holder.userImage.setImageResource(operator.userImageResId)
        holder.username.text = operator.username
        holder.relationStatus.text = operator.relationStatus
        holder.cardType.text = operator.cardType

        // Set click listener
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(operator)
        }
    }

    override fun getItemCount(): Int = operatorList.size

    // Define the interface for click handling
    interface OnItemClickListener {
        fun onItemClick(operator: OperatorData)
    }
}
