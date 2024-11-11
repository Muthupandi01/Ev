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
import com.example.heptotech.adapters.AllinOneAdapterAccess
import com.example.heptotech.adapters.AllinOneAdapterChargeStation
import com.example.heptotech.adapters.AllinOneAdapterConnecter
import com.example.heptotech.adapters.AllinOneAdapterLocation
import com.example.heptotech.adapters.AllinOneAdapterMultipleDevice
import com.example.heptotech.adapters.AllinOneAdapterNet
import com.example.heptotech.adapters.AllinOneAdapterRating
import com.example.heptotech.bean_dataclass.Filter
import com.example.heptotech.bean_dataclass.FilterAccess
import com.example.heptotech.bean_dataclass.FilterChargeStation
import com.example.heptotech.bean_dataclass.FilterConnecter
import com.example.heptotech.bean_dataclass.FilterLocation
import com.example.heptotech.bean_dataclass.FilterMultidevice
import com.example.heptotech.bean_dataclass.FilterNet
import com.example.heptotech.bean_dataclass.Filterrating

class AllinOneFilter : AppCompatActivity() {

    private lateinit var allinOneAdapter: AllinOneAdapter
    private lateinit var allinOneAdapterConnecter: AllinOneAdapterConnecter
    private lateinit var allinOneAdapterNet: AllinOneAdapterNet
    private lateinit var allinOneAdapterLocation: AllinOneAdapterLocation
    private lateinit var allinOneAdapterAccess: AllinOneAdapterAccess
    private lateinit var allinOneAdapterRating: AllinOneAdapterRating
    private lateinit var allinOneAdapterMultipleDevice: AllinOneAdapterMultipleDevice
    private lateinit var allinOneAdapterChargeStation: AllinOneAdapterChargeStation

    private lateinit var filterMainRec: RecyclerView
    private lateinit var header: TextView
    private lateinit var btnSubmit: TextView
    private var selectedCount = 0
    private var selectedItemsList = mutableListOf<Filter>()

    private var selectedItemsListConnecter = mutableListOf<FilterConnecter>()
    private var selectedItemsListNet = mutableListOf<FilterNet>()
    private var selectedItemsListLocation = mutableListOf<FilterLocation>()
    private var selectedItemsListAccess = mutableListOf<FilterAccess>()
    private var selectedItemsListRating = mutableListOf<Filterrating>()
    private var selectedItemsListMultiple = mutableListOf<FilterMultidevice>()
    private var selectedItemsListChargeStation = mutableListOf<FilterChargeStation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allin_one_filter)

        filterMainRec = findViewById(R.id.allinrec)
        header = findViewById(R.id.header)
        btnSubmit = findViewById(R.id.btn_submit)

        val brandName = intent.getStringExtra("BRAND_NAME")
        header.text = brandName

