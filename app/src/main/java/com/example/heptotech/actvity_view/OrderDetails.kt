package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heptotech.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class OrderDetails : AppCompatActivity() {
    private lateinit var main:ConstraintLayout
    private lateinit var back:ImageView
    private lateinit var proceedTopay:TextView
    private lateinit var showbottomsheet:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)


        main=findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        back=findViewById(R.id.back)
        proceedTopay=findViewById(R.id.proceedTopay)
        showbottomsheet=findViewById(R.id.showbottomsheet)
        showbottomsheet.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_orderdetails, null)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
        proceedTopay.setOnClickListener {
            val scanResult = "Sample Scan Result" // Replace with actual scan result
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_SCAN_RESULT", scanResult)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close ScanActivity and return to ViewVehicles
        }

        back.setOnClickListener {
            val scanResult = "Sample Scan Result" // Replace with actual scan result
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_SCAN_RESULT", scanResult)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close ScanActivity and return to ViewVehicles
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        val scanResult = "Sample Scan Result" // Replace with actual scan result
        val resultIntent = Intent()
        resultIntent.putExtra("EXTRA_SCAN_RESULT", scanResult)
        setResult(Activity.RESULT_OK, resultIntent)
        finish() // Close ScanActivity and return to ViewVehicles
    }
}