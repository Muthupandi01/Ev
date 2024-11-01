package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.heptotech.R

class RoutePlanCalculateRoot : AppCompatActivity()
{
    private lateinit var confirmtextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_root)
        confirmtextView=findViewById(R.id.con2_text)
        confirmtextView.setOnClickListener(){

            val intent = Intent(this, RoutePlanStop::class.java)
            startActivity(intent)
        }


    }
}

