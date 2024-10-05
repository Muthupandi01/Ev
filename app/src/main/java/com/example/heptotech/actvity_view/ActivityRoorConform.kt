package com.example.heptotech.actvity_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.heptotech.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ActivityRoorConform : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_confirm)
        val circleImageView: ImageView = findViewById(R.id.cicle)
        val relativeLayout: RelativeLayout = findViewById(R.id.relative)
        circleImageView.setOnClickListener {

            relativeLayout.visibility = View.GONE
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

}

