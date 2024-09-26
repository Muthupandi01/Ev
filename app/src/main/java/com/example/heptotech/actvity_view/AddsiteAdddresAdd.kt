package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.heptotech.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.location.Geocoder
import android.widget.Toast
import java.util.Locale

class AddsiteAdddresAdd : AppCompatActivity(), OnMapReadyCallback {

    lateinit var typeaddres: EditText
    lateinit var sitename: EditText
    lateinit var conformbtn: TextView
    lateinit var back: ImageView
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addsite_adddres_add)

        conformbtn = findViewById(R.id.conformbtn)
        sitename = findViewById(R.id.sitename)
        typeaddres = findViewById(R.id.typeaddres)
        back = findViewById(R.id.back)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        updateSubmitButtonState()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSubmitButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        sitename.addTextChangedListener(textWatcher)
        typeaddres.addTextChangedListener(textWatcher)

        back.setOnClickListener {
            val intent = Intent()
          //  intent.putExtra("site_name", sitename.text.toString())
          //  intent.putExtra("site_address", typeaddres.text.toString())
            setResult(RESULT_OK, intent)
            finish()
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
        }

        conformbtn.setOnClickListener {
            val intent = Intent()
           // intent.putExtra("MYKEY", "MYVALUE")
          //  intent.putExtra("site_name", sitename.text.toString())
          //  intent.putExtra("site_address", typeaddres.text.toString())
            setResult(RESULT_OK, intent)
            finish()
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
        }
    }

    private fun updateSubmitButtonState() {
        val isSitenameFilled = sitename.text.isNotEmpty()
        val isTypeaddresFilled = typeaddres.text.isNotEmpty()
        conformbtn.isEnabled = isSitenameFilled && isTypeaddresFilled
        conformbtn.setBackgroundResource(
            if (isSitenameFilled && isTypeaddresFilled) R.drawable.rectanglef_ev else R.drawable.rectangle_34624554_ev
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
      //  intent.putExtra("MYKEY", "MYVALUE")
      //  intent.putExtra("site_name", sitename.text.toString())
      //  intent.putExtra("site_address", typeaddres.text.toString())
        setResult(RESULT_OK, intent)
        finish()
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set a marker on the map
        val defaultLocation = LatLng(0.323334, 32.578890)  // Example coordinates
        mMap.addMarker(
            MarkerOptions().position(defaultLocation).title("NAKASERO")
        )

        // Animate camera zoom-in effect
//        val cameraPosition = CameraPosition.Builder()
//            .target(defaultLocation)
//            .zoom(15.2f)
//            .build()
//        mMap.animateCamera(
//            CameraUpdateFactory.newCameraPosition(cameraPosition),
//            2000,  // Animation duration
//            null
//        )
        // Reverse geocode to fetch address from the latitude and longitude
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
        fetchAddressFromLatLng(defaultLocation)
    }

    private fun fetchAddressFromLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0].getAddressLine(0)  // Get the complete address
             //   typeaddres.setText(address)  // Set the address in the EditText
               // sitename.setText("EV Charge Station")  // Set the address in the EditText

                // Optional: Toast message to show the fetched address
               // Toast.makeText(this, "Address: $address", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Unable to fetch address", Toast.LENGTH_SHORT).show()
        }
    }
}

