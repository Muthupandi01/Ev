package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R

import com.example.heptotech.activity_view.AvailablityBottomsheet
//import com.example.heptotech.activity_view.AvailablityBottomsheet
//import com.example.heptotech.activity_view.AvailablityBottomsheet
import com.example.heptotech.activity_view.ConnectToEvActivity
import com.example.heptotech.adapters.SiteAddAdapter
import com.example.heptotech.bean_dataclass.SiteAdd
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class MapMakrAftersave : AppCompatActivity(), OnMapReadyCallback {
    lateinit var addsitelnr: LinearLayout
    lateinit var locationupdate: LinearLayout

    lateinit var toggle_switch: Switch
    lateinit var siteRecyclerview: RecyclerView
    lateinit var chargerbtns: ConstraintLayout
    lateinit var addsite: ConstraintLayout
    lateinit var btn_submit: TextView

    lateinit var greygreentint: LinearLayout

    lateinit var txteditlocationdetails: TextView
    private lateinit var addSiteLauncher: ActivityResultLauncher<Intent>
    lateinit var menu: ImageView
    private lateinit var mMap: GoogleMap
    private lateinit var mapimgdummy: ImageView
    var checked: Boolean = true // Default value, updated based on your logic

    //bottomsheet
    lateinit var other: LinearLayout
    lateinit var price: LinearLayout
    lateinit var access: LinearLayout
    lateinit var availablity: LinearLayout
    lateinit var config: LinearLayout

   lateinit var lntsttime:LinearLayout
   lateinit var providesttime:EditText
   lateinit var conformbtn:TextView
   lateinit var tickstime:ImageView
   lateinit var reservetxt:TextView
   lateinit var booknewslottxt:TextView
   lateinit var child:LinearLayout

    lateinit var notplugedlayout: LinearLayout
    lateinit var nooperatorassignedlay: LinearLayout
    lateinit var onlinecard: LinearLayout
    lateinit var orangependingCard: CardView
    lateinit var greenChargingCard: CardView
    lateinit var maincard:CardView
    lateinit var homechargeblue:LinearLayout
    lateinit var neverConnected:RelativeLayout


    lateinit var editIcon:ImageView



    lateinit var leftImageView:ImageView
    lateinit var rightImageView:ImageView
    lateinit var firstTextView:TextView
    lateinit var showBottomsheteye:ImageView

    private var currentMonth: Int = 8 // Starting with August (0 = January, 11 = December)
    private val maxMonth = 11 // Maximum month index for December
    private val minMonth = 0  // Minimum month index for January

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var myEdit: SharedPreferences.Editor

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map_makr_aftersave)



        // Initialize views
        addsitelnr = findViewById(R.id.addsitelnr)
        locationupdate = findViewById(R.id.locationupdate)
        greygreentint = findViewById(R.id.greygreentint)
        txteditlocationdetails = findViewById(R.id.txteditlocationdetails)
        menu = findViewById(R.id.menu)
        mapimgdummy = findViewById(R.id.mapimgdummy)
        providesttime = findViewById(R.id.providesttime)
        lntsttime = findViewById(R.id.lntsttime)
        conformbtn = findViewById(R.id.conformbtn)
        tickstime = findViewById(R.id.tickstime)
        reservetxt = findViewById(R.id.reservetxt)
        child = findViewById(R.id.child)
        booknewslottxt = findViewById(R.id.booknewslottxt)
        leftImageView = findViewById(R.id.leftImageView)
        rightImageView = findViewById(R.id.rightImageView)
        firstTextView = findViewById(R.id.firstTextView)


        notplugedlayout = findViewById(R.id.notplugedlayout)
        nooperatorassignedlay = findViewById(R.id.nooperatorassignedlay)
        onlinecard = findViewById(R.id.onlinecard)
        orangependingCard = findViewById(R.id.orangependingCard)
        greenChargingCard = findViewById(R.id.greenChargingCard)
        maincard = findViewById(R.id.maincard)
        homechargeblue = findViewById(R.id.homechargeblue)
        editIcon = findViewById(R.id.editIcon)
        neverConnected = findViewById(R.id.neverConnected)
        showBottomsheteye = findViewById(R.id.showBottomsheteye)


