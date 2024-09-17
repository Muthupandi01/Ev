package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.actvity_view.MakeBrandModel
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Model

class ModelAdapter(
    private var modelList: List<Model>,
    private val clickListener: MakeBrandModel
) : RecyclerView.Adapter<ModelAdapter.ModelViewHolder>() {

    private var originalList: List<Model> = modelList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_card, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val model = modelList[position]
        holder.bind(model)
    }

    override fun getItemCount() = modelList.size

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val modelTextView: TextView = itemView.findViewById(R.id.model_id_txt)

        fun bind(model: Model) {
            modelTextView.text = model.name
            itemView.setOnClickListener {
                clickListener.onItemSelected(model)
            }
        }
    }

    fun filter(query: String) {
        modelList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}
