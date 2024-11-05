package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.heptotech.R
import com.example.heptotech.customclass.FixedRangeSeekBar
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar

class FilterActivity : AppCompatActivity() {
    lateinit var reset:TextView
    lateinit var tick:ImageView
    lateinit var back:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        tick=findViewById(R.id.tick)
        reset=findViewById(R.id.reset)

        val selectedRangeText = findViewById<TextView>(R.id.selectedRangeText)
        val seekBarContainer = findViewById<FrameLayout>(R.id.seekBarContainer)
        val customSeekBar = FixedRangeSeekBar(this).apply {
            setOnRangeChangedListener { min, max ->
                // Update selected range display in real-time
                val selectedRange = getSelectedRange()
                tick.isVisible=true
                reset.isVisible=false
                selectedRangeText.text = "Power type - "+"Range: ${selectedRange.first} to ${selectedRange.second}"
            }
        }
        val initialRange = customSeekBar.getSelectedRange()
        selectedRangeText.text = "Power type - "+"Range: ${initialRange.first} to ${initialRange.second}"
        seekBarContainer.addView(customSeekBar)




    }
}