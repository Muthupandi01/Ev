package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.StopsAdapter
import com.example.heptotech.adapters.VechicleUserAdapter
import com.example.heptotech.bean_dataclass.User
import com.example.heptotech.bean_dataclass.VechicleUserItem
import com.example.heptotech.fragment.ChooseDateFragment
import com.example.heptotech.fragment.ChooseDatesFragment
import com.google.android.material.bottomsheet.BottomSheetDialog


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
    private lateinit var choose_dates: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StopsAdapter
    private val stops: MutableList<String> = mutableListOf()
    private var nextStopNumber = 1
    private lateinit var radioSearchImageView: ImageView
    private lateinit var locationPinImageView: ImageView
    private var hasAddedStop = false
    private var isSwitched = false

    @SuppressLint("MissingInflatedId")
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
        location_svg = findViewById(R.id.locations_svg)
        choose_dates = findViewById(R.id.choose_dates)
        val avoidTolls = findViewById<LinearLayout>(R.id.avoid_tolls)
        val tollImage = avoidTolls.findViewById<ImageView>(R.id.tolls_image)

        val moterWays = findViewById<LinearLayout>(R.id.avoid_motor)
        val moterImg = moterWays.findViewById<ImageView>(R.id.motor_img)
        val avoid_ferries = findViewById<LinearLayout>(R.id.avoid_ferr)
        val ferrisImg  = avoid_ferries.findViewById<ImageView>(R.id.feriies_img)
        val normalImage = R.drawable.ellipse_631_ev    // Normal image
        val selectedImage = R.drawable.check_circle_ev
        var isSelected = false
        routelinear.setOnClickListener()
        {
            val intent = Intent(this, ActivityRoorConform::class.java)
            startActivity(intent)
        }
        avoidTolls.setOnClickListener {

           // tollImage.setImageResource(R.drawable.check_circle_ev)
            if (isSelected) {
                tollImage.setImageResource(selectedImage)  // Set the normal image
            } else {
                tollImage.setImageResource(normalImage)  // Set the selected image
            }
            isSelected = !isSelected
        }
        moterWays.setOnClickListener {


            // tollImage.setImageResource(R.drawable.check_circle_ev)
            if (isSelected) {
                moterImg.setImageResource(selectedImage)  // Set the normal image
            } else {
                moterImg.setImageResource(normalImage)  // Set the selected image
            }
            isSelected = !isSelected
        }
             avoid_ferries.setOnClickListener {


            // tollImage.setImageResource(R.drawable.check_circle_ev)
            if (isSelected) {
                ferrisImg.setImageResource(selectedImage)  // Set the normal image
            } else {
                ferrisImg.setImageResource(normalImage)  // Set the selected image
            }
            isSelected = !isSelected
        }
        recyclerView = findViewById(R.id.stops_recycler_view)
        adapter = StopsAdapter(stops, ::onStopDeleted)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up Add Stop button
        val addStopButton: LinearLayout = findViewById(R.id.add_stop)
        addStopButton.setOnClickListener {
            addStop()
        }

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

            switchText.setOnClickListener { v: View? ->
                if (isSwitched) {
                    // Switch back to original state
                    locationEditText.hint = "Search Location"
                    destinationEditText.hint = "Your Destination"

                    // Check if a stop has been added and switch images accordingly
                    if (hasAddedStop) {
                        // If a stop has been added, use the alternate images
                        radio_search.setImageResource(R.drawable.group_913__1_ev) // New image when stop is added
                        location_svg.setImageResource(R.drawable.location_pin__1_ev) // New image when stop is added
                    } else {
                        // Otherwise, use the default images
                        radio_search.setImageResource(R.drawable.group_427318913_ev) // Default image
                        location_svg.setImageResource(R.drawable.location_pin_ev) // Default image
                    }
                } else {
                    // Switch to the new state (swap the hints)
                    locationEditText.hint = "Your Destination"
                    destinationEditText.hint = "Search Location"

                    // Swap images based on whether a stop has been added
                    if (hasAddedStop) {
                        radio_search.setImageResource(R.drawable.location_pin__1_ev) // Swap image when stop is added
                        location_svg.setImageResource(R.drawable.group_913__1_ev) // Swap image when stop is added
                    } else {
                        radio_search.setImageResource(R.drawable.location_pin_ev) // Default swapped image
                        location_svg.setImageResource(R.drawable.group_427318913_ev) // Default swapped image
                    }
                }

                // Toggle the isSwitched flag for the next click
                isSwitched = !isSwitched
            }


        }

        chooseDate.setOnClickListener {
            // Create and show the BottomSheetDialog
            val bottomSheetDialog = BottomSheetDialog(this,R.style.ShoppingList_BottomSheetDialog)
            val bottomSheetView = layoutInflater.inflate(R.layout.fragment_example_bottom_sheet, null)
            bottomSheetDialog.setContentView(bottomSheetView)
         //   val bottomSheetDialog = BottomSheetDialog(this, R.style.ShoppingList_BottomSheetDialog)
          //  bottomSheetDialog.setContentView(R.layout.fragment_example_bottom_sheet)

            // Find the views in the BottomSheet layout
            val dayPicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.datePicker)
            val monthPicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.monthPicker)
            val yearPicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.yearPicker)
            val hourPicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.time_picker)
            val minutePicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.minute_Picker)
            val amPmPicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.am_PmPicker)
            val doneTextView = bottomSheetDialog.findViewById<TextView>(R.id.done_text)
            val deleteStart = bottomSheetDialog.findViewById<ImageView>(R.id.delete_start)
            // Ensure the NumberPickers are not null
            if (dayPicker != null && monthPicker != null && yearPicker != null &&
                hourPicker != null && minutePicker != null && amPmPicker != null) {

                // Set default values for the date pickers (Day, Month, Year)
                dayPicker.minValue = 1
                dayPicker.maxValue = 31
                dayPicker.value = 1  // Set initial day

                monthPicker.minValue = 1
                monthPicker.maxValue = 12
                monthPicker.displayedValues = resources.getStringArray(R.array.monthstime_array)
                monthPicker.value = 1  // Set initial month

                yearPicker.minValue = 1900
                yearPicker.maxValue = 2100
                yearPicker.value = 2024  // Set initial year

                // Set default values for the time pickers (Hour, Minute, AM/PM)
                hourPicker.minValue = 1
                hourPicker.maxValue = 12
                hourPicker.wrapSelectorWheel = true
                hourPicker.value = 1  // Set initial hour

                minutePicker.minValue = 0
                minutePicker.maxValue = 59
                minutePicker.wrapSelectorWheel = true
                minutePicker.value = 0  // Set initial minute
                minutePicker.setFormatter { i -> String.format("%02d", i) }  // Format minutes with two digits

                amPmPicker.minValue = 0
                amPmPicker.maxValue = 1
                amPmPicker.displayedValues = arrayOf("AM", "PM")
                amPmPicker.wrapSelectorWheel = true
                amPmPicker.value = 0  // Default to "AM"

                // Handle the "Done" button click
                doneTextView?.setOnClickListener {
                    // Get selected date
                    val selectedDay = dayPicker.value
                    val selectedMonth = monthPicker.displayedValues[monthPicker.value - 1]
                    val selectedYear = yearPicker.value

                    // Get selected time
                    val selectedHour = hourPicker.value
                    val selectedMinute = String.format("%02d", minutePicker.value)
                    val selectedAmPm = amPmPicker.displayedValues[amPmPicker.value]

                    // Format the selected date and time
                    val selectedDateTime = "$selectedDay $selectedMonth $selectedYear, $selectedHour:$selectedMinute $selectedAmPm"
                    val spannableString = SpannableString(selectedDateTime)

                    // Set the text color to black
                    val color = Color.BLACK

                    // Apply the color and bold style to the entire text
                    spannableString.setSpan(ForegroundColorSpan(color), 0, selectedDateTime.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, selectedDateTime.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    // Log the selected date and time (optional for debugging)
                    Log.d("SelectedDateTime", selectedDateTime)

                    // Update the TextView in the Activity with the selected date and time
                    findViewById<TextView>(R.id.choose_date)?.text = spannableString

                    // Dismiss the BottomSheetDialog
                    bottomSheetDialog.dismiss()
                }
                deleteStart?.setOnClickListener()
                {
                  bottomSheetDialog.dismiss()

                }

                // Show the BottomSheetDialog
                bottomSheetDialog.show()
            } else {
                Log.e("BottomSheetDialog", "Failed to find NumberPickers in the layout!")
            }
        }





        choose_dates.setOnClickListener {

           // val bottomSheetFragment = ChooseDatesFragment()
            //bottomSheetFragment.show(supportFragmentManager, "ChooseDatesFragment")
            val bottomSheetDialog = BottomSheetDialog(this,R.style.ShoppingList_BottomSheetDialog)
            val bottomSheetView = layoutInflater.inflate(R.layout.activity_ride_end, null)
            bottomSheetDialog.setContentView(bottomSheetView)
            val dayPickers = bottomSheetDialog.findViewById<NumberPicker>(R.id.datePickers)
            val monthPickers = bottomSheetDialog.findViewById<NumberPicker>(R.id.monthPickers)
            val yearPickers = bottomSheetDialog.findViewById<NumberPicker>(R.id.yearPickers)
            val hourPickers = bottomSheetDialog.findViewById<NumberPicker>(R.id.time_pickers)
            val minutePickers = bottomSheetDialog.findViewById<NumberPicker>(R.id.minute_Pickers)
            val amPmPickers = bottomSheetDialog.findViewById<NumberPicker>(R.id.am_PmPickers)
            val doneTextViews = bottomSheetDialog.findViewById<TextView>(R.id.done_texts)
            val deleteEnd = bottomSheetDialog.findViewById<ImageView>(R.id.delete_end)
            if (dayPickers != null && monthPickers != null && yearPickers != null &&
                hourPickers != null && minutePickers != null && amPmPickers != null)
            {
            dayPickers.minValue = 1
            dayPickers.maxValue = 31

            monthPickers.minValue = 1
            monthPickers.maxValue = 12
            monthPickers.displayedValues = resources.getStringArray(R.array.monthstime_array)
            yearPickers.minValue = 1900
            yearPickers.maxValue = 2100



            // Optionally set initial date
            dayPickers.value = 1
            monthPickers.value = 1
            yearPickers.value = 2024

                hourPickers.minValue = 1
                hourPickers.maxValue = 12
                hourPickers.wrapSelectorWheel = true

                // Set values for minute picker (0 to 59)
                minutePickers.minValue = 0
                minutePickers.maxValue = 59
                minutePickers.wrapSelectorWheel = true
                minutePickers.setFormatter { i -> String.format("%02d", i) } // Show 2 digits

                // Set values for AM/PM picker (0 = AM, 1 = PM)
                amPmPickers.minValue = 0
                amPmPickers.maxValue = 1
                amPmPickers.displayedValues = arrayOf("AM", "PM")
                amPmPickers.wrapSelectorWheel = true
                doneTextViews?.setOnClickListener {
                    // Get selected date
                    val selectedDay = dayPickers.value
                    val selectedMonth = monthPickers.displayedValues[monthPickers.value - 1]
                    val selectedYear = yearPickers.value

                    // Get selected time
                    val selectedHour = hourPickers.value
                    val selectedMinute = String.format("%02d", minutePickers.value)
                    val selectedAmPm = amPmPickers.displayedValues[amPmPickers.value]

                    // Format date and time
                    val selectedDateTime = "$selectedDay $selectedMonth $selectedYear, $selectedHour:$selectedMinute $selectedAmPm"
                    Log.d("SelectedDateTime", selectedDateTime)
                    val spannableString = SpannableString(selectedDateTime)

                    // Set the text color to black
                    val color = Color.BLACK

                    // Apply the color and bold style to the entire text
                    spannableString.setSpan(ForegroundColorSpan(color), 0, selectedDateTime.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, selectedDateTime.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


                    // Update the TextView in the Activity with the selected date and time
                    findViewById<TextView>(R.id.choose_dates)?.text = spannableString



                    // Dismiss the BottomSheetDialog
                    bottomSheetDialog.dismiss()
                }
                // Set click listener for the delete_end ImageView to dismiss the BottomSheetDialog
                deleteEnd?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }

                // Show the BottomSheetDialog
                bottomSheetDialog.show()
            } else {
                Log.e("BottomSheetDialog", "Failed to find NumberPickers in the layout!")
            }


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
    private fun addStop() {
        // Create a new stop name based on the next stop number
        val stopName = "Stop $nextStopNumber"
        stops.add(stopName) // Add to the stops list
        adapter.notifyItemInserted(stops.size - 1) // Notify the adapter
        nextStopNumber++

        // Change the images only on the first addition of a stop
        if (!hasAddedStop) {
            radio_search.setImageResource(R.drawable.group_913__1_ev) // Replace with your new image
            location_svg.setImageResource(R.drawable.location_pin__1_ev) // Replace with your new image
            hasAddedStop = true

        }
    }
    private fun onStopDeleted(position: Int) {
        stops.removeAt(position) // Remove the stop from the list
        adapter.notifyItemRemoved(position) // Notify the adapter about item removal
        adapter.notifyItemRangeChanged(position, stops.size) // Update positions of remaining items

        if (stops.isEmpty()) {
            nextStopNumber = 1
       resetImagesToNormal()
        hasAddedStop=false// Reset the stop number if needed
        }
    }
    private fun resetImagesToNormal() {
        // Reset images to their original state
        radio_search.setImageResource(R.drawable.group_427318913_ev) // Replace with your original image resource
        location_svg.setImageResource(R.drawable.location_pin_ev)
    isSwitched=false// Replace with your original image resource
    }

    private fun recalculateNextStopNumber() {
        nextStopNumber = if (stops.isEmpty()) {
            1 // Reset to 1 if no stops are left
        } else {
            stops.map { it.removePrefix("Stop ").toInt() }.maxOrNull()!! + 1
        }
    }

}
