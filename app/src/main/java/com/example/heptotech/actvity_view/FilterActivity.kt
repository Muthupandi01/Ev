package com.example.heptotech.actvity_view

import android.R.attr.name
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.activity_view.AllinOneFilter
import com.example.heptotech.adapters.FilterMainAdapter
import com.example.heptotech.bean_dataclass.Filter
import com.example.heptotech.bean_dataclass.FilterAccess
import com.example.heptotech.bean_dataclass.FilterChargeStation
import com.example.heptotech.bean_dataclass.FilterConnecter
import com.example.heptotech.bean_dataclass.FilterLocation
import com.example.heptotech.bean_dataclass.FilterMultidevice
import com.example.heptotech.bean_dataclass.FilterNet
import com.example.heptotech.bean_dataclass.Filterrating
import com.example.heptotech.customclass.FixedRangeSeekBar


class FilterActivity : AppCompatActivity(), FilterMainAdapter.OnItemClickListener {
    lateinit var reset:TextView
    lateinit var tick:ImageView
    lateinit var back:CardView

    private var selectedItemsListConnecter = mutableListOf<FilterConnecter>()
    private var selectedItemsListNet = mutableListOf<FilterNet>()
    private var selectedItemsListLocation = mutableListOf<FilterLocation>()
    private var selectedItemsListAccess = mutableListOf<FilterAccess>()
    private var selectedItemsListRating = mutableListOf<Filterrating>()
    private var selectedItemsListMultiple = mutableListOf<FilterMultidevice>()
    private var selectedItemsListChargeStation = mutableListOf<FilterChargeStation>()

    private lateinit var filterMainAdapter: FilterMainAdapter
    private lateinit var filerMainrec: RecyclerView
    private val FilterList = listOf(
        Filter(R.drawable.connecttype_ev, "Connnector types"),
        Filter(R.drawable.globe, "Networks"),
        Filter(R.drawable.baseline_location_on_24, "Location types"),
        Filter(R.drawable.access_ev, "Access"),
        Filter(R.drawable.baseline_star_24, "User rating"),
        Filter(R.drawable.multi_ev, "Multiple devices"),
        Filter(R.drawable.baseline_fiber_new_24, "Charge Station"))

    var check="I"
    var tickI="I"
    companion object {
        const val REQUEST_CODE_ALL_IN_ONE_FILTER = 1001
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        tick=findViewById(R.id.tick)
        back=findViewById(R.id.back)
        reset=findViewById(R.id.reset)
        filerMainrec=findViewById(R.id.filerMainrec)
        back.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            tickI= sharedPreferences.getString("tickI","C").toString()
            if (tickI.equals("I")){
                val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
                sharedPref.edit().clear().apply()
            }
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }

//

        tick.setOnClickListener {
           // reset.isVisible=true
            tick.isVisible=false
            tickI="C"


            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()
                myEdit.putString("tickI", tickI)

                myEdit.apply()


            //getseekbar()
        }

        reset.setOnClickListener {
            reset.isVisible=false
           // tick.isVisible=true
            val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            filterMainAdapter = FilterMainAdapter(FilterList,this,this)
            filerMainrec.layoutManager = LinearLayoutManager(this)
            filerMainrec.adapter = filterMainAdapter
            check="C"
            getseekbar()
        }

