package com.example.heptotech.actvity_view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.heptotech.R
import com.example.heptotech.fragment.ChooseDateFragment
import com.example.heptotech.fragment.ChooseDatesFragment


class IphoneRouteplan : AppCompatActivity() {
    private lateinit var locationEditText: EditText
    private lateinit var destinationEditText: EditText
    private lateinit var routelinear: LinearLayout
    private lateinit var rootTime: LinearLayout
    private lateinit var switchText: TextView
    private lateinit var chooseDate: LinearLayout
    private lateinit var chooseDate_time: TextView
    private lateinit var Ride_later: TextView
    private lateinit var Ride_now: TextView
    private var isRideLaterSelected = true
    private var isRideNowSelected = false
    private lateinit var oneWayLayout: LinearLayout
    private lateinit var returnJourneyLayout: LinearLayout
    private lateinit var oneWayImage: ImageView
    private lateinit var returnJourneyImage: ImageView
    private lateinit var oneWayText: TextView
    private lateinit var returnJourneyText: TextView
    private lateinit var radio_search: ImageView
    private lateinit var location_svg: ImageView
    private lateinit var roottimes: LinearLayout
    private lateinit var Ride_late: TextView
    private lateinit var choose_dates:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iphone_promax)

        locationEditText = findViewById(R.id.search_text)
        destinationEditText = findViewById(R.id.your_destination)
        routelinear = findViewById(R.id.route_plan)
        rootTime = findViewById(R.id.linear_time)
        roottimes = findViewById(R.id.linear_times)
        switchText = findViewById(R.id.switch_text)
        chooseDate = findViewById(R.id.linear_choose)
        chooseDate_time = findViewById(R.id.choose_date)
        Ride_later = findViewById(R.id.Ride_later)
        Ride_now = findViewById(R.id.ride_now)
        oneWayLayout = findViewById(R.id.one_way)
        returnJourneyLayout = findViewById(R.id.return_journey)
        oneWayImage = findViewById(R.id.one_Radio)
        returnJourneyImage = findViewById(R.id.return_image)
        oneWayText = findViewById(R.id.one_way_text)
        returnJourneyText = findViewById(R.id.return_text)
        radio_search = findViewById(R.id.radio_search)
        location_svg = findViewById(R.id.location_svg)
        choose_dates = findViewById(R.id.choose_dates)

        val searchImageRes = R.drawable.group_427318913_ev
        val locationImageRes = R.drawable.location_pin_ev

        oneWayLayout.setOnClickListener {
            selectOneWay()
        }

        returnJourneyLayout.setOnClickListener {
            selectReturnJourney()


        }

        // Initial selection
        selectOneWay() // B


        // Listener for focus changes on locationEditText
        locationEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus || locationEditText.text.isNotEmpty()) {
                locationEditText.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.times_circle__3__ev,
                    0
                )
            } else {
                locationEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }

        // Listener for focus changes on destinationEditText
        destinationEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus || destinationEditText.text.isNotEmpty()) {
                destinationEditText.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.times_circle__3__ev,
                    0
                )
            } else {
                destinationEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }

        // TextWatcher for both EditTexts
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update the background of routelinear if either EditText contains text
                if (!locationEditText.text.isNullOrEmpty() || !destinationEditText.text.isNullOrEmpty()) {
                    routelinear.setBackgroundResource(R.drawable.group_427318938_ev)  // Set your drawable here
                } else {
                    routelinear.setBackgroundResource(R.drawable.group_427318939_ev)  // Set back to original if no text
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed in this case
            }
        }

        // Add the TextWatcher to both EditTexts
        locationEditText.addTextChangedListener(textWatcher)
        destinationEditText.addTextChangedListener(textWatcher)
        var isSwitched = false
        switchText.setOnClickListener { v: View? ->

            // Toggle visibility of rootTime

            if (isSwitched) {
                // Switch to original state
                locationEditText.hint = "Search Location"
                destinationEditText.hint = "Your Destination"

                // Switch images back
                radio_search.setImageResource(searchImageRes)
                location_svg.setImageResource(locationImageRes)

            } else {
                // Switch to new state
                locationEditText.hint = "Your Destination"
                destinationEditText.hint = "Search Location"

                // Swap images
                radio_search.setImageResource(locationImageRes)
                location_svg.setImageResource(searchImageRes)
            }

            // Toggle the flag
            isSwitched = !isSwitched

            // Ensure all views are not null


        }
        chooseDate.setOnClickListener {
            // Open the BottomSheetFragment
            val bottomSheetFragment = ChooseDateFragment()
            bottomSheetFragment.show(supportFragmentManager, "ChooseDateFragment")
        }
        choose_dates.setOnClickListener {

            val bottomSheetFragment = ChooseDatesFragment()
            bottomSheetFragment.show(supportFragmentManager, "ChooseDatesFragment")
        }

        Ride_later.setOnClickListener {
            selectRideLater()
        }

        // Ride_now click listener
        Ride_now.setOnClickListener {
            selectRideNow()
        }
    }

    private fun selectReturnJourney() {
        returnJourneyLayout.setBackgroundResource(R.drawable.rectangle_34624357) // Your selected background drawable
        returnJourneyImage.setImageResource(R.drawable.group_427318914_ev) // Change image on selection
        returnJourneyText.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.return_journey
            )
        ) // Your selected color

        // Reset one-way layout
        oneWayLayout.setBackgroundResource(
            R.drawable.rectangle_34624359_ev
        ) // Your default background drawable
        oneWayImage.setImageResource(R.drawable.group_427318916_ev) // Reset image to default
        oneWayText.setTextColor(ContextCompat.getColor(this, R.color.one_way))
        if (roottimes.visibility == View.GONE) {
            roottimes.visibility = View.VISIBLE
        } else {
            roottimes.visibility = View.GONE
        }// Reset text color

    }





