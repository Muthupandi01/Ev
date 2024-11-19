package com.example.heptotech.actvity_view

import android.Manifest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
//import android.graphics.Color
import android.graphics.PorterDuff
//import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
//import android.os.Handler
import android.os.Looper
//import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View

import android.widget.AutoCompleteTextView
//import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
//import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapter.FindChargerAdapter
import com.example.heptotech.adapters.BookmarkEvAdapter
import com.example.heptotech.adapters.PublicStationMarkerAdapter
import com.example.heptotech.bean_dataclass.BookmarkEv
import com.example.heptotech.bean_dataclass.FindChargerInfo
import com.example.heptotech.bean_dataclass.MarkerInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pl.droidsonroids.gif.GifImageView
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
    private lateinit var blinkimage: GifImageView
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
    private lateinit var filterCard:CardView
    private var lastClickedMarker: Marker? = null
    private var clickCount = 0
    var isScanTextClicked = false


    //  private lateinit var searchView: androidx.appcompat.widget.SearchView







    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingInflatedId")
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
        filterCard=findViewById(R.id.filterCard)
     //   searchView=findViewById(R.id.idSearchView)
        // left_image = findViewById(R.id.left_angle)
        val searchView = findViewById<TextView>(R.id.idSearchView)
        // val searchTextView = searchView.findViewById<android.widget.TextView>(androidx.appcompat.R.id.search_src_text)


