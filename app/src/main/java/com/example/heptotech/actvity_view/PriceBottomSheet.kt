package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.heptotech.R

class PriceBottomSheet : AppCompatActivity() {
    lateinit var back: ImageView

    lateinit var singlechange: TextView
    lateinit var daynightchange: TextView

    lateinit var perweekchange: TextView
    lateinit var perminutechange: TextView

    lateinit var prepaidchange: TextView
    lateinit var postpaidchange: TextView


    lateinit var singlechild: LinearLayout
    lateinit var daynightchild: LinearLayout

    lateinit var btn_submit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_price_bottom_sheet)

        singlechange=findViewById(R.id.singlechange)
        daynightchange=findViewById(R.id.daynightchange)
        perweekchange=findViewById(R.id.perweekchange)
        perminutechange=findViewById(R.id.perminutechange)
        prepaidchange=findViewById(R.id.prepaidchange)
        postpaidchange=findViewById(R.id.postpaidchange)
        singlechild=findViewById(R.id.singlechild)
        daynightchild=findViewById(R.id.daynightchild)
        back=findViewById(R.id.back)
        btn_submit=findViewById(R.id.btn_submit)

        singlechange.setOnClickListener {
            singlechange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            singlechange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            daynightchange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            daynightchange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
            singlechild.isVisible=true
            daynightchild.isVisible=false
        }

        daynightchange.setOnClickListener {
            daynightchange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            daynightchange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            singlechange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            singlechange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
            singlechild.isVisible=false
            daynightchild.isVisible=true
        }



        perweekchange.setOnClickListener {
            perweekchange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            perweekchange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            perminutechange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            perminutechange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
        }

        perminutechange.setOnClickListener {
            perminutechange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            perminutechange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            perweekchange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            perweekchange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
        }

        prepaidchange.setOnClickListener {
            prepaidchange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            prepaidchange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            postpaidchange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            postpaidchange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
        }

        postpaidchange.setOnClickListener {
            postpaidchange.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor))
            postpaidchange.setBackgroundResource(R.drawable.rectangle_34624559_ev)
            prepaidchange.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
            prepaidchange.setBackgroundResource(R.drawable.rectangle_34624560_ev)
        }



        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        btn_submit.setOnClickListener {
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