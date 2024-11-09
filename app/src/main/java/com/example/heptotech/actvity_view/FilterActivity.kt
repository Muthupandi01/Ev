package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    lateinit var tick:TextView
    lateinit var back:CardView
    private lateinit var filterMainAdapter: FilterMainAdapter
    private lateinit var filerMainrec: RecyclerView
    private val FilterList = listOf(
        Filter(R.drawable.connecttype_ev, "Connnector types"),
        Filter(R.drawable.globe, "Networks"),
        Filter(R.drawable.baseline_location_on_24, "Location types"),
        Filter(R.drawable.access_ev, "Access"),
        Filter(R.drawable.baseline_star_24, "User rating"),
        Filter(R.drawable.multi_ev, "Multiple devices"),
        Filter(R.drawable.baseline_fiber_new_24, "Charge Station")

    )
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
            startActivity(Intent(this, PublicStation::class.java))
        }

        tick.setOnClickListener {
            reset.isVisible=true
            tick.isVisible=false
        }
        reset.setOnClickListener {
            reset.isVisible=false
            tick.isVisible=true
            val sharedPref = getSharedPreferences("USER_SELECTIONS", MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            filterMainAdapter = FilterMainAdapter(FilterList,this,this)
            filerMainrec.layoutManager = LinearLayoutManager(this)
            filerMainrec.adapter = filterMainAdapter

        }

        filterMainAdapter = FilterMainAdapter(FilterList,this,this)
        filerMainrec.layoutManager = LinearLayoutManager(this)
        filerMainrec.adapter = filterMainAdapter
        val selectedRangeText = findViewById<TextView>(R.id.selectedRangeText)
        val seekBarContainer = findViewById<FrameLayout>(R.id.seekBarContainer)
        val customSeekBar = FixedRangeSeekBar(this).apply {
            setOnRangeChangedListener { min, max ->
                // Update selected range display in real-time
                val selectedRange = getSelectedRange()
                tick.isVisible=true
                reset.isVisible=false
                //selectedRangeText.text = "Power type - "+"Range: ${selectedRange.first} to ${selectedRange.second}"
            }
        }
        val initialRange = customSeekBar.getSelectedRange()
       // selectedRangeText.text = "Power type - "+"Range: ${initialRange.first} to ${initialRange.second}"
        seekBarContainer.addView(customSeekBar)
    }

    override fun onItemClicked(brandName: String, countTextView: TextView) {
        val intent = Intent(this, AllinOneFilter::class.java)
        intent.putExtra("BRAND_NAME", brandName)

        // Retrieve previously saved items for this brand
        val sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE)
        val selectedItems = sharedPreferences.getStringSet(brandName, emptySet()) ?: emptySet()

        // Pass the saved selected items to the intent as a string array list
        intent.putStringArrayListExtra("SELECTED_ITEMS", ArrayList(selectedItems))

        startActivityForResult(intent, REQUEST_CODE_ALL_IN_ONE_FILTER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ALL_IN_ONE_FILTER && resultCode == RESULT_OK) {
            // Retrieve the updated count and selected items from AllinOneFilter
            val brandName = data?.getStringExtra("BRAND_NAME")
            val selectedCount = data?.getIntExtra("SELECTED_COUNT", 0) ?: 0
            val selectedItems = data?.getStringArrayListExtra("SELECTED_ITEMS") ?: arrayListOf()

            val selectedItemsListConnecter = data?.getStringArrayListExtra("SELECTED_ITEMS_CO") ?: arrayListOf()
            val selectedItemsListNet = data?.getStringArrayListExtra("SELECTED_ITEMS_NET") ?: arrayListOf()
            val selectedItemsListLocation = data?.getStringArrayListExtra("SELECTED_ITEMS_LOC") ?: arrayListOf()
            val selectedItemsListAccess = data?.getStringArrayListExtra("SELECTED_ITEMS_ACC") ?: arrayListOf()
            val selectedItemsListRating = data?.getStringArrayListExtra("SELECTED_ITEMS_RAT") ?: arrayListOf()
            val selectedItemsListMultiple = data?.getStringArrayListExtra("SELECTED_ITEMS_MULTI") ?: arrayListOf()
            val selectedItemsListChargeStation = data?.getStringArrayListExtra("SELECTED_ITEMS_CHARGE") ?: arrayListOf()

            when (brandName) {
                "Connnector types" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListConnecter.size)
                }
                "Networks" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListNet.size)
                }
                "Location types" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListLocation.size)
                }
                "Access" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListAccess.size)
                }
                "User rating" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListRating.size)
                }
                "Multiple devices" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListMultiple.size)
                }
                "Charge Station" -> {
                    filterMainAdapter.updateCount(brandName, selectedItemsListChargeStation.size)
                }
            }




            // Save selected items to SharedPreferences
             //saveSelectedItems(brandName, selectedItems)
        }
    }


}