        filterMainAdapter = FilterMainAdapter(FilterList,this,this)
        filerMainrec.layoutManager = LinearLayoutManager(this)
        filerMainrec.adapter = filterMainAdapter
        getseekbar()

    }

    private fun getseekbar() {
        val selectedRangeText = findViewById<TextView>(R.id.selectedRangeText)
        val seekBarContainer = findViewById<FrameLayout>(R.id.seekBarContainer)
        val customSeekBar = FixedRangeSeekBar(this).apply {
            setOnRangeChangedListener { min, max ->
                // Update selected range display in real-time
                val selectedRange = getSelectedRange()
                tick.isVisible=true
                reset.isVisible=false

                // selectedRangeText.text = "Power type - Range: ${selectedRange.first} to ${selectedRange.second}"

                // Store data into SharedPreferences
//                val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
//                val myEdit = sharedPreferences.edit()
//                myEdit.putString("min", selectedRange.first)
//                myEdit.putString("max", selectedRange.second)
//                myEdit.apply()
            }
        }

        // Load saved values when the activity opens
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        tickI= sharedPreferences.getString("tickI","").toString()
        val savedMin = sharedPreferences.getString("min", null)?.let { customSeekBar.labels.indexOf(it) } ?: 0
        val savedMax = customSeekBar.labels.size - 1 // assuming fixed max is last element

        if (check.equals("I")){
                if (tickI.equals("C")){
                    customSeekBar.setSelectedMin(savedMin)
                    reset.isVisible=true

                }else{
                    val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
                    sharedPref.edit().clear().apply()
                    filterMainAdapter = FilterMainAdapter(FilterList,this,this)
                    filerMainrec.layoutManager = LinearLayoutManager(this)
                    filerMainrec.adapter = filterMainAdapter
                }



           // reset.isVisible=true
            // Ensure the seekbar is added only if not already added
            if (seekBarContainer.childCount == 0) {
                seekBarContainer.addView(customSeekBar)
            }
        }else{
            seekBarContainer.removeAllViews()
            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.clear().apply()
            seekBarContainer.addView(customSeekBar)
        }
        // Set initial range to saved range

    }


    private fun getseekbars() {
        val selectedRangeText = findViewById<TextView>(R.id.selectedRangeText)
        val seekBarContainer = findViewById<FrameLayout>(R.id.seekBarContainer)
        val customSeekBar = FixedRangeSeekBar(this).apply {
            setOnRangeChangedListener { min, max ->
                // Update selected range display in real-time
                val selectedRange = getSelectedRange()
                tick.isVisible=true
                reset.isVisible=false

                // Storing data into SharedPreferences
                val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()
                myEdit.putString("min", selectedRange.first.toString())
                myEdit.putString("max", selectedRange.second.toString())
                myEdit.commit()
               // val savedMin = sharedPreferences.getString("min", null)?.toIntOrNull() ?: 0
               // selectedRangeText.text = "Power type - "+"Range: ${selectedRange.first} to ${selectedRange.second}"
            }
        }


        val initialRange = customSeekBar.getSelectedRange()
        // selectedRangeText.text = "Power type - "+"Range: ${initialRange.first} to ${initialRange.second}"
        if (check.equals("I")){
            seekBarContainer.addView(customSeekBar)
        }else{
            seekBarContainer.removeAllViews()
            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.clear().apply()
            seekBarContainer.addView(customSeekBar)
        }
    }

    override fun onItemClicked(brandName: String, countTextView: TextView) {
        val intent = Intent(this, AllinOneFilter::class.java)
        intent.putExtra("BRAND_NAME", brandName)

        // Retrieve previously saved items for this brand
        val sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE)
        val selectedItems = sharedPreferences.getStringSet(brandName, emptySet()) ?: emptySet()

        // Pass the saved selected items to the intent as a string array list
        intent.putStringArrayListExtra("SELECTED_ITEMS", ArrayList(selectedItems))

        allInOneFilterLauncher.launch(intent)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    // Register the Activity Result Launcher
    private val allInOneFilterLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val brandName = data?.getStringExtra("BRAND_NAME")
            val selectedCount = data?.getIntExtra("SELECTED_COUNT", 0) ?: 0
            val selectedItemsListConnecters = data?.getStringArrayListExtra("SELECTED_ITEMS_CO") ?: arrayListOf()
            val selectedItemsListNets = data?.getStringArrayListExtra("SELECTED_ITEMS_NET") ?: arrayListOf()
            val selectedItemsListLocations = data?.getStringArrayListExtra("SELECTED_ITEMS_LOC") ?: arrayListOf()
            val selectedItemsListAccesss = data?.getStringArrayListExtra("SELECTED_ITEMS_ACC") ?: arrayListOf()
            val selectedItemsListRatings = data?.getStringArrayListExtra("SELECTED_ITEMS_RAT") ?: arrayListOf()
            val selectedItemsListMultiples = data?.getStringArrayListExtra("SELECTED_ITEMS_MULTI") ?: arrayListOf()
            val selectedItemsListChargeStations = data?.getStringArrayListExtra("SELECTED_ITEMS_CHARGE") ?: arrayListOf()

            tick.isVisible = true
            reset.isVisible = false
//            when (brandName) {
//                "Connnector types" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListConnecters.size)
//                }
//                "Networks" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListNets.size)
//                }
//                "Location types" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListLocations.size)
//                }
//                "Access" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListAccesss.size)
//                }
//                "User rating" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListRatings.size)
//                }
//                "Multiple devices" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListMultiples.size)
//                }
//                "Charge Station" -> {
//                    filterMainAdapter.updateCount(brandName, selectedItemsListChargeStations.size)
//                }
//            }


            val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
            when (brandName) {
                "Connnector types" -> {
                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_CONNECTOR", emptySet())
                    selectedItemsListConnecter = selectedItemsSet?.map { FilterConnecter(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListConnecter.size)
                }
                "Networks" -> {
                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_NETWORK", emptySet())
                    selectedItemsListNet = selectedItemsSet?.map { FilterNet(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListNet.size)

                }
                "Location types" -> {
                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_LOCATION", emptySet())
                    selectedItemsListLocation = selectedItemsSet?.map { FilterLocation(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListLocation.size)

                }
                "Access" -> {
                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_ACCESS", emptySet())
                    selectedItemsListAccess = selectedItemsSet?.map { FilterAccess(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListAccess.size)

                }
                "User rating" -> {
                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_RATING", emptySet())
                    selectedItemsListRating = selectedItemsSet?.map { Filterrating(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListRating.size)

                }
                "Multiple devices" -> {
                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_MULTIPLE", emptySet())
                    selectedItemsListMultiple = selectedItemsSet?.map { FilterMultidevice(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListMultiple.size)

                }
                "Charge Station" -> {

                    val selectedItemsSet = sharedPref.getStringSet("SELECTED_CHARGESTATION", emptySet())
                    selectedItemsListChargeStation = selectedItemsSet?.map { FilterChargeStation(name = it) }?.toMutableList() ?: mutableListOf()
                    filterMainAdapter.updateCount(brandName, selectedItemsListChargeStation.size)

                }
            }
        }
    }

    // Launch the Activity
    fun launchAllInOneFilter() {
        val intent = Intent(this, AllinOneFilter::class.java)
        allInOneFilterLauncher.launch(intent)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        tickI= sharedPreferences.getString("tickI","C").toString()
        if (tickI.equals("I")){
            val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
            sharedPref.edit().clear().apply()

        }



        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()


    }

}