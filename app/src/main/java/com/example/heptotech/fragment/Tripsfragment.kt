package com.example.heptotech.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.TravelAdapter
import com.example.heptotech.bean_dataclass.TravelItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class Tripsfragment : Fragment(R.layout.trips_tab) {

    private lateinit var tripsAdapter: TravelAdapter
    private lateinit var barChart: BarChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rectrips = view.findViewById<RecyclerView>(R.id.rectrips)
        val cardsheduled = view.findViewById<CardView>(R.id.cardsheduled)

        val lnr_past = view.findViewById<LinearLayout>(R.id.lnr_past)
        val txt_pasttrip = view.findViewById<TextView>(R.id.txt_pasttrip)
        val img_pasttrip = view.findViewById<ImageView>(R.id.img_pasttrip)

        val lnr_scheduled = view.findViewById<LinearLayout>(R.id.lnr_scheduled)
        val txt_scheduled = view.findViewById<TextView>(R.id.txt_scheduled)
        val img_scheduled = view.findViewById<ImageView>(R.id.img_scheduled)
        barChart=view.findViewById<BarChart>(R.id.barChart)


        lnr_past.setOnClickListener {
            rectrips.isVisible = true
            cardsheduled.isVisible = false
            img_scheduled.isVisible = false
            img_pasttrip.isVisible = true
            txt_pasttrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_common))
            txt_scheduled.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_common))
        }

        lnr_scheduled.setOnClickListener {
            cardsheduled.isVisible = true
            rectrips.isVisible = false
            img_scheduled.isVisible = true
            img_pasttrip.isVisible = false
            txt_pasttrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_common))
            txt_scheduled.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_common))
        }

        // Initialize the adapter with an empty list or data
        tripsAdapter = TravelAdapter(listOf())

        // Set up RecyclerView
        rectrips.layoutManager = LinearLayoutManager(requireContext())
        rectrips.adapter = tripsAdapter

        // Load data into the adapter
        loadTripsData()

        // Set up and customize the bar chart
        setupBarChart()
    }

    private fun loadTripsData() {
        // Replace with your actual data source
        val trips = listOf(
            TravelItem("Wed, Jul 10, 2024", "Completed", "Kampala", "Entebbe Airport"),
            TravelItem("Thu, Jul 11, 2024", "In Progress", "Entebbe Airpor", "Kampala"),
            TravelItem("Thu, Jul 11, 2024", "In Progress", "Entebbe Airpor", "Kampala"),
            TravelItem("Thu, Jul 11, 2024", "In Progress", "Entebbe Airpor", "Kampala"),
            TravelItem("Thu, Jul 11, 2024", "Canceled", "Nairobi", "Kampala")
        )

        // Update adapter data
        tripsAdapter.updateTrips(trips)
    }

    private fun setupBarChart() {
        // Data entries for the bar chart
        val entries = listOf(
            BarEntry(0f, 500f),
            BarEntry(1f, 800f),
            BarEntry(2f, 1200f),
            BarEntry(3f, 700f),
            BarEntry(4f, 600f),
            BarEntry(5f, 900f),
            BarEntry(6f, 1100f),
            BarEntry(7f, 850f),
            BarEntry(8f, 950f),
            BarEntry(9f, 1000f)
        )

        val barDataSet = BarDataSet(entries, "Distance").apply {
            colors = listOf(
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.greentxt),
                ContextCompat.getColor(requireContext(), R.color.blue_common)
            )
            valueTextColor = Color.BLACK
            valueTextSize = 12f
            barBorderWidth = 0.4f
            setDrawValues(false)
        }

        val data = BarData(barDataSet)
        barChart.data = data

        // Configure X axis labels
        val timeLabels = arrayOf("09", "10", "11", "12", "13", "14", "15", "16", "17", "18")
        barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(timeLabels)
            granularity = 1f
            textColor = Color.BLACK
            textSize = 12f
            position = XAxis.XAxisPosition.BOTTOM
            setDrawAxisLine(true)
            setDrawGridLines(false)
            setDrawLabels(true)
            labelCount = timeLabels.size
        }

        // Configure right Y axis
        barChart.axisRight.apply {
            isEnabled = true
            axisMinimum = 0f
            axisMaximum = 1200f
            textColor = Color.BLACK
            setDrawGridLines(true)
            setDrawAxisLine(true)
            granularity = 300f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return when (value) {
                        0f -> "0"
                        300f -> "300"
                        600f -> "600"
                        900f -> "900"
                        1200f -> "1200"
                        else -> ""
                    }
                }
            }
        }

        // Configure left Y axis
        barChart.axisLeft.isEnabled = false

        // Other customizations
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.invalidate() // Refresh the chart
    }

















}
