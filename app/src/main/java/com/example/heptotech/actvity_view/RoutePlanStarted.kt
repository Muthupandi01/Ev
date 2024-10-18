package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.heptotech.R

class RoutePlanStarted : AppCompatActivity()
{
    private lateinit var create_Route:LinearLayout
    private lateinit var back_img: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routeplan_design)
        create_Route=findViewById(R.id.routeplan_linear)
        back_img=findViewById(R.id.im)
        create_Route.setOnClickListener()
        {
            val intent = Intent(this, IphoneRouteplan::class.java)
            startActivity(intent)
        }
        back_img.setOnClickListener()
        {
            setResult(Activity.RESULT_OK)
            finish()


        }

    }
}