//        // Retrieving data from SharedPreferences
//        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
//        val s1: String = sharedPreferences.getString("Key", "") ?: ""
//        Log.d("CheckLoggedvalue", "onCreate: "+s1)

        val intent = intent
        val checkSumValue = intent.getStringExtra("Key")
        val checkSumValueCompare = intent.getStringExtra("Key2")

        Log.d("CheckValuethis", "onCreate: "+checkSumValue)

        if (checkSumValue.equals("Home")){
            getDisplayfn()

                 neverConnected.isVisible=false
                notplugedlayout.isVisible=false
                homechargeblue.isVisible=true


        }
        else if (checkSumValue.equals("Office")){
            getDisplayfn()
            neverConnected.isVisible=false
            notplugedlayout.isVisible=false
            homechargeblue.isVisible=false
            nooperatorassignedlay.isVisible=true
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                nooperatorassignedlay.isVisible=false
                onlinecard.isVisible=true
            }


        }else if (checkSumValue.equals("Office")){

            if (checkSumValueCompare.equals("Manual")){
                getDisplayfn()
                neverConnected.isVisible=false
                notplugedlayout.isVisible=false
                homechargeblue.isVisible=false
                nooperatorassignedlay.isVisible=true
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    nooperatorassignedlay.isVisible=false
                    onlinecard.isVisible=true
                }
            }else{
                getDisplayfn()
                neverConnected.isVisible=false
                notplugedlayout.isVisible=false
                homechargeblue.isVisible=false
                nooperatorassignedlay.isVisible=true
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    nooperatorassignedlay.isVisible=false
                    onlinecard.isVisible=true
                }

            }



        }
        else if (checkSumValue.equals("Commercial")){
            getDisplayfn()
            neverConnected.isVisible=false
            notplugedlayout.isVisible=false
            homechargeblue.isVisible=false
            nooperatorassignedlay.isVisible=false
            onlinecard.isVisible=false


            orangependingCard.isVisible=true
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                orangependingCard.isVisible=false
                greenChargingCard.isVisible=true

                delay(1000)
                orangependingCard.isVisible=false
                greenChargingCard.isVisible=false
                maincard.isVisible=true
            }



        }else if (checkSumValue.equals("Day")||checkSumValue.equals("Night")){
            getDisplayfn()
            neverConnected.isVisible=false
            notplugedlayout.isVisible=false
            homechargeblue.isVisible=false
            nooperatorassignedlay.isVisible=false
            onlinecard.isVisible=false


            orangependingCard.isVisible=true
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                orangependingCard.isVisible=false
                greenChargingCard.isVisible=true

                delay(1000)
                orangependingCard.isVisible=false
                greenChargingCard.isVisible=false
                maincard.isVisible=true
            }




        }else if (checkSumValue.equals("Manual")){
            getDisplayfn()
            neverConnected.isVisible=false
            notplugedlayout.isVisible=false
            homechargeblue.isVisible=false
            nooperatorassignedlay.isVisible=true
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                nooperatorassignedlay.isVisible=false
                onlinecard.isVisible=true
            }

        }else{
            GlobalScope.launch(Dispatchers.Main) {
                delay(3000)
                neverConnected.isVisible=false
                notplugedlayout.isVisible=true

            }
        }








        showBottomsheteye.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.bottomsheet_commercial_device, null)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }


        // Initial month display
        updateMonthDisplay()
        leftImageView.setOnClickListener {
            if (currentMonth > minMonth) {
                currentMonth--
                updateMonthDisplay()
            }
        }

        rightImageView.setOnClickListener {
            if (currentMonth < maxMonth) {
                currentMonth++
                updateMonthDisplay()
            }
        }


        editIcon.setOnClickListener {
            val intent = Intent(this@MapMakrAftersave, ConnectToEvActivity::class.java)
            startActivity(intent)

        }




        booknewslottxt.setOnClickListener {
            booknewslottxt.isVisible=false
            child.isVisible=true
        }

        providesttime.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_sttime, null)
            bottomSheetDialog.setContentView(view)
            // Initialize NumberPickers for hours, minutes, and AM/PM
            val header = view.findViewById<TextView>(R.id.header)
            val conformbtns = view.findViewById<TextView>(R.id.conformbtn)
            val hourPicker = view.findViewById<NumberPicker>(R.id.hourPicker)
            val minutePicker = view.findViewById<NumberPicker>(R.id.minutePicker)
            val amPmPicker = view.findViewById<NumberPicker>(R.id.amPmPicker)

            // Set values for hour picker (1 to 12)
            hourPicker.minValue = 1
            hourPicker.maxValue = 12
            hourPicker.wrapSelectorWheel = true

            // Set values for minute picker (0 to 59)
            minutePicker.minValue = 0
            minutePicker.maxValue = 59
            minutePicker.wrapSelectorWheel = true
            minutePicker.setFormatter { i -> String.format("%02d", i) } // Show 2 digits

            // Set values for AM/PM picker (0 = AM, 1 = PM)
            amPmPicker.minValue = 0
            amPmPicker.maxValue = 1
            amPmPicker.displayedValues = arrayOf("AM", "PM")
            amPmPicker.wrapSelectorWheel = true

            // Set listeners for pickers (optional, for handling user selections)
            hourPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                // Do something with the selected hour
            }

            minutePicker.setOnValueChangedListener { picker, oldVal, newVal ->
                // Do something with the selected minute
            }

            amPmPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                // Do something with the selected AM/PM
                val selectedAmPm = if (newVal == 0) "AM" else "PM"
               // Toast.makeText(this, "Selected: $selectedAmPm", Toast.LENGTH_SHORT).show()
            }

            header.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

