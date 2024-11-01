package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.UserModeAdapter
import com.example.heptotech.bean_dataclass.UserMode

class ConfiguratonActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

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
            UserMode(imageResId = R.drawable.vector__12_ev, text = "CCS 1"),
            UserMode(imageResId = R.drawable.vector__13_ev, text = "CCS 2"),
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