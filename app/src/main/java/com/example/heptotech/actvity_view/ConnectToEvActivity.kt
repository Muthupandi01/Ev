package com.example.heptotech.activity_view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.actvity_view.ConnectToServer
import com.example.heptotech.actvity_view.PrivateStation
import com.example.heptotech.adapters.VehicleItemCheckBoxAdapter
import com.example.heptotech.bean_dataclass.VehicleItem

// ConnectToEvActivity.kt
class ConnectToEvActivity : AppCompatActivity() {

    private lateinit var vehicleRecyclerView: RecyclerView
    private lateinit var btnSubmit: TextView
    private lateinit var devicename: TextView
    private lateinit var vehicleItemCheckBoxAdapter: VehicleItemCheckBoxAdapter
    private lateinit var scrollView: ScrollView
    lateinit var back: ImageView
    private val vehicleList = listOf(
        VehicleItem(R.drawable.g, "2-Wheeler"),
        VehicleItem(R.drawable.h, "4-Wheeler")
        // Add more items as needed
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_ev)
        vehicleRecyclerView = findViewById(R.id.vehicletype)
        btnSubmit = findViewById(R.id.btn_submit)
        devicename = findViewById(R.id.devicename)
        back=findViewById(R.id.back)



        btnSubmit.setOnClickListener {
            val intent = Intent(this@ConnectToEvActivity, ConnectToServer::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }


        back.setOnClickListener{
            val intent = Intent(this@ConnectToEvActivity, PrivateStation::class.java)
            startActivity(intent)
        }

        vehicleItemCheckBoxAdapter = VehicleItemCheckBoxAdapter(vehicleList) { isChecked ->
            updateSubmitButtonColor(isChecked)
        }
        vehicleRecyclerView.layoutManager = LinearLayoutManager(this)
        vehicleRecyclerView.adapter = vehicleItemCheckBoxAdapter

        // Optionally enable edge-to-edge mode
        enableEdgeToEdge()
        setRedStarTextView(devicename)
    }

    fun setRedStarTextView(textView: TextView) {
        val context = textView.context

        // Create a SpannableString with the text you want
        val text = "Device name *"
        val spannableString = SpannableString(text)

        // Define the color for the star
        val starColor = Color.RED

        // Define the position for the star
        val starIndex = text.indexOf("*")

        // Create a drawable for the star
        val starDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.red_star_ev)
        starDrawable?.setBounds(0, 0, starDrawable.intrinsicWidth, starDrawable.intrinsicHeight)

        // Set the star drawable to the spannable string
        val imageSpan = starDrawable?.let { ImageSpan(it, ImageSpan.ALIGN_BOTTOM) }
        spannableString.setSpan(imageSpan, starIndex, starIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the color of the star
        spannableString.setSpan(ForegroundColorSpan(starColor), starIndex, starIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the styled text to the TextView
        textView.text = spannableString
    }

    private fun updateSubmitButtonColor(isChecked: Boolean) {
        if (isChecked) {
            btnSubmit.setBackgroundResource(R.drawable.rectanglef_ev) // Green drawable for enabled state
        } else {
            btnSubmit.setBackgroundResource(R.drawable.rectangle_34624554_ev) // Grey drawable for disabled state
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ConnectToEvActivity, PrivateStation::class.java)
        startActivity(intent)
    }
}