//            conformbtns.setOnClickListener {
//                bottomSheetDialog.dismiss()
//                conformbtn.setBackgroundResource(R.drawable.rectangle_34624614_green_ev)
//
//            }


            conformbtns.setOnClickListener {
                // Get selected values from pickers
                val selectedHour = hourPicker.value
                val selectedMinute = minutePicker.value
                val selectedAmPm = if (amPmPicker.value == 0) "AM" else "PM"

                // Format the time as "HH:mm AM/PM"
                val formattedTime = String.format("%02d:%02d %s", selectedHour, selectedMinute, selectedAmPm)

                // Do something with the formatted time (e.g., store or display it)
                providesttime.setText(formattedTime) // Display the formatted time in the button


                bottomSheetDialog.dismiss()

                // Change button background to green after selection
                conformbtn.setBackgroundResource(R.drawable.rectangle_34624614_green_ev)
            }

            bottomSheetDialog.show()
        }

        conformbtn.setOnClickListener {
            if (providesttime.text.toString().length>0){
                tickstime.isVisible=true
                reservetxt.isVisible=true
                conformbtn.setBackgroundResource(R.drawable.rectangle_34624614_red_ev)
                conformbtn.text="Cancel"
            }

        }












        // Initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        GlobalScope.launch(Dispatchers.Main) {
                        // Step 1: Initial state
                        delay(2000)
            greygreentint.setBackgroundTintList(ContextCompat.getColorStateList(this@MapMakrAftersave, R.color.greentxt))
            addsitelnr.isVisible = false
            mapimgdummy.isVisible = false
            notplugedlayout.isVisible = false
            // nooperatorassignedlay.isVisible = true
            txteditlocationdetails.text = "Edit Location details"
            menu.isVisible = true
        }

        // Site address launcher
        addSiteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {


                    val siteName = data.getStringExtra("site_name")
                    val siteAddress = data.getStringExtra("site_address")

                    // Update the UI here
                    greygreentint.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.greentxt))
                    addsitelnr.isVisible = false
                    mapimgdummy.isVisible = false
                    notplugedlayout.isVisible = false
                   // nooperatorassignedlay.isVisible = true
                    txteditlocationdetails.text = "Edit Location details"
                    menu.isVisible = true
