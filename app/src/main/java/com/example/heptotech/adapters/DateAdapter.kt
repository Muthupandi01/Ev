package com.example.heptotech.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.DateModel
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(
    private var dateList: List<DateModel>,
    private val onDateSelected: (DateModel) -> Unit
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectedPosition = -1
    private val currentDate: String = getCurrentDate()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date_picker, parent, false)
        return DateViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged", "RecyclerView")
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val dateModel = dateList[position]
        holder.bind(dateModel, position == selectedPosition, dateModel.date==currentDate.toInt())

        holder.itemView.setOnClickListener {
            selectedPosition = position
            onDateSelected(dateModel)
            notifyDataSetChanged()  // Refresh view to show the selection
        }
    }

    override fun getItemCount(): Int = dateList.size

    fun updateDates(newDateList: List<DateModel>) {
        this.dateList = newDateList
        notifyDataSetChanged()
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd", Locale.getDefault())  // Assuming day format as 'dd'
        return sdf.format(Date())
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.dayText)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateText)

        fun bind(dateModel: DateModel, isSelected: Boolean, isCurrentDate: Boolean) {
            dayTextView.text = dateModel.day
            dateTextView.text = dateModel.date.toString()

            when {
                isSelected -> {
                    // Apply selected date style
                    itemView.setBackgroundResource(R.drawable.rectangle_34624286)
                    dateTextView.setBackgroundResource(R.drawable.ellipse_617)
                    dayTextView.setTextColor(Color.WHITE)
                    dateTextView.setTextColor(Color.BLACK)
                }
                isCurrentDate -> {
                    // Apply current date style
                    itemView.setBackgroundResource(R.drawable.rectangle_34624288)
                    dateTextView.setBackgroundResource(R.drawable.default_background)
                    dayTextView.setTextColor(Color.BLACK) // Customize current date color as needed
                    dateTextView.setTextColor(Color.BLACK)
                }
                else -> {
                    // Apply default unselected style
                    itemView.setBackgroundResource(R.drawable.default_background)
                    dateTextView.setBackgroundResource(R.drawable.default_background)
                    dayTextView.setTextColor(Color.BLACK)
                    dateTextView.setTextColor(Color.BLACK)
                }
            }
        }
    }
}