//        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
//        selectedCount = sharedPref.getInt("SELECTED_COUNT", 0)
//        sharedPref.edit().clear().apply()

        loadUserSelections(brandName)
       // val brandList: List<Filter> = getBrandList(brandName)


        when (brandName) {
            "Connnector types" -> {
                val brandListConnecter: List<FilterConnecter> = getBrandListConnecter(brandName)
                setupAdapterConnecter(brandListConnecter,brandName)
            }
            "Networks" -> {
                val brandListNet: List<FilterNet> = getBrandListNet(brandName)
                setupAdapterNet(brandListNet,brandName)
            }
            "Location types" -> {
                val brandListLocation: List<FilterLocation> = getBrandListLocation(brandName)
                setupAdapterLocation(brandListLocation,brandName)
            }
            "Access" -> {
                val brandListAccess: List<FilterAccess> = getBrandListAccess(brandName)
                setupAdapterAcccess(brandListAccess,brandName)
            }
            "User rating" -> {
                val brandListRating: List<Filterrating> = getBrandListRating(brandName)
                setupAdapterRating(brandListRating,brandName)
            }
            "Multiple devices" -> {
                val brandListMultidevice: List<FilterMultidevice> = getBrandListMultiple(brandName)
                setupAdapterMultiple(brandListMultidevice,brandName)
            }
            "Charge Station" -> {
                val brandListChargeStation: List<FilterChargeStation> = getBrandListChargeStation(brandName)
                setupAdapterCharge(brandListChargeStation,brandName)
            }
        }

        setupSubmitButton(brandName)
    }

    private fun setupAdapterCharge(brandList: List<FilterChargeStation>, brandName: String) {
        allinOneAdapterChargeStation = AllinOneAdapterChargeStation(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListChargeStation = selectedItems.toMutableList()
        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterChargeStation
    }

    private fun setupAdapterMultiple(brandList: List<FilterMultidevice>, brandName: String) {
        allinOneAdapterMultipleDevice = AllinOneAdapterMultipleDevice(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListMultiple = selectedItems.toMutableList()

        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterMultipleDevice
    }

    private fun setupAdapterRating(brandList: List<Filterrating>, brandName: String) {
        allinOneAdapterRating = AllinOneAdapterRating(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListRating = selectedItems.toMutableList()

        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterRating
    }

    private fun setupAdapterAcccess(brandList: List<FilterAccess>, brandName: String) {
        allinOneAdapterAccess = AllinOneAdapterAccess(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListAccess = selectedItems.toMutableList()

        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterAccess
    }

    private fun setupAdapterLocation(brandList: List<FilterLocation>, brandName: String) {
        allinOneAdapterLocation = AllinOneAdapterLocation(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListLocation = selectedItems.toMutableList()

        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterLocation
    }

    private fun setupAdapterNet(brandList: List<FilterNet>, brandName: String) {
        allinOneAdapterNet = AllinOneAdapterNet(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListNet = selectedItems.toMutableList()

        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterNet

    }

    private fun setupAdapterConnecter(brandList: List<FilterConnecter>, brandName: String) {
        allinOneAdapterConnecter = AllinOneAdapterConnecter(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsListConnecter = selectedItems.toMutableList()

        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapterConnecter
    }


    private fun loadUserSelections(brandName: String?) {
        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)

        // Clear the selection lists
//        selectedItemsList.clear()
//        selectedItemsListConnecter.clear()
//        selectedItemsListNet.clear()
//        selectedItemsListLocation.clear()
//        selectedItemsListAccess.clear()
//        selectedItemsListRating.clear()
//        selectedItemsListMultiple.clear()
//        selectedItemsListChargeStation.clear()

        // Load selected items based on brand name
        when (brandName) {
            "Connnector types" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_CONNECTOR", emptySet())
                selectedItemsListConnecter = selectedItemsSet?.map { FilterConnecter(name = it) }?.toMutableList() ?: mutableListOf()
            }
            "Networks" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_NETWORK", emptySet())
                selectedItemsListNet = selectedItemsSet?.map { FilterNet(name = it) }?.toMutableList() ?: mutableListOf()
            }
            "Location types" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_LOCATION", emptySet())
                selectedItemsListLocation = selectedItemsSet?.map { FilterLocation(name = it) }?.toMutableList() ?: mutableListOf()
            }
            "Access" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_ACCESS", emptySet())
                selectedItemsListAccess = selectedItemsSet?.map { FilterAccess(name = it) }?.toMutableList() ?: mutableListOf()
            }
            "User rating" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_RATING", emptySet())
                selectedItemsListRating = selectedItemsSet?.map { Filterrating(name = it) }?.toMutableList() ?: mutableListOf()
            }
            "Multiple devices" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_MULTIPLE", emptySet())
                selectedItemsListMultiple = selectedItemsSet?.map { FilterMultidevice(name = it) }?.toMutableList() ?: mutableListOf()
            }
            "Charge Station" -> {
                val selectedItemsSet = sharedPref.getStringSet("SELECTED_CHARGESTATION", emptySet())
                selectedItemsListChargeStation = selectedItemsSet?.map { FilterChargeStation(name = it) }?.toMutableList() ?: mutableListOf()
            }
        }
        Log.d("loadUserSelections", "Loaded selections for $brandName")
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

    private fun getBrandListConnecter(brandName: String?): List<FilterConnecter> {
        return when (brandName) {
            "Connnector types" -> listOf(
                FilterConnecter(0, "DC", isHeader = true),
                FilterConnecter(R.drawable.connecttype_ev, "CCS Combo"),
                FilterConnecter(R.drawable.connecttype_ev, "Tesla CCS Combo"),
                FilterConnecter(R.drawable.connecttype_ev, "Tesla Type 2"),
                FilterConnecter(R.drawable.connecttype_ev, "CHAdeMO"),
                FilterConnecter(0, "AC", isHeader = true),
                FilterConnecter(R.drawable.connecttype_ev, "Type 2 Mennekes"),
                FilterConnecter(R.drawable.connecttype_ev, "Type 1 Yazaki"),
                FilterConnecter(R.drawable.connecttype_ev, "Tesla Tpe 1"),
                FilterConnecter(R.drawable.connecttype_ev, "3-Pin"),
                FilterConnecter(R.drawable.connecttype_ev, "Commando")
            )
            else -> listOf()
        }





    }
    private fun getBrandListNet(brandName: String?): List<FilterNet> {
        return when (brandName) {
            "Networks" -> listOf(
                FilterNet(R.drawable.globe, "Alfa Power"),
                FilterNet(R.drawable.globe, "Applegreen Electric"),
                FilterNet(R.drawable.globe, "Char.gy"),
                FilterNet(R.drawable.globe, "Connected Kerb"),
                FilterNet(R.drawable.globe, "ESB Energy"),
                FilterNet(R.drawable.globe, "Evyve"),
                FilterNet(R.drawable.globe, "Fastned"),
                FilterNet(R.drawable.globe, "FOR-EV"),
                FilterNet(R.drawable.globe, "GeniePoint"),
                FilterNet(R.drawable.globe, "Mer"),
                FilterNet(R.drawable.globe, "MFG EV Power"),
                FilterNet(R.drawable.globe, "Osprey"),
                FilterNet(R.drawable.globe, "Pogo Charge")
            )
            else -> listOf()
        }





    }
    private fun getBrandListLocation(brandName: String?): List<FilterLocation> {
        return when (brandName) {
            "Location types" -> listOf(
                FilterLocation(R.drawable.baseline_location_on_24, "Accomodation"),
                FilterLocation(R.drawable.baseline_location_on_24, "Car park"),
                FilterLocation(R.drawable.baseline_location_on_24, "Dealership"),
                FilterLocation(R.drawable.baseline_location_on_24, "Education"),
                FilterLocation(R.drawable.baseline_location_on_24, "Fuel Forecourt"),
                FilterLocation(R.drawable.baseline_location_on_24, "Health Services"),
                FilterLocation(R.drawable.baseline_location_on_24, "Home"),
                FilterLocation(R.drawable.baseline_location_on_24, "Leisure"),
                FilterLocation(R.drawable.baseline_location_on_24, "Motorway Services"),
                FilterLocation(R.drawable.baseline_location_on_24, "On-Street"),
                FilterLocation(R.drawable.baseline_location_on_24, "Other"),
                FilterLocation(R.drawable.baseline_location_on_24, "Park and Ride"),
                FilterLocation(R.drawable.baseline_location_on_24, "Public Services"),
                FilterLocation(R.drawable.baseline_location_on_24, "Restaurent/Pub/Cafe"),
                FilterLocation(R.drawable.baseline_location_on_24, "Retail Car Park"),
                FilterLocation(R.drawable.baseline_location_on_24, "Supermarket"),
                FilterLocation(R.drawable.baseline_location_on_24, "Travel Interchange"),
                FilterLocation(R.drawable.baseline_location_on_24, "Workplace Car Park")
            )
            else -> listOf()
        }





    }
    private fun getBrandListAccess(brandName: String?): List<FilterAccess> {
        return when (brandName) {
            "Access" -> listOf(
                FilterAccess(R.drawable.access_ev, "24 hours access"),
                FilterAccess(R.drawable.access_ev, "Public access"),
                FilterAccess(R.drawable.access_ev, "No physical restrictions"),
                FilterAccess(R.drawable.access_ev, "Hide Zap-Home points"),
                FilterAccess(R.drawable.access_ev, "Hide Zap-Work points"),
                FilterAccess(R.drawable.access_ev, "Show Taxi Only")
            )
            else -> listOf()
        }





    }
    private fun getBrandListRating(brandName: String?): List<Filterrating> {
        return when (brandName) {
            "User rating" -> listOf(
                Filterrating(R.drawable.baseline_star_24, "2 stars or more"),
                Filterrating(R.drawable.baseline_star_24, "3 stars or more"),
                Filterrating(R.drawable.baseline_star_24, "4 stars or more")
            )
            else -> listOf()
        }





    }
    private fun getBrandListMultiple(brandName: String?): List<FilterMultidevice> {
        return when (brandName) {
            "Multiple devices" -> listOf(
                FilterMultidevice(R.drawable.multi_ev, "2 devices or more"),
                FilterMultidevice(R.drawable.multi_ev, "3 devices or more"),
                FilterMultidevice(R.drawable.multi_ev, "4 devices or more"),
                FilterMultidevice(R.drawable.multi_ev, "5 devices or more"),
                FilterMultidevice(R.drawable.multi_ev, "6 devices or more")
            )
            else -> listOf()
        }





    }
    private fun getBrandListChargeStation(brandName: String?): List<FilterChargeStation> {
        return when (brandName) {
            "Charge Station" -> listOf(
                FilterChargeStation(R.drawable.baseline_fiber_new_24, "Residential"),
                FilterChargeStation(R.drawable.baseline_fiber_new_24, "Corporate"),
                FilterChargeStation(R.drawable.baseline_fiber_new_24, "Public")
            )
            else -> listOf()
        }
    }

    private fun setupAdapter(brandList: List<Filter>, brandName: String?) {
        allinOneAdapter = AllinOneAdapter(brandList, this) { selectedCount, selectedItems ->
            this.selectedCount = selectedCount
            selectedItemsList = selectedItems.toMutableList()
        }
        filterMainRec.layoutManager = LinearLayoutManager(this)
        filterMainRec.adapter = allinOneAdapter
    }

    private fun setupSubmitButton(brandName: String?) {

        btnSubmit.setOnClickListener {
            saveUserSelections(brandName)
            val resultIntent = Intent().apply {
                putExtra("BRAND_NAME", brandName)
                putExtra("SELECTED_COUNT", selectedCount)
                putExtra("SELECTED_ITEMS_CO", ArrayList(selectedItemsListConnecter))
                putExtra("SELECTED_ITEMS_NET", ArrayList(selectedItemsListNet))
                putExtra("SELECTED_ITEMS_LOC", ArrayList(selectedItemsListLocation))
                putExtra("SELECTED_ITEMS_ACC", ArrayList(selectedItemsListAccess))
                putExtra("SELECTED_ITEMS_RAT", ArrayList(selectedItemsListRating))
                putExtra("SELECTED_ITEMS_MULTI", ArrayList(selectedItemsListMultiple))
                putExtra("SELECTED_ITEMS_CHARGE", ArrayList(selectedItemsListChargeStation))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    private fun saveUserSelections(brandName: String?) {
        val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
        with(sharedPref.edit()) {
            when (brandName) {
                "Connnector types" -> putStringSet("SELECTED_CONNECTOR", selectedItemsListConnecter.map { it.name }.toSet())
                "Networks" -> putStringSet("SELECTED_NETWORK", selectedItemsListNet.map { it.name }.toSet())
                "Location types" -> putStringSet("SELECTED_LOCATION", selectedItemsListLocation.map { it.name }.toSet())
                "Access" -> putStringSet("SELECTED_ACCESS", selectedItemsListAccess.map { it.name }.toSet())
                "User rating" -> putStringSet("SELECTED_RATING", selectedItemsListRating.map { it.name }.toSet())
                "Multiple devices" -> putStringSet("SELECTED_MULTIPLE", selectedItemsListMultiple.map { it.name }.toSet())
                "Charge Station" -> putStringSet("SELECTED_CHARGESTATION", selectedItemsListChargeStation.map { it.name }.toSet())
            }
            apply()
        }
        Log.d("saveUserSelections", "Saved selections for $brandName")
    }

}