//                    GlobalScope.launch(Dispatchers.Main) {
//                        // Step 1: Initial state
//                        delay(500)
//                        notplugedlayout.isVisible = false
//                        nooperatorassignedlay.isVisible = true
//
//                        // Step 2: Show the next layout after 1 second
//                        delay(1500)
//                        nooperatorassignedlay.isVisible = false
//                        onlinecard.isVisible = true
//
//                        // Step 3: Show the third layout after another 1 second
//                        delay(1500)
//                        onlinecard.isVisible = false
//                        orangependingCard.isVisible = true
//
//                        // Step 4: Show the fourth layout after another 1 second
//                        delay(1500)
//                        orangependingCard.isVisible = false
//                        greenChargingCard.isVisible = true
//
//                        // Step 5: Show the fifth layout after another 1 second
//                        delay(1500)
//                        greenChargingCard.isVisible = false
//                        homechargeblue.isVisible = true
//
//                        // Step 6: Show the sixth layout after another 1 second
//                        delay(1500)
//                        homechargeblue.isVisible = false
//                        maincard.isVisible = true
//
//                    }
                    updateMapVisibility()
                }
            }
        }

        // Launch Add Site Activity
        addsitelnr.setOnClickListener {

            addsitefn()
//            val intent = Intent(this@MapMakrAftersave, AddsiteAdddresAdd::class.java)
//            addSiteLauncher.launch(intent)


        }

        // Bottom sheet menu
        menu.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_chargemenus, null)
            bottomSheetDialog.setContentView(view)


            other = view.findViewById(R.id.other)
            price = view.findViewById(R.id.price)
            access = view.findViewById(R.id.access)
            availablity = view.findViewById(R.id.availablity)
            config = view.findViewById(R.id.config)

            other.setOnClickListener {
                val intent = Intent(this@MapMakrAftersave, OtherDevicesChargestation::class.java)
                startActivity(intent)
                bottomSheetDialog.dismiss()
            }

            availablity.setOnClickListener {
                val intent = Intent(this@MapMakrAftersave,AvailablityBottomsheet::class.java)
                startActivity(intent)
                bottomSheetDialog.dismiss()

            }

            price.setOnClickListener {
                val intent = Intent(this@MapMakrAftersave, PriceBottomSheet::class.java)
                startActivity(intent)
                bottomSheetDialog.dismiss()
            }

            access.setOnClickListener {
                val intent = Intent(this@MapMakrAftersave, AccessBottomsheet::class.java)
                startActivity(intent)
                bottomSheetDialog.dismiss()
            }

            config.setOnClickListener {
                val intent = Intent(this@MapMakrAftersave, UserModeCharge::class.java)
                startActivity(intent)
                bottomSheetDialog.dismiss()
            }


            bottomSheetDialog.show()
        }

        // Location update logic
        locationupdate.setOnClickListener {

            addsitefn()

        }
    }

    private fun getDisplayfn() {
        greygreentint.setBackgroundTintList(ContextCompat.getColorStateList(this@MapMakrAftersave, R.color.greentxt))
        addsitelnr.isVisible = false
        mapimgdummy.isVisible = false
        notplugedlayout.isVisible = false
        txteditlocationdetails.text = "Edit Location details"
        menu.isVisible = true
    }


    private fun updateMonthDisplay() {
        val calendar = java.util.Calendar.getInstance()
        val currentMonthValue = calendar.get(java.util.Calendar.MONTH)

        val monthName = calendar.apply {
            set(java.util.Calendar.MONTH, currentMonth)
        }.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, Locale.getDefault())

        firstTextView.text = monthName+" Overview"

        // Disable the right arrow if the currentMonth is the current month
        rightImageView.isEnabled = currentMonth < maxMonth && currentMonth < currentMonthValue
        leftImageView.isEnabled = currentMonth > minMonth
    }


    private fun addsitefn() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_choosesite, null)
        bottomSheetDialog.setContentView(view)

        toggle_switch = view.findViewById(R.id.toggle_switch)
        siteRecyclerview = view.findViewById(R.id.siteRecyclerview)
        chargerbtns = view.findViewById(R.id.chargerbtns)
        addsite = view.findViewById(R.id.addsite)
        btn_submit = view.findViewById(R.id.btn_submit)

        addsite.setOnClickListener {
            val intent = Intent(this@MapMakrAftersave, AddsiteAdddresAdd::class.java)
            addSiteLauncher.launch(intent)
        }

        // Setup RecyclerView
        val siteList = listOf(
            SiteAdd("Evzone Charge Station", "Kampala, Uganda"),
            SiteAdd("Soroti Charge Station", "Soroti, Uganda")
        )
        siteRecyclerview.layoutManager = LinearLayoutManager(this)
        siteRecyclerview.adapter = SiteAddAdapter(siteList)

        // Switch logic
        toggle_switch.setOnCheckedChangeListener { _, isChecked ->
            chargerbtns.isVisible = !isChecked
            addsite.isVisible = !isChecked
        }

        // Submit button
        btn_submit.setOnClickListener {
            bottomSheetDialog.dismiss()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        bottomSheetDialog.show()


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        updateMapVisibility()
    }

    // Function to handle the visibility of elements based on 'checked'
    private fun updateMapVisibility() {
        val defaultLocation = LatLng(0.323334, 32.578890)
        mMap.addMarker(MarkerOptions().position(defaultLocation).title("NAKASERO"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
    }
}
