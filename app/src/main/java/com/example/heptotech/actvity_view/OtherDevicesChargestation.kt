package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.heptotech.R
import com.example.heptotech.activity_view.ConnectToEvActivity

class OtherDevicesChargestation : AppCompatActivity() {
    lateinit var back:ImageView
    lateinit var card_click:CardView
    lateinit var addsitelnr:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_other_devices_chargestation)
        addsitelnr=findViewById(R.id.addsitelnr)
        back=findViewById(R.id.back)
        card_click=findViewById(R.id.card_click)

        card_click.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        addsitelnr.setOnClickListener {
            val intent = Intent(this@OtherDevicesChargestation, ConnectToEvActivity::class.java)
            startActivity(intent)
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