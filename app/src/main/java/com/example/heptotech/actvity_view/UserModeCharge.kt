package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.UserModeAdapter
import com.example.heptotech.bean_dataclass.UserMode

class UserModeCharge : AppCompatActivity() {
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chargestation)

        val recyclerView: RecyclerView = findViewById(R.id.recycle)
        imageView = findViewById(R.id.back1)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageView.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        val items = listOf(
            UserMode(imageResId = R.drawable.vector, text = "CHatsMO"),
            UserMode(imageResId = R.drawable.vector__12, text = "CCS 1"),
            UserMode(imageResId = R.drawable.vector__13, text = "CCS 2"),
            UserMode(imageResId = R.drawable.vector, text = "CCs 3")
        )
        val adapter = UserModeAdapter(items as MutableList<UserMode>) { position ->
            // Handle item selection
            // You can use this callback to perform actions when an item is selected
        }
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}