package com.example.heptotech.actvity_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.heptotech.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class IphoneMap : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.iphone_google_map_ev)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Define marker locations
        val nakaseroLocation = LatLng(0.323334, 32.578890) // Marker with title
        val otherLocations = listOf(
            LatLng(0.374000, 32.579000), // Another marker without title
            LatLng(0.355000, 32.580000),
            LatLng(0.327500, 32.579500), // First additional marker
            LatLng(0.329500, 32.580500), // Second additional marker
            LatLng(0.335500, 32.581500)// Another marker without title
        )

        // Add marker for NAKASERO with custom image
        val nakaseroMarkerImage = BitmapFromVector(this, R.drawable.layer_3_ev)
        mMap.addMarker(
            MarkerOptions()
                .position(nakaseroLocation)
                .title("NAKASERO")
                .icon(nakaseroMarkerImage) // Custom icon
        )

        // Add other markers without titles (using default marker)
        for (location in otherLocations) {
            mMap.addMarker(
                MarkerOptions()
                    .position(location) // Set the position
                    .icon(nakaseroMarkerImage) // Default marker
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
                val address = addresses[0].getAddressLine(0) // Get the complete address
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
