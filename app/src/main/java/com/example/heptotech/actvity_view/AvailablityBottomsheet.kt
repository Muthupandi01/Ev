package com.example.heptotech.activity_view

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.heptotech.R

class AvailablityBottomsheet : AppCompatActivity() {
    lateinit var btnSubmit: TextView
    lateinit var back: ImageView
    lateinit var manualonlychild: LinearLayout
    lateinit var sunTextView: TextView
    lateinit var monTextView: TextView
    lateinit var tueTextView: TextView
    lateinit var wedTextView: TextView
    lateinit var thuTextView: TextView
    lateinit var friTextView: TextView
    lateinit var satTextView: TextView
    lateinit var daymode:ConstraintLayout
    lateinit var night_mode:ConstraintLayout
    lateinit var maual_mode:ConstraintLayout

    private var selectedTextViews = mutableSetOf<TextView>()
    lateinit var onlineChange: TextView
    lateinit var offlineChange: TextView
    private lateinit var model:TextView

    private lateinit var days:TextView
    //mode
    lateinit var radioHome: RadioButton
    lateinit var radioOffice: RadioButton
    lateinit var radioCommercial: RadioButton

    //model
    lateinit var radio_day: RadioButton
    lateinit var radio_night: RadioButton
    lateinit var radio_manual: RadioButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Optional for edge-to-edge UI
        setContentView(R.layout.activity_availablity_bottomsheet)

        // Find views by their ID
        back = findViewById(R.id.back)
        btnSubmit = findViewById(R.id.btn_submit)
        sunTextView = findViewById(R.id.sun)
        monTextView = findViewById(R.id.mon)
        tueTextView = findViewById(R.id.tue)
        wedTextView = findViewById(R.id.wed)
        thuTextView = findViewById(R.id.thu)
        friTextView = findViewById(R.id.fri)
        satTextView = findViewById(R.id.sat)
        onlineChange = findViewById(R.id.Onlinechange)
        offlineChange = findViewById(R.id.oflinechange)

        radioHome = findViewById(R.id.radio_home)
        radioOffice = findViewById(R.id.radio_office)
        radioCommercial = findViewById(R.id.radio_commercial)

        radio_day = findViewById(R.id.radio_day)
        radio_night = findViewById(R.id.radio_night)
        radio_manual = findViewById(R.id.radio_manual)
        manualonlychild = findViewById(R.id.manualonlychild)
        model = findViewById(R.id.maual_text)
        days=findViewById(R.id.days_text)
        daymode=findViewById(R.id.day_mode)
        night_mode=findViewById(R.id.night_mode)
        maual_mode=findViewById(R.id.manual_mode)



        // Handle back button click
        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Handle submit button click
        btnSubmit.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Handle online/offline change
        onlineChange.setOnClickListener {
            onlineChange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            onlineChange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            offlineChange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            offlineChange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
        }

        offlineChange.setOnClickListener {
            offlineChange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            offlineChange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            onlineChange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            onlineChange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
        }

        // Set click listeners for all TextViews (days of the week)
        val textViews = listOf(sunTextView, monTextView, tueTextView, wedTextView, thuTextView, friTextView, satTextView)
        textViews.forEach { textView ->
            textView.setOnClickListener {
                toggleTextView(textView)
            }
        }

        // Handle RadioButton clicks separately
        radioHome.setOnClickListener {
            // Handle home radio button click
            radioHomeClicked()
        }

        radioOffice.setOnClickListener {
            // Handle office radio button click
            radioOfficeClicked()
        }

        radioCommercial.setOnClickListener {
            // Handle commercial radio button click
            radioCommercialClicked()
        }

        // Add separate click handling for day, night, and manual if needed
        radio_day.setOnClickListener {
            radioDayClicked()
        }

        radio_night.setOnClickListener {
            radioNightClicked()
        }

        radio_manual.setOnClickListener {
            radioManualClicked()
        }
    }

    // Function to handle Home RadioButton click
    private fun radioHomeClicked() {
        radioOffice.isChecked=false
        radioCommercial.isChecked=false
        radioHome.isChecked=true
        manualonlychild.isVisible=false
        model.isVisible=false


        sunTextView.isVisible=false
        monTextView.isVisible=false
        tueTextView.isVisible=false
        wedTextView.isVisible=false
        thuTextView.isVisible=false
        friTextView.isVisible=false
        satTextView.isVisible=false
        days.isVisible=false
        daymode.isVisible=false
        night_mode.isVisible=false
        maual_mode.isVisible=false
    }

    // Function to handle Office RadioButton click
    private fun radioOfficeClicked() {
        radioHome.isChecked=false
        radioOffice.isChecked=true
        radioCommercial.isChecked=false
        days.isVisible=true
        night_mode.isVisible=true
        maual_mode.isVisible=true
        model.isVisible=true
        daymode.isVisible=true
        sunTextView.isVisible=true
        monTextView.isVisible=true
        tueTextView.isVisible=true
        wedTextView.isVisible=true
        thuTextView.isVisible=true
        friTextView.isVisible=true
        satTextView.isVisible=true


    }

    // Function to handle Commercial RadioButton click
    private fun radioCommercialClicked() {

        radioHome.isChecked=false
        radioOffice.isChecked=false
        radioCommercial.isChecked=true
        days.isVisible=true
        night_mode.isVisible=true
        maual_mode.isVisible=true
        model.isVisible=true
        daymode.isVisible=true
        sunTextView.isVisible=true
        monTextView.isVisible=true
        tueTextView.isVisible=true
        wedTextView.isVisible=true
        thuTextView.isVisible=true
        friTextView.isVisible=true
        satTextView.isVisible=true
    }

    // Function to handle Day RadioButton click
    private fun radioDayClicked() {
        radio_day.isChecked=true
        radio_night.isChecked=false
        radio_manual.isChecked=false
        manualonlychild.isVisible=false

    }

    // Function to handle Night RadioButton click
    private fun radioNightClicked() {
        radio_day.isChecked=false
        radio_night.isChecked=true
        radio_manual.isChecked=false
        manualonlychild.isVisible=false

    }

    // Function to handle Manual RadioButton click
    private fun radioManualClicked() {
        radio_day.isChecked=false
        radio_night.isChecked=false
        radio_manual.isChecked=true
        manualonlychild.isVisible=true

    }


    // Override the back button to return a result
    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    // Function to toggle the background and text color of TextView
    private fun toggleTextView(textView: TextView) {
        if (selectedTextViews.contains(textView)) {
            // Deselect the TextView
            selectedTextViews.remove(textView)
            textView.setBackgroundResource(R.drawable.rectangle_34624564_ev) // Grey background
            textView.setTextColor(Color.BLACK) // Black text
        } else {
            // Select the TextView
            selectedTextViews.add(textView)
            textView.setBackgroundResource(R.drawable.rec_green_ev) // Green background
            textView.setTextColor(Color.WHITE) // White text
        }
    }


}
