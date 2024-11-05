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
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.PublicStationMarkerAdapter
import com.example.heptotech.bean_dataclass.MarkerInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale

class PublicStation : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var topCenterText: CardView
    private lateinit var mMap: GoogleMap
    private lateinit var imageView: ImageView
    private lateinit var currentLocationImageView: ImageView
    private lateinit var current_loctions: ImageView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val markers = mutableListOf<Marker>() // List to hold all markers
    var checkkey = "Open"
    private lateinit var blinkimage: ImageView
    private lateinit var current:ImageView
    private lateinit var bottomsearch:RelativeLayout
    private var clickedMarker: Marker? = null
    private lateinit var recyclerView:RecyclerView
    private var currentLocationMarker: Marker? = null
    private var isInCurrentLocationMode = false
    private lateinit var left_image:ImageView
    private var originalCameraPosition: LatLng? = null
    private var lastClickedMarkerIndex: Int? = null
    private lateinit var group1C:CardView
    private lateinit var current_loctionsC:CardView






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_public_station)
        imageView = findViewById(R.id.group1)
        currentLocationImageView = findViewById(R.id.current_loction)
        topCenterText = findViewById(R.id.topCenterText)
        current_loctions = findViewById(R.id.current_loctions)
        current=findViewById(R.id.group)
        bottomsearch=findViewById(R.id.bottom_container)
        recyclerView=findViewById(R.id.recyclerView)
        group1C=findViewById(R.id.group1C)
        current_loctionsC=findViewById(R.id.current_loctionsC)
        // left_image = findViewById(R.id.left_angle)
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.idSearchView)
        // val searchTextView = searchView.findViewById<android.widget.TextView>(androidx.appcompat.R.id.search_src_text)
        val searchTextView = searchView.findViewById<android.widget.TextView>(androidx.appcompat.R.id.search_src_text)
        searchTextView?.let {
            it.textSize = 12f // Set your desired text size (SP)
            it.setTextColor(ContextCompat.getColor(this, R.color.black))
            //  it.setTypeface(null, Typeface.BOLD)

        } ?: Log.e("SearchView", "TextView not found inside SearchView")



        blinkimage = findViewById(R.id.blink_img)
        startBlinkingAnimation()
        blinkimage.setOnClickListener()
        {
            showImagePickerBottomSheet()
        }

        topCenterText.setOnClickListener {
            toggleMarkersVisibility()
//            for (marker in markers) {
//                marker.setIcon(BitmapFromVector(this, R.drawable.frame_locate, 125, 125)) // Reset to the original icon
//            }
            //selva
            for (marker in markers) {
                marker.setIcon(
                    BitmapDescriptorFactory.fromBitmap(bitmapFromLayout(this, R.layout.cluster_marker, 125, 125))
                )
            }


            for (marker in markers) {
                marker.isVisible = true
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 2f))

            }


            topCenterText.visibility = View.GONE
            clickedMarker = null
            recyclerView.isVisible = false
            group1C.isVisible=true
            current_loctionsC.isVisible=true


            //  topCenterText.visibility = ImageView.INVISIBLE
            if (!isInCurrentLocationMode) {
                topCenterText.visibility = View.INVISIBLE
            } else {
                // Optionally, you can also keep it visible here to ensure it doesn't change
                topCenterText.visibility = View.VISIBLE // Ensure it remains visible if in current location mode
            }
        }







        current.setOnClickListener()
        {
            getCurrentLocation()

        }

        val markerInfoList = listOf(
            MarkerInfo("Akibua Rd Point", "22,Akii Bus Road,\nKambala 22,Akii Bus", 4.5f, "1/4 Available", "Type 2\n22Kw AC", "Type 2\n22Kw AC", "Type 3\n22Kw AC","200 Km"),
            MarkerInfo("Another Point", "Akii Bus Road,\n" +
                    "Kambala 22,Akii Bus", 3.5f, "2/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km"),
            MarkerInfo("Charger Point", "Akii Bus Road,\n" +
                    "Kambala 22,Akii Bus", 3.5f, "3/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km"),
            MarkerInfo("Kambala city", "Akii Bus Road,\n" +
                    "Kambala 22,Akii Bus", 3.5f, "4/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km"))
        val adapter = PublicStationMarkerAdapter(markerInfoList)
        // val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

