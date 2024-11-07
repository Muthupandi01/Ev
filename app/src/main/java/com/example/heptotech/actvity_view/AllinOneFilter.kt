package com.example.heptotech.activity_view

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
    private lateinit var filterMainRec: RecyclerView
    private lateinit var header: TextView
    private lateinit var btnSubmit: TextView
    private var selectedCount = 0
    private var selectedItemsList = mutableListOf<Filter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allin_one_filter)

        filterMainRec = findViewById(R.id.allinrec)
        header = findViewById(R.id.header)
        btnSubmit = findViewById(R.id.btn_submit)

        val brandName = intent.getStringExtra("BRAND_NAME")
        header.text = brandName

        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
        selectedCount = sharedPref.getInt("SELECTED_COUNT", 0)
        sharedPref.edit().clear().apply()

        loadUserSelections()
        val brandList: List<Filter> = getBrandList(brandName)
        setupAdapter(brandList)
        setupSubmitButton(brandName)
    }

    private fun loadUserSelections() {
        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
        selectedCount = sharedPref.getInt("SELECTED_COUNT", 0)
        val selectedItemsSet = sharedPref.getStringSet("SELECTED_ITEMS", emptySet())
        selectedItemsList = selectedItemsSet?.map { Filter(name = it, isSelected = true) }?.toMutableList() ?: mutableListOf()

        // Now update the isSelected property of filters based on user selections
        val brandName = intent.getStringExtra("BRAND_NAME")
        val brandList = getBrandList(brandName) // This will be the list of all possible filters

        brandList.forEach { filter ->
            filter.isSelected = selectedItemsList.any { it.name == filter.name }
        }

        Log.d("selectedItemsList", "Loaded One: $brandName - $selectedItemsList")
        //Log.d("selectedItemsList", "Loaded Two: $brandName")




    }


    private fun getBrandList(brandName: String?): List<Filter> {
        return when (brandName) {
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
                Filter(R.drawable.globe, "Alfa Power"),
                Filter(R.drawable.globe, "Applegreen Electric"),
                Filter(R.drawable.globe, "Char.gy"),
                Filter(R.drawable.globe, "Connected Kerb"),
                Filter(R.drawable.globe, "ESB Energy"),
                Filter(R.drawable.globe, "Evyve"),
                Filter(R.drawable.globe, "Fastned"),
                Filter(R.drawable.globe, "FOR-EV"),
                Filter(R.drawable.globe, "GeniePoint"),
                Filter(R.drawable.globe, "Mer"),
                Filter(R.drawable.globe, "MFG EV Power"),
                Filter(R.drawable.globe, "Osprey"),
                Filter(R.drawable.globe, "Pogo Charge")
            )
            "Location types" -> listOf(
                Filter(R.drawable.baseline_location_on_24, "Accomodation"),
                Filter(R.drawable.baseline_location_on_24, "Car park"),
                Filter(R.drawable.baseline_location_on_24, "Dealership"),
                Filter(R.drawable.baseline_location_on_24, "Education"),
                Filter(R.drawable.baseline_location_on_24, "Fuel Forecourt"),
                Filter(R.drawable.baseline_location_on_24, "Health Services"),
                Filter(R.drawable.baseline_location_on_24, "Home"),
                Filter(R.drawable.baseline_location_on_24, "Leisure"),
                Filter(R.drawable.baseline_location_on_24, "Motorway Services"),
                Filter(R.drawable.baseline_location_on_24, "On-Street"),
                Filter(R.drawable.baseline_location_on_24, "Other"),
                Filter(R.drawable.baseline_location_on_24, "Park and Ride"),
                Filter(R.drawable.baseline_location_on_24, "Public Services"),
                Filter(R.drawable.baseline_location_on_24, "Restaurent/Pub/Cafe"),
                Filter(R.drawable.baseline_location_on_24, "Retail Car Park"),
                Filter(R.drawable.baseline_location_on_24, "Supermarket"),
                Filter(R.drawable.baseline_location_on_24, "Travel Interchange"),
                Filter(R.drawable.baseline_location_on_24, "Workplace Car Park")
            )
            "Access" -> listOf(
                Filter(R.drawable.access_ev, "24 hours access"),
                Filter(R.drawable.access_ev, "Public access"),
                Filter(R.drawable.access_ev, "No physical restrictions"),
                Filter(R.drawable.access_ev, "Hide Zap-Home points"),
                Filter(R.drawable.access_ev, "Hide Zap-Work points"),
                Filter(R.drawable.access_ev, "Show Taxi Only")
            )
            "User rating" -> listOf(
                Filter(R.drawable.baseline_star_24, "2 stars or more"),
                Filter(R.drawable.baseline_star_24, "3 stars or more"),
                Filter(R.drawable.baseline_star_24, "4 stars or more")
            )
            "Multiple devices" -> listOf(
                Filter(R.drawable.multi_ev, "2 devices or more"),
                Filter(R.drawable.multi_ev, "3 devices or more"),
                Filter(R.drawable.multi_ev, "4 devices or more"),
                Filter(R.drawable.multi_ev, "5 devices or more"),
                Filter(R.drawable.multi_ev, "6 devices or more")
            )
            else -> listOf(
                Filter(R.drawable.baseline_fiber_new_24, "Residential"),
                Filter(R.drawable.baseline_fiber_new_24, "Corporate"),
                Filter(R.drawable.baseline_fiber_new_24, "Public")
            )
        }





    }

    private fun setupAdapter(brandList: List<Filter>) {
        allinOneAdapter = AllinOneAdapter(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsList = selectedItems.toMutableList()
        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapter
    }

    private fun setupSubmitButton(brandName: String?) {
        btnSubmit.setOnClickListener {
            saveUserSelections()
            val resultIntent = Intent().apply {
                putExtra("BRAND_NAME", brandName)
                putExtra("SELECTED_COUNT", selectedCount)
                putExtra("SELECTED_ITEMS", ArrayList(selectedItemsList))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    private fun saveUserSelections() {
        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("SELECTED_COUNT", selectedCount)
            putStringSet("SELECTED_ITEMS", selectedItemsList.map { it.name }.toSet())
            apply()
        }
        Log.d("saveUserSelections", "Saved to SharedPreferences: $selectedItemsList")
    }

}
