package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heptotech.R
import com.example.heptotech.activity_view.ConnectToEvActivity

class ConnectToServer : AppCompatActivity() {
    lateinit var back:ImageView
  lateinit var btn_submit:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_server)
        back=findViewById(R.id.back)
        btn_submit=findViewById(R.id.btn_submit)

        btn_submit.setOnClickListener{
            val intent = Intent(this@ConnectToServer, MapMakrAftersave::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
        }

        back.setOnClickListener{
            val intent = Intent(this@ConnectToServer, ConnectToEvActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ConnectToServer, ConnectToEvActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}