// Set LinearLayoutManager with horizontal orientation
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        current_loctions.setOnClickListener {
            //Selva bro intent
            startActivity(Intent(this, RoutePlanStarted::class.java))
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        imageView.setOnClickListener {
            showPopupDialog(it)
        }

        currentLocationImageView.setOnClickListener{
            toggleMap()
        }
    }

    fun bitmapFromLayout(context: Context, layoutResId: Int, width: Int, height: Int): Bitmap {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutResId, null)

        // Measure and layout the view
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        // Create a bitmap with the view's dimensions
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }


    /*  private fun showAllMarkers() {
          for (marker in markers) {
              marker.isVisible = true
          }

          // Optionally, you can also move the camera to the last clicked marker or the first marker
          if (clickedMarker != null) {
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedMarker!!.position, 15f))
          } else {
              // Move the camera to the first marker in the list
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers[0].position, 15f))
          }
         // recyclerView.isVisible=true
          Handler(Looper.getMainLooper()).postDelayed({
              Log.d("RVVisAfter", "Setting visibility to VISIBLE")
              recyclerView.visibility = View.VISIBLE
              Log.d("RVVisibility", "New Visibility: ${recyclerView.visibility}")
              Log.d("AdapterItemCount", "Item count: ${recyclerView.adapter?.itemCount}")
          }, 3000) // Delay for 5000 milliseconds (5 seconds)
      }*/

    private fun showPopupDialog(view: View)
    {
        val inflater = LayoutInflater.from(this)
        val popupView = inflater.inflate(R.layout.popup_dialog, null)

        // Create the PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        // Set up click listeners for each option in the popup
        val optionAvailable = popupView.findViewById<TextView>(R.id.option_available)
        val optionUnavailable = popupView.findViewById<TextView>(R.id.option_unavailable)
        val optionInUse = popupView.findViewById<TextView>(R.id.option_in_use)

        optionAvailable.setOnClickListener {
            Toast.makeText(this, "Available selected", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }

        optionUnavailable.setOnClickListener {
            Toast.makeText(this, "Unavailable selected", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }

        optionInUse.setOnClickListener {
            Toast.makeText(this, "In Use selected", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }

        // Display the popup window to the left of the ImageView
        //  popupWindow.showAsDropDown(view, -popupView.width, -view.height)
        // popupWindow.showAsDropDown(view, -(popupView.width + 40), -view.height / 2)
        // Measure the popup view to get its actual width and height
        popupView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        val popupWidth = popupView.measuredWidth
        val popupHeight = popupView.measuredHeight

// Get the location of the ImageView on the screen
        val location = IntArray(2)
        view.getLocationOnScreen(location)

// Calculate x and y positions with more space
        val xOffset = location[0] - popupWidth - 40 // Increase the padding, for example to 100dp
        val yOffset = location[1] - (view.height / 2) // Adjust yOffset for vertical alignment

// Show the popup at the calculated position
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xOffset, yOffset)


    }

    private fun startBlinkingAnimation() {
        // Create AlphaAnimation for blinking effect (fade in and fade out)
        // Create AlphaAnimation for blinking effect
        val zoomAnimation = ScaleAnimation(
            1.0f, 2.0f, // Start at normal size, zoom to 150%
            1.0f, 2.0f, // Zoom on both X and Y axis
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point at center (X)
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point at center (Y)
        )
        zoomAnimation.duration = 1500 // Duration of each zoom cycle (1 second)
        zoomAnimation.repeatMode = Animation.REVERSE // Reverse to zoom out after zooming in
        zoomAnimation.repeatCount = Animation.INFINITE // Keep repeating the zoom in/out

        // Start the animation on the ImageView
        blinkimage.startAnimation(zoomAnimation)

    }

    private fun toggleMarkersVisibility() {
        // Toggle the visibility of all markers
        val allVisible = markers.all { it.isVisible }
        for (marker in markers) {
            marker.isVisible = !allVisible
        }

    }

    private fun showImagePickerBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this,R.style.ShoppingList_BottomSheetDialog)

        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_public, null)
        bottomSheetDialog.setContentView(view)

        val chooseCamera = view.findViewById<ConstraintLayout>(R.id.choose_camera)
        val chooseGallery = view.findViewById<ConstraintLayout>(R.id.choose_gallery)
        val cancel = view.findViewById<ConstraintLayout>(R.id.cancel)

        chooseCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkkey = "open"
            startActivity(Intent(this, VehicleSpecificOpenBooking::class.java).putExtra("KEY", checkkey))
        }

        chooseGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkkey = "close"
            startActivity(Intent(this, VehicleSpecificOpenBooking::class.java).putExtra("KEY", checkkey))
        }

        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun toggleMap() {
        if (mMap.mapType == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            imageView.setImageResource(R.drawable.popover_ev) // Change to satellite icon
        } else {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            imageView.setImageResource(R.drawable.popover_ev) // Change back to normal icon
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
                val markerIcon = BitmapFromVector(this, R.drawable.group_427318907_ev, 150, 150)
                currentLocationMarker = mMap.addMarker(MarkerOptions().position(userLocation).icon(markerIcon).title("Current Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                fetchAddressFromLatLng(userLocation)
            } else {
                Toast.makeText(this, "Unable to find current location", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to get location: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("IphoneMap", "Location failure", e)
        }
        group1C.isVisible = true
        current_loctionsC.isVisible = true
        recyclerView.isVisible = false
        topCenterText.isVisible = true
        isInCurrentLocationMode = true
        topCenterText.isClickable=true






    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Define marker locations
        val nakaseroLocation = LatLng(0.323334, 32.578890) // Marker with title
        val otherLocations = listOf(
            LatLng(15.5527, 48.5164),
            LatLng(23.8859, 45.0792),
            LatLng(15.5007, 32.5599)
        )


        // Add marker for NAKASERO
        val nakaseroMarkerImage = BitmapFromVector(this, R.drawable.frame_locate_ev, 125, 125)
        val nakaseroMarker = mMap.addMarker(
            MarkerOptions()
                .position(nakaseroLocation)
                .title("NAKASERO")
                .icon(nakaseroMarkerImage)
        )
        markers.add(nakaseroMarker!!)

        // Add other markers
        for (location in otherLocations) {
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .icon(BitmapFromVector(this, R.drawable.frame_locate_ev, 125, 125))
            )
            markers.add(marker!!)
        }
        for (marker in markers) {
            marker.isVisible = false
        }

        // Set marker click listener
        mMap.setOnMarkerClickListener { marker ->
            onMarkerClicked(marker)
            true // Return true to indicate that the event is consumed
        }

        // Move camera to NAKASERO location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nakaseroLocation, 2f))
        // Move camera to NAKASERO location

    }

    private fun onMarkerClicked(marker: Marker)
    {
        // topCenterText.visibility = View.VISIBLE

        isInCurrentLocationMode = false
        if (marker == currentLocationMarker) {
            // Do nothing if the current location marker is clicked
            return
        }
        if (clickedMarker != marker) {
            clickedMarker?.let {
                // Reset the previous marker to the original image
                it.setIcon(BitmapFromVector(this, R.drawable.frame_locate_ev, 125, 125))
                it.isVisible = true // Show the previous marker
            }

            // Hide all other markers except the clicked one
            for (m in markers) {
                if (m != marker) {
                    m.isVisible = false // Hide the other markers
                }
            }

            //
            //
            //  topCenterText.isVisible=true
            group1C.isVisible=false
            current_loctionsC.isVisible=false


            // clickedMarker = marker // Set the currently clicked marker
            Handler(Looper.getMainLooper()).postDelayed({
                // Change back to the original marker icon after 10 seconds
                for (marker in markers) {

                    marker.setIcon(
                        BitmapDescriptorFactory.fromBitmap(bitmapFromLayout(this, R.layout.cluster_marker_green, 125, 125))
                    )
                }
                //selva
                // marker.setIcon(BitmapFromVector(this, R.drawable.layer_3_ev, 125, 125))
            }, 3000) // Delay for 10000 milliseconds (10 seconds)


            // Change the clicked marker icon
            /* marker.setIcon(
                 BitmapFromVector(
                     this,
                     R.drawable.layer_3_ev,
                     125,
                     125)
             ) */// Replace with the new icon

            // Show the RecyclerView
            // Get marker index and update RecyclerView
            val markerIndex = markers.indexOf(marker)
            if (markerIndex != -1) {
                // Update the adapter to show the clicked marker's details
                (recyclerView.adapter as PublicStationMarkerAdapter).setSelectedMarker(markerIndex)

                recyclerView.scrollToPosition(markerIndex)
                Log.d("RVVisBefore", "Visibility: ${recyclerView.visibility}")

                // Ensure we remove any pending posts of delayed visibility changes
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)

                // Set visibility to VISIBLE after delay
                Handler(Looper.getMainLooper()).postDelayed({
                    Log.d("RVVisAfter", "Setting visibility to VISIBLE")

                    recyclerView.visibility = View.VISIBLE
                    Log.d("RVVisibility", "New Visibility: ${recyclerView.visibility}")
                    Log.d("AdapterItemCount", "Item count: ${recyclerView.adapter?.itemCount}")
                }, 3000) // Delay for 5000 milliseconds (5 seconds)
                Handler(Looper.getMainLooper()).postDelayed({
                    //  Log.d("RVVisAfter", "Setting visibility to VISIBLE")

                    topCenterText.visibility = View.VISIBLE
                    //  Log.d("RVVisibility", "New Visibility: ${recyclerView.visibility}")
                    // Log.d("AdapterItemCount", "Item count: ${recyclerView.adapter?.itemCount}")
                }, 3000) // Delay for 5000 milliseconds (5 seconds)

            }

            //   recyclerView.visibility = View.VISIBLE
            // Center the camera on the clicked marker

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))

            // Delay showing the marker image change and RecyclerView visibility
            /* timer(initialDelay = 50000, period = 50000) {
                runOnUiThread {
                    // This will keep the RecyclerView and marker visible
                    recyclerView.visibility = View.VISIBLE
                }
        }*/
        }


    }

    private fun BitmapFromVector(context: Context, vectorResId: Int, width: Int, height: Int): BitmapDescriptor? {
        val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

        // Create a bitmap from the vector drawable
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        // Scale the bitmap to the specified width and height
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)

        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    }

    private fun fetchAddressFromLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0].getAddressLine(0)
               // Toast.makeText(this, "Address: $address", Toast.LENGTH_SHORT).show()
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
