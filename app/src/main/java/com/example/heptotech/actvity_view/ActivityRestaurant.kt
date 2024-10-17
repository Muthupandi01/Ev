package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.RestaurantAdapter
import com.example.heptotech.bean_dataclass.RestaurantData

class ActivityRestaurant : AppCompatActivity()
{
    private lateinit var recylcer:RecyclerView
    private lateinit var card_amen:LinearLayout
    private var isRecyclerViewVisible = false
    private lateinit var amen_textView: TextView
    private lateinit var char:LinearLayout
    private lateinit var charge_textView: TextView
    private lateinit var char_chat:LinearLayout
    private lateinit var chat_textView: TextView
    private lateinit var back_image:ImageView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_location_charperpoint)
        recylcer=findViewById(R.id.recyclerView)
        card_amen=findViewById(R.id.card_text)
        amen_textView=findViewById(R.id.card_am)
        char=findViewById(R.id.card_charge)
        charge_textView=findViewById(R.id.text_ca)
        char_chat=findViewById(R.id.card_chat)
        chat_textView=findViewById(R.id.chat_text)
        back_image=findViewById(R.id.back_img)

        card_amen.setOnClickListener()
        {
            cardchange()
            recylcer.isVisible=true
        }
        char.setOnClickListener()
        {
            chargechange()
            recylcer.isVisible=false
        }
        char_chat.setOnClickListener()
        {
            chatchange()
                recylcer.isVisible=false
        }
        back_image.setOnClickListener()
        {

                setResult(Activity.RESULT_OK)
                finish()

        }
        val items = listOf(
            RestaurantData(
                R.drawable.car_img_overviewsub_ev,
                " Restaurent ",
                "Harrodes",
                2f,
                "38,731",
                "Open Hours: 10:00 - 17:00",
                "Mobile Number: 123456789",
                "London For 4 Days With Your Partner."
            ),
            RestaurantData(
                R.drawable.car_img_overviewsub_ev,
                " Attraction ",
                "Harrodes",
                4.5f,
                "40,000",
                "Open Hours: 10:00 - 17:00",
                "Mobile Number: 123456789",
                "London For 4 Days With Your Partner."
            ),

        )

        val adapter = RestaurantAdapter(items)
       // recylcer.isNestedScrollingEnabled = false
      //  ViewCompat.setNestedScrollingEnabled(recylcer, false)
        recylcer.adapter = adapter
        recylcer.layoutManager = LinearLayoutManager(this)

    }

    private fun chatchange() {
        char_chat.setBackgroundResource(R.drawable.green_bgev)
        chat_textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        card_amen.setBackgroundResource(R.drawable.empty_bgev)
        amen_textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        char.setBackgroundResource(R.drawable.empty_bgev)
        charge_textView.setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun chargechange() {
        char.setBackgroundResource(R.drawable.green_bgev)
        charge_textView.setTextColor(ContextCompat.getColor(this, R.color.white))

        // Reset "Amenities" background and text to normal
        card_amen.setBackgroundResource(R.drawable.empty_bgev)
        amen_textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        char_chat.setBackgroundResource(R.drawable.empty_bgev)
        chat_textView.setTextColor(ContextCompat.getColor(this, R.color.black))

    }

    private fun cardchange() {
        card_amen.setBackgroundResource(R.drawable.green_bgev)
        amen_textView.setTextColor(ContextCompat.getColor(this, R.color.white))

        // Reset "Chargers" background and text to normal
        char.setBackgroundResource(R.drawable.empty_bgev)
        charge_textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        char_chat.setBackgroundResource(R.drawable.empty_bgev)
        chat_textView.setTextColor(ContextCompat.getColor(this, R.color.black))



    }
    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK)
        finish()
    }


}