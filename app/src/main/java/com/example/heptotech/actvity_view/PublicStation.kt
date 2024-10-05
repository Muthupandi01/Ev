package com.example.heptotech.actvity_view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heptotech.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale

class PublicStation : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var topCenterText: TextView
    private lateinit var mMap: GoogleMap
    private lateinit var imageView: ImageView
    private lateinit var currentLocationImageView: ImageView
    private lateinit var current_loctions: ImageView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
  var checkkey="Open"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_public_station)
        imageView = findViewById(R.id.group1)
        currentLocationImageView = findViewById(R.id.current_loction)
        topCenterText = findViewById(R.id.topCenterText)
        current_loctions = findViewById(R.id.current_loctions)

        topCenterText.setOnClickListener {
            showImagePickerBottomSheet()
        }

        current_loctions.setOnClickListener {
            //Selva bro intent

        // startActivity(Intent(this, VehicleSpecificOpenBooking::class.java))

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        imageView.setOnClickListener {
            toggleMap()
        }

        currentLocationImageView.setOnClickListener {
            getCurrentLocation()
        }
    }


    private fun showImagePickerBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_public, null)
        bottomSheetDialog.setContentView(view)

        val chooseCamera = view.findViewById<ConstraintLayout>(R.id.choose_camera)
        val chooseGallery = view.findViewById<ConstraintLayout>(R.id.choose_gallery)

        chooseCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkkey="open"
            startActivity(Intent(this, VehicleSpecificOpenBooking::class.java).putExtra("KEY",checkkey))

        }

        chooseGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkkey="close"
            startActivity(Intent(this, VehicleSpecificOpenBooking::class.java).putExtra("KEY",checkkey))


        }

        bottomSheetDialog.show()
    }

    private fun toggleMap() {
        if (mMap.mapType == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            imageView.setImageResource(R.drawable.group_908_ev) // Change to satellite icon
        } else {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            imageView.setImageResource(R.drawable.group_427318908_2x) // Change back to normal icon
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val userLocation = LatLng(location.latitude, location.longitude)
                val markerIcon = BitmapFromVector(this, R.drawable.group_427318907) // Change to your marker image
                mMap.addMarker(MarkerOptions().position(userLocation).icon(markerIcon).title("Current Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                fetchAddressFromLatLng(userLocation)
            } else {
                Toast.makeText(this, "Unable to find current location", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to get location: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("IphoneMap", "Location failure", e)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Define marker locations
        val nakaseroLocation = LatLng(0.323334, 32.578890) // Marker with title
        val otherLocations = listOf(
            LatLng(0.374000, 32.579000),
            LatLng(0.355000, 32.580000),
            LatLng(0.327500, 32.579500),
            LatLng(0.329500, 32.580500),
            LatLng(0.335500, 32.581500)
        )

        // Add marker for NAKASERO with custom image
        val nakaseroMarkerImage = BitmapFromVector(this, R.drawable.layer_3_ev)
        mMap.addMarker(
            MarkerOptions()
                .position(nakaseroLocation)
                .title("NAKASERO")
                .icon(nakaseroMarkerImage)
        )

        // Add other markers without titles
        for (location in otherLocations) {
            mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .icon(nakaseroMarkerImage)
            )
        }

        // Move camera to NAKASERO location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nakaseroLocation, 15f))
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun fetchAddressFromLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0].getAddressLine(0)
                Toast.makeText(this, "Address: $address", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Unable to fetch address", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}