package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.heptotech.R

class RootPlanScreen : AppCompatActivity()
{
    private lateinit var createtextView: TextView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_rootplan_screen)
        createtextView=findViewById(R.id.create_text)
        createtextView.setOnClickListener()
        {
            val intent= Intent(this,RoutePlan::class.java)
            startActivity(intent)
        }

    }
}
