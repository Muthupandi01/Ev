package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.AllinOneAdapter
import com.example.heptotech.bean_dataclass.Filter

class AllinOneFilter : AppCompatActivity() {

    private lateinit var allinOneAdapter: AllinOneAdapter
    private lateinit var filerMainrec: RecyclerView
    private lateinit var header: TextView
    private lateinit var btn_submit: TextView
    var selectedCountTextView = 0 // Display selected count
    var selectedItemsList = mutableListOf<Filter>() // Track selected items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allin_one_filter)

        filerMainrec = findViewById(R.id.allinrec)
        header = findViewById(R.id.header)
        btn_submit = findViewById(R.id.btn_submit)

        val brandName = intent.getStringExtra("BRAND_NAME")
        header.text = brandName

        // Retrieve selected items from SharedPreferences
        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
        selectedCountTextView = sharedPref.getInt("SELECTED_COUNT", 0)
        val selectedItemsSet = sharedPref.getStringSet("SELECTED_ITEMS", emptySet())

        // Update the UI or adapter with the saved values
        selectedItemsList = selectedItemsSet?.map { Filter(name = it) }?.toMutableList() ?: mutableListOf()
        Log.d("selectedItemsListN", "onCreate: "+selectedItemsList)

        // Initialize the list based on brandName
        val brandList: List<Filter> = when (brandName) {
            "Connnector types" -> listOf(
                Filter(0, "DC", isHeader = true),
                Filter(R.drawable.connecttype_ev, "CCS Combo"),
                Filter(R.drawable.connecttype_ev, "Tesla CCS Combo"),
                Filter(R.drawable.connecttype_ev, "Tesla Type 2"),
                Filter(R.drawable.connecttype_ev, "CHAdeMO"),
                Filter(0, "AC", isHeader = true),
                Filter(R.drawable.connecttype_ev, "Type 2 Mennekes"),
                Filter(R.drawable.connecttype_ev, "Type 1 Yazaki"),
                Filter(R.drawable.connecttype_ev, "Tesla Tpe 1"),
                Filter(R.drawable.connecttype_ev, "3-Pin"),
                Filter(R.drawable.connecttype_ev, "Commando")
            )
            "Networks" -> listOf(
                Filter(R.drawable.location_pin__1_ev, "Alfa Power"),
                Filter(R.drawable.location_pin__1_ev, "Applegreen Electric"),
                Filter(R.drawable.location_pin__1_ev, "Char.gy"),
                Filter(R.drawable.location_pin__1_ev, "Connected Kerb"),
                Filter(R.drawable.location_pin__1_ev, "ESB Energy"),
                Filter(R.drawable.location_pin__1_ev, "Evyve"),
                Filter(R.drawable.location_pin__1_ev, "Fastned"),
                Filter(R.drawable.location_pin__1_ev, "FOR-EV"),
                Filter(R.drawable.location_pin__1_ev, "GeniePoint"),
                Filter(R.drawable.location_pin__1_ev, "Mer"),
                Filter(R.drawable.location_pin__1_ev, "MFG EV Power"),
                Filter(R.drawable.location_pin__1_ev, "Osprey"),
                Filter(R.drawable.location_pin__1_ev, "Pogo Charge")
            )
            "Location types" -> listOf(
                Filter(R.drawable.location_pin__1_ev, "Accomodation"),
                Filter(R.drawable.location_pin__1_ev, "Car park"),
                Filter(R.drawable.location_pin__1_ev, "Dealership"),
                Filter(R.drawable.location_pin__1_ev, "Education"),
                Filter(R.drawable.location_pin__1_ev, "Fuel Forecourt"),
                Filter(R.drawable.location_pin__1_ev, "Health Services"),
                Filter(R.drawable.location_pin__1_ev, "Home"),
                Filter(R.drawable.location_pin__1_ev, "Leisure"),
                Filter(R.drawable.location_pin__1_ev, "Motorway Services"),
                Filter(R.drawable.location_pin__1_ev, "On-Street"),
                Filter(R.drawable.location_pin__1_ev, "Other"),
                Filter(R.drawable.location_pin__1_ev, "Park and Ride"),
                Filter(R.drawable.location_pin__1_ev, "Public Services"),
                Filter(R.drawable.location_pin__1_ev, "Restaurent/Pub/Cafe"),
                Filter(R.drawable.location_pin__1_ev, "Retail Car Park"),
                Filter(R.drawable.location_pin__1_ev, "Supermarket"),
                Filter(R.drawable.location_pin__1_ev, "Travel Interchange"),
                Filter(R.drawable.location_pin__1_ev, "Workplace Car Park")
            )
            "Access" -> listOf(
                Filter(R.drawable.location_pin__1_ev, "24 hours access"),
                Filter(R.drawable.location_pin__1_ev, "Public access"),
                Filter(R.drawable.location_pin__1_ev, "No physical restrictions"),
                Filter(R.drawable.location_pin__1_ev, "Hide Zap-Home points"),
                Filter(R.drawable.location_pin__1_ev, "Hide Zap-Work points"),
                Filter(R.drawable.location_pin__1_ev, "Show Taxi Only")
            )
            "User rating" -> listOf(
                Filter(R.drawable.location_pin__1_ev, "2 stars or more"),
                Filter(R.drawable.location_pin__1_ev, "3 stars or more"),
                Filter(R.drawable.location_pin__1_ev, "4 stars or more")
            )
            "Multiple devices" -> listOf(
                Filter(R.drawable.connecttype_ev, "2 devices or more"),
                Filter(R.drawable.connecttype_ev, "3 devices or more"),
                Filter(R.drawable.connecttype_ev, "4 devices or more"),
                Filter(R.drawable.connecttype_ev, "5 devices or more"),
                Filter(R.drawable.connecttype_ev, "6 devices or more")
            )
            else -> listOf(
                Filter(R.drawable.location_pin__1_ev, "Residential"),
                Filter(R.drawable.location_pin__1_ev, "Corporate"),
                Filter(R.drawable.location_pin__1_ev, "Public")
            )
        }

        // Pass brandList to the adapter
        allinOneAdapter = AllinOneAdapter(brandList, this) { selectedCount, selectedItems ->
            selectedCountTextView = selectedCount
            selectedItemsList = selectedItems.toMutableList()
            // Update UI or other components if needed
        }

        filerMainrec.layoutManager = LinearLayoutManager(this)
        filerMainrec.adapter = allinOneAdapter

//        btn_submit.setOnClickListener {
//            val resultIntent = Intent().apply {
//                putExtra("BRAND_NAME", brandName)
//                putExtra("SELECTED_COUNT", selectedCountTextView)
//                putExtra("SELECTED_ITEMS", ArrayList(selectedItemsList)) // Pass selected items as a list
//            }
//            setResult(RESULT_OK, resultIntent)
//            finish()
//        }
        btn_submit.setOnClickListener {
            val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
            val editor = sharedPref.edit()

            // Save selected count and items
            editor.putInt("SELECTED_COUNT", selectedCountTextView)
            editor.putStringSet("SELECTED_ITEMS", selectedItemsList.map { it.name }.toSet())

            editor.apply()

            val resultIntent = Intent().apply {
                putExtra("BRAND_NAME", brandName)
                putExtra("SELECTED_COUNT", selectedCountTextView)
                putExtra("SELECTED_ITEMS", ArrayList(selectedItemsList)) // Pass selected items as a list
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
}
