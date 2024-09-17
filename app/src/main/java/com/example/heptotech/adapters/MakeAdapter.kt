package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.actvity_view.MakeBrandModel
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Make

class MakeAdapter(
    private var makeList: List<Make>,
    private val clickListener: MakeBrandModel
) : RecyclerView.Adapter<MakeAdapter.MakeViewHolder>() {

    private var originalList: List<Make> = makeList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.make_card, parent, false)
        return MakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakeViewHolder, position: Int) {
        val make = makeList[position]
        holder.bind(make, position)
    }

    override fun getItemCount() = makeList.size

    inner class MakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val makeImageView: ImageView = itemView.findViewById(R.id.make_id_img)
        private val makeTextView: TextView = itemView.findViewById(R.id.make_id_txt)
        private val makeCardParent: View = itemView.findViewById(R.id.make_card_parent)

        fun bind(make: Make, position: Int) {
            makeTextView.text = make.name

            makeImageView.setImageResource(make.imageResId)

            itemView.setOnClickListener {
                clickListener.onItemSelected(make)
            }
          }
      }

    fun filter(query: String) {
        makeList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}