// Call the function with the desired visibility condition



    private fun selectOneWay() {
        oneWayLayout.setBackgroundResource(R.drawable.rectangle_34624357) // Your selected background drawable
        oneWayImage.setImageResource(R.drawable.group_427318914_ev) // Change image on selection
        oneWayText.setTextColor(ContextCompat.getColor(this, R.color.return_journey)) // Your selected color

        // Reset return journey layout
        returnJourneyLayout.setBackgroundResource(R.drawable.rectangle_34624359_ev) // Your default background drawable
        returnJourneyImage.setImageResource(R.drawable.group_427318916_ev) // Reset image to default
        returnJourneyText.setTextColor(ContextCompat.getColor(this, R.color.one_way))
    // Reset text color
        roottimes.visibility = View.GONE
    }

    private fun selectRideNow()
    {
        isRideNowSelected = true
        isRideLaterSelected = false

        Ride_now.setBackgroundResource(R.drawable.rectangle_34624355__1_ev) // Selected drawable
        Ride_now.setTextColor(ContextCompat.getColor(this, R.color.white)) // Selected text color

        // Reset Ride_later appearance
        Ride_later.setBackgroundResource(R.drawable.rectangle_34624355_ev) // Default drawable
        Ride_later.setTextColor(ContextCompat.getColor(this, R.color.ride_later))
    // Default text color
        rootTime.visibility = View.GONE


    }

    private fun selectRideLater() {
        isRideLaterSelected = true
        isRideNowSelected = false

        Ride_later.setBackgroundResource(R.drawable.rectangle_34624355__1_ev) // Selected drawable
        Ride_later.setTextColor(ContextCompat.getColor(this, R.color.white)) // Selected text color

        // Reset Ride_now appearance
        Ride_now.setBackgroundResource(R.drawable.rectangle_34624355_ev) // Default drawable
        Ride_now.setTextColor(ContextCompat.getColor(this, R.color.ride_later))
    // Default text color
        if (rootTime.visibility == View.GONE) {
            rootTime.visibility = View.VISIBLE
        } else {
            rootTime.visibility = View.GONE
        }
    }

}
