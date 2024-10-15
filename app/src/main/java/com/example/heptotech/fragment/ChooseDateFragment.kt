package com.example.heptotech.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import com.example.heptotech.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseDateFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_example_bottom_sheet, container, false)
        val dayPicker = view.findViewById<NumberPicker>(R.id.datePicker)
        val monthPicker = view.findViewById<NumberPicker>(R.id.monthPicker)
        val yearPicker = view.findViewById<NumberPicker>(R.id.yearPicker)
        val hourPicker = view.findViewById<NumberPicker>(R.id.time_picker)
        val minutePicker = view.findViewById<NumberPicker>(R.id.minute_Picker)
        val amPmPicker = view.findViewById<NumberPicker>(R.id.am_PmPicker)
        val doneTextView = view.findViewById<TextView>(R.id.done_text)
        dayPicker.minValue = 1
        dayPicker.maxValue = 31

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.displayedValues = resources.getStringArray(R.array.monthstime_array)
        yearPicker.minValue = 1900
        yearPicker.maxValue = 2100



        // Optionally set initial date
        dayPicker.value = 1
        monthPicker.value = 1
        yearPicker.value = 2024

        // Set listeners to get the selected values
        dayPicker.setOnValueChangedListener { _, _, newDay ->
            Log.d("SelectedDay", "$newDay")
        }

        monthPicker.setOnValueChangedListener { _, _, newMonth ->
            Log.d("SelectedMonth", "$newMonth")
        }

        yearPicker.setOnValueChangedListener { _, _, newYear ->
            Log.d("SelectedYear", "$newYear")
        }
        hourPicker.minValue = 1
        hourPicker.maxValue = 12
        hourPicker.wrapSelectorWheel = true

        // Set values for minute picker (0 to 59)
        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.wrapSelectorWheel = true
        minutePicker.setFormatter { i -> String.format("%02d", i) } // Show 2 digits

        // Set values for AM/PM picker (0 = AM, 1 = PM)
        amPmPicker.minValue = 0
        amPmPicker.maxValue = 1
        amPmPicker.displayedValues = arrayOf("AM", "PM")
        amPmPicker.wrapSelectorWheel = true

        // Set listeners for pickers (optional, for handling user selections)
        hourPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            // Do something with the selected hour
        }

        minutePicker.setOnValueChangedListener { picker, oldVal, newVal ->
            // Do something with the selected minute
        }

        amPmPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            // Do something with the selected AM/PM
            val selectedAmPm = if (newVal == 0) "AM" else "PM"
            // Toast.makeText(this, "Selected: $selectedAmPm", Toast.LENGTH_SHORT).show()
        }
        doneTextView.setOnClickListener {
            // Get selected date
            val selectedDay = dayPicker.value
            val selectedMonth = monthPicker.displayedValues[monthPicker.value - 1]
            val selectedYear = yearPicker.value

            // Get selected time
            val selectedHour = hourPicker.value
            val selectedMinute = String.format("%02d", minutePicker.value)
            val selectedAmPm = amPmPicker.displayedValues[amPmPicker.value]

            // Format date and time
            val selectedDateTime = "$selectedDay $selectedMonth $selectedYear, $selectedHour:$selectedMinute $selectedAmPm"

            // Find the target TextView where date and time will be displayed
           // val chooseTextView = view.findViewById<TextView>(R.id.choose_text)

            // Set the selected date and time to the TextView
            activity?.findViewById<TextView>(R.id. choose_date)?.text = selectedDateTime



            // Log the selected values (optional)
            Log.d("SelectedDateTime", selectedDateTime)
            dismiss()
        }


        // Return the inflated view
        return view

    }
}