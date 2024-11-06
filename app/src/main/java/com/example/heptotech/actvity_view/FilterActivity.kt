package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.FilterMainAdapter
import com.example.heptotech.bean_dataclass.Filter
import com.example.heptotech.customclass.FixedRangeSeekBar

class FilterActivity : AppCompatActivity(), FilterMainAdapter.OnItemClickListener {
    lateinit var reset:TextView
    lateinit var tick:ImageView
    lateinit var back:ImageView
    private lateinit var filterMainAdapter: FilterMainAdapter
    private lateinit var filerMainrec: RecyclerView
    private val FilterList = listOf(
        Filter(R.drawable.connecttype_ev, "Connnector types"),
        Filter(R.drawable.connecttype_ev, "Networks"),
        Filter(R.drawable.location_pin__1_ev, "Location types"),
        Filter(R.drawable.connecttype_ev, "Access"),
        Filter(R.drawable.tesla_ev, "User rating"),
        Filter(R.drawable.connecttype_ev, "Multiple devices"),
        Filter(R.drawable.location_pin__1_ev, "Charge Station")

    )
    companion object {
        const val REQUEST_CODE_ALL_IN_ONE_FILTER = 1001
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        tick=findViewById(R.id.tick)
        reset=findViewById(R.id.reset)
        filerMainrec=findViewById(R.id.filerMainrec)



        filterMainAdapter = FilterMainAdapter(FilterList,this)
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
        startActivityForResult(intent, REQUEST_CODE_ALL_IN_ONE_FILTER)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ALL_IN_ONE_FILTER && resultCode == RESULT_OK) {
            // Retrieve the updated count from AllinOneFilter
            val brandName = data?.getStringExtra("BRAND_NAME")
            val selectedCount = data?.getIntExtra("SELECTED_COUNT", 0) ?: 0

            // Update the specific item count in the adapter
            filterMainAdapter.updateCount(brandName, selectedCount)
        }
    }

}