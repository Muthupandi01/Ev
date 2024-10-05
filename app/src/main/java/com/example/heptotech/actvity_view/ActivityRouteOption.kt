package com.example.heptotech.actvity_view

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.heptotech.R

class ActivityRouteOption : AppCompatActivity() {

    private lateinit var seekBars: Array<SeekBar>
    private lateinit var tvSeekBarProgress: Array<TextView>
    private lateinit var milesSeekBars: Array<SeekBar>
    private lateinit var tvMilesSeekBarProgress: Array<TextView>
    private lateinit var more_option: TextView
    private lateinit var Batery_charge: TextView
    private lateinit var seekBarExtra: SeekBar
    private lateinit var tvSeekBarExtra: TextView
    private lateinit var charge_stop:TextView
    private lateinit var charge_text:TextView
    private lateinit var charge_texts:TextView
    private lateinit var linear_change:LinearLayout
    private lateinit var linear_stop:LinearLayout
    private lateinit var linear_stops:LinearLayout
    private lateinit var search_text:TextView
    private lateinit var distance_text:TextView
    private lateinit var renge_text:TextView
    private lateinit var renge_texts:TextView
    private var areViewsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_routeplan_option)

        more_option = findViewById(R.id.more_text)
        Batery_charge = findViewById(R.id.startourney_text)
        charge_text = findViewById(R.id .charge_text)
        linear_change=findViewById(R.id.chage)
        linear_stop=findViewById(R.id.numbers)
        charge_stop = findViewById(R.id .number)
        charge_texts = findViewById(R.id .chagess)
        linear_stops=findViewById(R.id.numberss)
        search_text = findViewById(R.id .search_text)
        distance_text=findViewById(R.id.Distance_text)
        renge_text=findViewById(R.id.range_text)
        renge_texts=findViewById(R.id.range_textss)

        initializeSeekBars()

        more_option.setOnClickListener {
            toggleSeekBarsVisibility()

        }
    }

    private fun initializeSeekBars() {
        seekBars = arrayOf(
            findViewById(R.id.seekBar),
            findViewById(R.id.seekBar1),
            findViewById(R.id.seekBar2)
        )

        tvSeekBarProgress = arrayOf(
            findViewById(R.id.tect),
            findViewById(R.id.tectss),
            findViewById(R.id.tectsss)
        )

        milesSeekBars = arrayOf(
            findViewById(R.id.seekBar4),
            findViewById(R.id.seekBar5)
        )

        tvMilesSeekBarProgress = arrayOf(
            findViewById(R.id.tect_miles),
            findViewById(R.id.tect_max)
        )

        seekBarExtra = findViewById(R.id.seekBar6)
        tvSeekBarExtra = findViewById(R.id.tect_maxs)

        for (i in seekBars.indices) {
            setupSeekBarListener(seekBars[i], tvSeekBarProgress[i])
        }

        for (i in milesSeekBars.indices) {
            setupMilesSeekBarListener(milesSeekBars[i], tvMilesSeekBarProgress[i])
        }

        setupExtraSeekBarListener(seekBarExtra, tvSeekBarExtra)
    }

    private fun toggleSeekBarsVisibility() {
        areViewsVisible = !areViewsVisible
        val visibility = if (areViewsVisible) View.VISIBLE else View.GONE

        // Toggle visibility for SeekBars and related TextViews
        for (seekBar in seekBars) {
            seekBar.visibility = visibility
        }
        for (textView in tvSeekBarProgress) {
            textView.visibility = visibility
        }

        // Toggle visibility for Miles SeekBars and their TextViews
        for (seekBar in milesSeekBars) {
            seekBar.visibility = visibility
        }
        for (textView in tvMilesSeekBarProgress) {
            textView.visibility = visibility
        }

        // Toggle visibility for the extra SeekBar and its TextView
        seekBarExtra.visibility = visibility
        tvSeekBarExtra.visibility = visibility

        // Toggle visibility for additional views
        linear_change.visibility = visibility
        linear_stop.visibility = visibility
        charge_stop.visibility = visibility
        Batery_charge.visibility = visibility
        charge_text.visibility = visibility
        charge_texts.visibility = visibility
        linear_stops.visibility = visibility
        search_text.visibility = visibility
        distance_text.visibility = visibility
        renge_text.visibility = visibility
        renge_texts.visibility = visibility
    }

    private fun setupSeekBarListener(seekBar: SeekBar, textView: TextView) {
        textView.text = "${seekBar.progress}"

        seekBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                seekBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateTextViewPosition(seekBar, textView, seekBar.progress)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = progress.toString()
                seekBar?.let { updateTextViewPosition(it, textView, progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupMilesSeekBarListener(seekBar: SeekBar, textView: TextView) {
        textView.text = "${seekBar.progress} miles"

        seekBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                seekBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateTextViewPosition(seekBar, textView, seekBar.progress)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "$progress miles"
                seekBar?.let { updateTextViewPosition(it, textView, progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupExtraSeekBarListener(seekBar: SeekBar, textView: TextView) {
        textView.text = "${seekBar.progress} locations"

        seekBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                seekBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateTextViewPosition(seekBar, textView, seekBar.progress)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "$progress locations"
                seekBar?.let { updateTextViewPosition(it, textView, progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTextViewPosition(seekBar: SeekBar, textView: TextView, progress: Int) {
        val seekBarWidth = seekBar.width - seekBar.paddingStart - seekBar.paddingEnd
        val thumbPosX = (progress / seekBar.max.toDouble() * seekBarWidth).toInt() + seekBar.paddingStart
        val tvWidth = textView.width
        val newX = thumbPosX - (tvWidth / 2)

        textView.x = newX.toFloat()
        // Set Y position above the thumb
        val thumbPosY = seekBar.y - textView.height - 16 // Adjust as needed
        textView.y = thumbPosY
    }
}
