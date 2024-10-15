package com.example.heptotech.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import com.example.heptotech.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseDatesFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.activity_ride_end, container, false)
        val dayPickers = view.findViewById<NumberPicker>(R.id.datePickers)
        val monthPickers = view.findViewById<NumberPicker>(R.id.monthPickers)
        val yearPickers = view.findViewById<NumberPicker>(R.id.yearPickers)
        val hourPickers = view.findViewById<NumberPicker>(R.id.time_pickers)
        val minutePickers = view.findViewById<NumberPicker>(R.id.minute_Pickers)
        val amPmPickers = view.findViewById<NumberPicker>(R.id.am_PmPickers)
        val doneTextViews = view.findViewById<TextView>(R.id.done_texts)
        dayPickers.minValue = 1
        dayPickers.maxValue = 31

        monthPickers.minValue = 1
        monthPickers.maxValue = 12
        monthPickers.displayedValues = resources.getStringArray(R.array.monthstime_array)
        yearPickers.minValue = 1900
        yearPickers.maxValue = 2100



        // Optionally set initial date
        dayPickers.value = 1
        monthPickers.value = 1
        yearPickers.value = 2024

        // Set listeners to get the selected values
        dayPickers.setOnValueChangedListener { _, _, newDay ->
            Log.d("SelectedDay", "$newDay")
        }

        monthPickers.setOnValueChangedListener { _, _, newMonth ->
            Log.d("SelectedMonth", "$newMonth")
        }

        yearPickers.setOnValueChangedListener { _, _, newYear ->
            Log.d("SelectedYear", "$newYear")
        }
        hourPickers.minValue = 1
        hourPickers.maxValue = 12
        hourPickers.wrapSelectorWheel = true

        // Set values for minute picker (0 to 59)
        minutePickers.minValue = 0
        minutePickers.maxValue = 59
        minutePickers.wrapSelectorWheel = true
        minutePickers.setFormatter { i -> String.format("%02d", i) } // Show 2 digits

        // Set values for AM/PM picker (0 = AM, 1 = PM)
        amPmPickers.minValue = 0
        amPmPickers.maxValue = 1
        amPmPickers.displayedValues = arrayOf("AM", "PM")
        amPmPickers.wrapSelectorWheel = true

        // Set listeners for pickers (optional, for handling user selections)
        hourPickers.setOnValueChangedListener { picker, oldVal, newVal ->
            // Do something with the selected hour
        }

        minutePickers.setOnValueChangedListener { picker, oldVal, newVal ->
            // Do something with the selected minute
        }

        amPmPickers.setOnValueChangedListener { picker, oldVal, newVal ->
            // Do something with the selected AM/PM
            val selectedAmPm = if (newVal == 0) "AM" else "PM"
            // Toast.makeText(this, "Selected: $selectedAmPm", Toast.LENGTH_SHORT).show()
        }
        doneTextViews.setOnClickListener {
            // Get selected date
            val selectedDay = dayPickers.value
            val selectedMonth = monthPickers.displayedValues[monthPickers.value - 1]
            val selectedYear = yearPickers.value

            // Get selected time
            val selectedHour = hourPickers.value
            val selectedMinute = String.format("%02d", minutePickers.value)
            val selectedAmPm = amPmPickers.displayedValues[amPmPickers.value]

            // Format date and time
            val selectedDateTime = "$selectedDay $selectedMonth $selectedYear, $selectedHour:$selectedMinute $selectedAmPm"

            // Find the target TextView where date and time will be displayed
            // val chooseTextView = view.findViewById<TextView>(R.id.choose_text)

            // Set the selected date and time to the TextView
            activity?.findViewById<TextView>(R.id. choose_dates)?.text = selectedDateTime



            // Log the selected values (optional)
            Log.d("SelectedDateTime", selectedDateTime)
            dismiss()
        }


        // Return the inflated view
        return view

    }
}