package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.StopsAdapter

class ActivtyCalculateRoot : AppCompatActivity()
{
    private lateinit var confirmtextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_root)
        confirmtextView=findViewById(R.id.con2_text)
        confirmtextView.setOnClickListener(){

            val intent = Intent(this, ActivityAddstop::class.java)
            startActivity(intent)
        }


    }
}

