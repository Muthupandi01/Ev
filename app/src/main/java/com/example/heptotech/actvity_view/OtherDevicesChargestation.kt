package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.heptotech.R

class OtherDevicesChargestation : AppCompatActivity() {
    lateinit var back:ImageView
    lateinit var card_click:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_other_devices_chargestation)
        back=findViewById(R.id.back)
        card_click=findViewById(R.id.card_click)

        card_click.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}