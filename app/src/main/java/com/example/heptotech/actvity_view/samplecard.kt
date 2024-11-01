package com.example.heptotech.actvity_view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.heptotech.R

class samplecard : AppCompatActivity() {

    private lateinit var textLeft: TextView
    private lateinit var textCenter: TextView
    private lateinit var textRight: TextView
    private lateinit var mainLayout: LinearLayout

    private lateinit var edgeCurvedBackgroundLeft: Drawable
    private lateinit var centerBackground: Drawable
    private lateinit var edgeCurvedBackgroundRight: Drawable
    private lateinit var defaultBackground: Drawable

    private var lastClicked: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_samplecard)

        textLeft = findViewById(R.id.text_left)
        textCenter = findViewById(R.id.text_center)
        textRight = findViewById(R.id.text_right)
        mainLayout = findViewById(R.id.main)

        defaultBackground = ContextCompat.getDrawable(this, R.drawable.default_background_ev)!!
        edgeCurvedBackgroundLeft = ContextCompat.getDrawable(this, R.drawable.leftc_ev)!!
        centerBackground = ContextCompat.getDrawable(this, R.drawable.centerc_ev)!!
        edgeCurvedBackgroundRight = ContextCompat.getDrawable(this, R.drawable.rightc_ev)!!

        textLeft.setOnClickListener {
            handleClick(textLeft, edgeCurvedBackgroundLeft)
        }

        textCenter.setOnClickListener {
            handleClick(textCenter, centerBackground)
        }

        textRight.setOnClickListener {
            handleClick(textRight, edgeCurvedBackgroundRight)
        }
    }

    private fun handleClick(clickedView: TextView, clickedBackground: Drawable) {
        // Reset the background of the last clicked view
        if (lastClicked != clickedView) {
            lastClicked?.background = defaultBackground
        }

        // Update the background of the current clicked view
        clickedView.background = clickedBackground
        lastClicked = clickedView
    }
}
