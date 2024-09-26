package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heptotech.R
import com.example.heptotech.activity_view.ConnectToEvActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class PrivateStation : AppCompatActivity() {
    lateinit var overlay:LinearLayout
    lateinit var addChargerButton:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_station)
        overlay=findViewById(R.id.overlay)
        addChargerButton=findViewById(R.id.addChargerButton)

        addChargerButton.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_privatestation, null)
            bottomSheetDialog.setContentView(view)
            val chooseCamera = view.findViewById<ConstraintLayout>(R.id.choose_camera)

            chooseCamera.setOnClickListener {
                val intent = Intent(this@PrivateStation, ConnectToEvActivity::class.java)
                startActivity(intent)
               // overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }



            bottomSheetDialog.show()
        }
    }
}