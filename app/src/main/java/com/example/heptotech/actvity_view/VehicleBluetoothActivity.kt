package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.BrandAdapterDevice
import com.example.heptotech.bean_dataclass.Brand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VehicleBluetoothActivity : AppCompatActivity() {

    // Declaring variables
    private lateinit var backImageView: ImageView
    private lateinit var gifImageView: pl.droidsonroids.gif.GifImageView
    private lateinit var searchingTextView: TextView
    private lateinit var noDeviceImageView: LinearLayout
    private lateinit var connectButton: Button
    private lateinit var serachparent: LinearLayout
    private lateinit var devices: RecyclerView
    private lateinit var img_image: LinearLayout
    private lateinit var tempv: String

    //
    private lateinit var vehiclepair_lnr:LinearLayout
    private lateinit var turnon:TextView
    private lateinit var overallbtn:LinearLayout
    private lateinit var parentsub:LinearLayout
    private lateinit var commontxt:TextView
    private lateinit var changetxtsubhead:TextView
    private lateinit var changetxthead:TextView
    private lateinit var commongif: pl.droidsonroids.gif.GifImageView

    private var selectedConnection: String? = null

     var type:String?=null
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_bluetooth)

        // Initializing variables
        backImageView = findViewById(R.id.back)
        gifImageView = findViewById(R.id.gif)
        noDeviceImageView = findViewById(R.id.img_image)
        connectButton = findViewById(R.id.btn_submit)
        serachparent = findViewById(R.id.serachparent)
        devices = findViewById(R.id.devices)
        img_image = findViewById(R.id.img_image)

        //

        vehiclepair_lnr = findViewById(R.id.vehiclepair_lnr)
        overallbtn = findViewById(R.id.overallbtn)
        parentsub = findViewById(R.id.parentsub)
        commongif = findViewById(R.id.commongif)
        commontxt = findViewById(R.id.commontxt)
        turnon = findViewById(R.id.turnon)
        changetxtsubhead = findViewById(R.id.changetxtsubhead)
        changetxthead = findViewById(R.id.changetxthead)


        type = intent.getStringExtra("SEND")

        when (type) {
            "B" -> {
                commontxt.text="Turn on vehicle bluetooth and keep your phone near"
                changetxtsubhead.text="Turn on vehicle bluetooth and keep your phone near"
                changetxthead.text="Bluetooth"
                selectedConnection = "Bluetooth"

            }
            "W" -> {
                selectedConnection = "OBD"
                commontxt.text="Insert the OBD II device in your vehicle"
                changetxtsubhead.text="Insert the OBD II device in your vehicle"
                changetxthead.text="OBD wireless connection"
            }

        }


      connectButton.setOnClickListener {
          val resultIntent = Intent()
          resultIntent.putExtra("SELECTED_CONNECTION", selectedConnection)
          setResult(Activity.RESULT_OK, resultIntent)
          finish()
      }

        turnon.setOnClickListener {
            when (type) {
                "B" -> {
                    commongif.setImageResource(R.drawable.bluetooth_ev)
                }
                "W" -> {
                    commongif.setImageResource(R.drawable.wifi_ev)
                }
            }

            GlobalScope.launch(Dispatchers.Main) {
                    delay(2000)
                vehiclepair_lnr.isVisible=false
                when (type) {
                    "B" -> {
                        gifImageView.setImageResource(R.drawable.bluetooth_ev)
                    }
                    "W" -> {
                        gifImageView.setImageResource(R.drawable.wifi_ev)
                    }
                }
                     parentsub.isVisible=true

                        GlobalScope.launch(Dispatchers.Main) {
                  delay(2000)
            img_image.isVisible = false
            serachparent.isVisible = true
        }



                     overallbtn.isVisible=true



                }



        }



        backImageView.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Initialize the data list
        val brandList = listOf(
            Brand(R.drawable._3_car_ev, "John Tesla X"),
            Brand(R.drawable.earbuds_ev, "Pixel Buds A- Series"),
            Brand(R.drawable.lap_ev, "Laptop")
        )


        val adapter = BrandAdapterDevice(brandList, connectButton) { brand ->
            if (brand != null) {
                tempv=brand!!.name


            } else {
                // Handle the case where no brand is selected (unselected)
            }
        }


        // Set up RecyclerView
        devices.layoutManager = LinearLayoutManager(this)
        devices.adapter = adapter

        // Optionally: Show noDeviceImageView if no devices are available
        if (brandList.isEmpty()) {
            noDeviceImageView.visibility = View.VISIBLE
        } else {
            noDeviceImageView.visibility = View.GONE
        }

        // Delay for 3 seconds using coroutine
        img_image.isVisible = true
//        GlobalScope.launch(Dispatchers.Main) {
//            delay(3000)
//            img_image.isVisible = false
//            serachparent.isVisible = true
//        }
    }


}