// Make SearchView clickable for custom action and non-editable


        filterCard.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }
        searchView.setOnClickListener()
        {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.ShoppingList_BottomSheetDialog)
            val bottomSheetView = layoutInflater.inflate(R.layout.evcharge_botttomsheet, null)
            val scan_text = bottomSheetView.findViewById<LinearLayout>(R.id.scannerView2)
            val recyle_book= bottomSheetView.findViewById<RecyclerView>(R.id.recycle_bookmark)
            val recycler_ev = bottomSheetView.findViewById<RecyclerView>(R.id.recycle_ev)
            val searchView1 =bottomSheetView.findViewById<androidx.appcompat.widget.SearchView>(R.id.idSearchView1)
            // val searchTextView = searchView.findViewById<android.widget.TextView>(androidx.appcompat.R.id.search_src_text)
            val searchAutoComplete1 =searchView1.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)

            searchAutoComplete1?.let {
                // it.inputType = InputType.TYPE_NULL  // Prevent keyboard from opening
                //  it.isFocusable = false
                // it.isClickable = true
                // it.isFocusableInTouchMode = false
                it.textSize = 12f // Adjust text size in SP
                it.setTextColor(ContextCompat.getColor(this, R.color.black))
                val cursorDrawable = ContextCompat.getDrawable(this, R.drawable.custom_cursor) // Replace with your custom cursor drawable
                searchAutoComplete1.textCursorDrawable = cursorDrawable
            }
            recycler_ev.isVisible=true
            recyle_book.isVisible=false
            scan_text.setOnClickListener()
            {

                if (isScanTextClicked) {
                    // Restore to normal state
                    recycler_ev.visibility = View.VISIBLE
                    recyle_book.visibility = View.GONE

                    // Restore the background color of the scanner view
                    val scanImage = bottomSheetView.findViewById<LinearLayout>(R.id.scannerView2)
                        .findViewById<ImageView>(R.id.book)
                    scanImage.setColorFilter(
                        ContextCompat.getColor(this, R.color.black),
                        PorterDuff.Mode.SRC_IN
                    )

                    // Replace with your normal color

                    // Restore the ImageView color to the normal state

                    scan_text.setBackgroundResource(


                        R.drawable.borderwhite_ev

                    )


                    isScanTextClicked = false
                } else {
                    // Switch to clicked state
                    recycler_ev.visibility = View.GONE
                    recyle_book.visibility = View.VISIBLE


                    // Restore the ImageView color to the normal state

                    scan_text.setBackgroundResource(


                        R.drawable.border_line

                    )

                    // Change the ImageView color to white
                    val scanImage = bottomSheetView.findViewById<LinearLayout>(R.id.scannerView2)
                        .findViewById<ImageView>(R.id.book)
                    scanImage.setColorFilter(
                        ContextCompat.getColor(this, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )


                    // Change the background color of the scanner view to red

                    isScanTextClicked = true
                }
            }
            recyle_book.layoutManager = LinearLayoutManager(this)
            val dataList1 = listOf(
                BookmarkEv("Akibua Rd Point", "22,Akii Bus Road,\nKambala 22,Akii Bus", 4.5f, "1/4 Available", "Type 2\n22Kw AC", "Type 2\n22Kw AC", "Type 3\n22Kw AC","200 Km",R.drawable.evbottomimg_ev,R.drawable.heart_ev),
                BookmarkEv("Another Point", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "2/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize_ev,R.drawable.heart_ev))

            val adapter1 = BookmarkEvAdapter(dataList1) {

                val intent = Intent(this, PubicstationCard::class.java)
                startActivity(intent)
            }
            recyle_book.adapter=adapter1
            // Retrieve RecyclerView from the inflated bottomSheetView
            recycler_ev.layoutManager = LinearLayoutManager(this)
            // Sample data
            val dataList = listOf(
                FindChargerInfo("Akibua Rd Point", "22,Akii Bus Road,\nKambala 22,Akii Bus", 4.5f, "1/4 Available", "Type 2\n22Kw AC", "Type 2\n22Kw AC", "Type 3\n22Kw AC","200 Km",R.drawable.evbottomimg_ev),
                FindChargerInfo("Another Point", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "2/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize_ev),
                FindChargerInfo("Charger Point", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "3/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize1_ev),
                FindChargerInfo("Kambala city", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "4/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize3_ev))
            // Set up the adapter
            val adapter = FindChargerAdapter(dataList)
            {
                val intent = Intent(this,PubicstationCard::class.java)
                startActivity(intent)
            }
            recycler_ev.adapter = adapter
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.behavior.isDraggable = true  // Optional: to prevent dragging
            bottomSheetDialog.behavior.peekHeight = resources.displayMetrics.heightPixels
            bottomSheetDialog.show()
        }
       /* searchView.setOnClickListener()
        {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.ShoppingList_BottomSheetDialog)
            val bottomSheetView = layoutInflater.inflate(R.layout.evcharge_botttomsheet, null)
            val scan_text = bottomSheetView.findViewById<LinearLayout>(R.id.scannerView2)
            val recyle_book= bottomSheetView.findViewById<RecyclerView>(R.id.recycle_bookmark)
            val recycler_ev = bottomSheetView.findViewById<RecyclerView>(R.id.recycle_ev)
            val searchView1 =bottomSheetView.findViewById<androidx.appcompat.widget.SearchView>(R.id.idSearchView1)
            // val searchTextView = searchView.findViewById<android.widget.TextView>(androidx.appcompat.R.id.search_src_text)
            val searchAutoComplete1 =searchView1.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)

            searchAutoComplete1?.let {
               // it.inputType = InputType.TYPE_NULL  // Prevent keyboard from opening
              //  it.isFocusable = false
               // it.isClickable = true
               // it.isFocusableInTouchMode = false
                it.textSize = 12f // Adjust text size in SP
                it.setTextColor(ContextCompat.getColor(this, R.color.black))
                val cursorDrawable = ContextCompat.getDrawable(this, R.drawable.custom_cursor) // Replace with your custom cursor drawable
                searchAutoComplete1.textCursorDrawable = cursorDrawable
            }
            recycler_ev.isVisible=true
            recyle_book.isVisible=false
            scan_text.setOnClickListener()
            {

                if (isScanTextClicked) {
                    // Restore to normal state
                    recycler_ev.visibility = View.VISIBLE
                    recyle_book.visibility = View.GONE

                    // Restore the background color of the scanner view
                    // Restore the background color of the scanner view
                    val scanImage = bottomSheetView.findViewById<LinearLayout>(R.id.scannerView2)
                        .findViewById<ImageView>(R.id.book)
                    scanImage.setColorFilter(
                        ContextCompat.getColor(this, R.color.black),
                        PorterDuff.Mode.SRC_IN)/


                    isScanTextClicked = false
                } else {
                    // Switch to clicked state
                    recycler_ev.visibility = View.GONE
                    recyle_book.visibility = View.VISIBLE
                    scan_text.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    ) // Replace with your normal color

                    // Restore the ImageView color to the normal state
                    val scanImage = bottomSheetView.findViewById<LinearLayout>(R.id.scannerView2)
                        .findViewById<ImageView>(R.id.book)
                    scanImage.setColorFilter(
                        ContextCompat.getColor(this, R.color.black),
                        PorterDuff.Mode.SRC_IN
                    )

                    // Change the background color of the scanner view to red

                    isScanTextClicked = true
                }
            }
            recyle_book.layoutManager = LinearLayoutManager(this)
            val dataList1 = listOf(
                BookmarkEv("Akibua Rd Point", "22,Akii Bus Road,\nKambala 22,Akii Bus", 4.5f, "1/4 Available", "Type 2\n22Kw AC", "Type 2\n22Kw AC", "Type 3\n22Kw AC","200 Km",R.drawable.evbottomimg_ev,R.drawable.heart_ev),
                BookmarkEv("Another Point", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "2/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize_ev,R.drawable.heart_ev))

            val adapter1 = BookmarkEvAdapter(dataList1) {

                val intent = Intent(this, PubicstationCard::class.java)
                startActivity(intent)
            }
            recyle_book.adapter=adapter1
            // Retrieve RecyclerView from the inflated bottomSheetView
            recycler_ev.layoutManager = LinearLayoutManager(this)
            // Sample data
            val dataList = listOf(
                FindChargerInfo("Akibua Rd Point", "22,Akii Bus Road,\nKambala 22,Akii Bus", 4.5f, "1/4 Available", "Type 2\n22Kw AC", "Type 2\n22Kw AC", "Type 3\n22Kw AC","200 Km",R.drawable.evbottomimg_ev),
                FindChargerInfo("Another Point", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "2/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize_ev),
                FindChargerInfo("Charger Point", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "3/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize1_ev),
                FindChargerInfo("Kambala city", "Akii Bus Road,\n" +
                        "Kambala 22,Akii Bus", 3.5f, "4/4 Available", "Type 2\n22Kw AC", "Type 3\n22Kw AC", "Type 3\n22Kw AC","100 Km",R.drawable.evsize3_ev))
            // Set up the adapter
            val adapter = FindChargerAdapter(dataList)
            {
                val intent = Intent(this,PubicstationCard::class.java)
                startActivity(intent)
            }
            recycler_ev.adapter = adapter
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.behavior.isDraggable = true  // Optional: to prevent dragging
            bottomSheetDialog.behavior.peekHeight = resources.displayMetrics.heightPixels
            bottomSheetDialog.show()
        }*/

        blinkimage = findViewById(R.id.blink_img)
        blinkimage.setImageResource(R.drawable.record_gif)
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
           // clickedMarker = null
            lastClickedMarker = null // Reset the last clicked marker
            clickCount = 0 // Reset click count


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

    /*private fun startBlinkingAnimation() {
        // Initialize the ImageView
        val blinkimage = findViewById<ImageView>(R.id.blink_img) // Replace with your actual ImageView ID

        // Create a ValueAnimator for the zooming effect on both X and Y axes
        val scaleX = ValueAnimator.ofFloat(1.0f, 2.0f)
        val scaleY = ValueAnimator.ofFloat(1.0f, 2.0f)

        // Set duration for one complete cycle of zoom
        scaleX.duration = 1500 // 1.5 seconds for one zoom in/out cycle
        scaleY.duration = 1500 // 1.5 seconds for one zoom in/out cycle

        // Set repeat mode and count for each ValueAnimator
        scaleX.repeatMode = ValueAnimator.REVERSE // Reverse the animation after each cycle
        scaleX.repeatCount = ValueAnimator.INFINITE // Repeat indefinitely

        scaleY.repeatMode = ValueAnimator.REVERSE // Reverse the animation after each cycle
        scaleY.repeatCount = ValueAnimator.INFINITE // Repeat indefinitely

        // Add an update listener to apply the animated values to the ImageView
        scaleX.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            blinkimage.scaleX = value
        }

        scaleY.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            blinkimage.scaleY = value
        }

        // Create an AnimatorSet to play both scaleX and scaleY animations together
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)

        // Start the animation
        animatorSet.start()

    }*/

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

        // Try to get the last known location immediately
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                handleNewLocation(location)
            } else {
                // No last known location, so start requesting updates
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 1000 // Request location every second
            fastestInterval = 500 // Get updates no more frequently than every half second
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.firstOrNull()?.let { location ->
                    handleNewLocation(location)
                }
            }
        }, Looper.getMainLooper())
    }

    private fun handleNewLocation(location: Location) {
        // Use the location to update the map
        val userLocation = LatLng(location.latitude, location.longitude)
        val markerIcon = BitmapFromVector(this, R.drawable.group_427318907_ev, 150, 150)

        currentLocationMarker?.remove()  // Remove previous marker, if any
        currentLocationMarker = mMap.addMarker(MarkerOptions().position(userLocation).icon(markerIcon).title("Current Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
        fetchAddressFromLatLng(userLocation)

        // Update UI elements
        group1C.isVisible = true
        current_loctionsC.isVisible = true
        recyclerView.isVisible = false
        topCenterText.isVisible = true
        isInCurrentLocationMode = true
        topCenterText.isClickable = true
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

      /*  isInCurrentLocationMode = false
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
        }*/
        if (marker == currentLocationMarker) {
            return
        }

        // First click: Show only the clicked marker and RecyclerView
        if (lastClickedMarker != marker) {
            // Reset the icon of the previously clicked marker if needed
            lastClickedMarker?.setIcon(BitmapFromVector(this, R.drawable.frame_locate_ev, 125, 125))

            // Hide all other markers except the clicked one
            for (m in markers) {
                m.isVisible = m == marker
            }
            group1C.isVisible=false
            current_loctionsC.isVisible=false
            topCenterText.isVisible=true

            // Set the current marker as the last clicked one and reset click count
            lastClickedMarker = marker
            clickCount = 1

            // Make RecyclerView visible
          //  recyclerView.visibility = View.VISIBLE
            val markerIndex = markers.indexOf(marker)
            if (markerIndex != -1) {
                // Update RecyclerView with clicked marker's details
                (recyclerView.adapter as PublicStationMarkerAdapter).setSelectedMarker(markerIndex)
                recyclerView.scrollToPosition(markerIndex)
            }

            // Move the camera to the clicked marker
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
        } else {
            // Second click on the same marker: Change the icon to cluster_marker_green
            if (clickCount == 1) {
                marker.setIcon(
                    BitmapDescriptorFactory.fromBitmap(bitmapFromLayout(this, R.layout.cluster_marker_green, 125, 125))
                )
                clickCount = 2
                recyclerView.visibility = View.VISIBLE

            }

            // Ensure RecyclerView is still visible

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
            Log.e("callocation",e.toString())
            Toast.makeText(this, "Unable to fetch address", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
