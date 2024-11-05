package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.CarInfoAdapter
import com.example.heptotech.adapters.DateAdapter
import com.example.heptotech.adapters.PlugAdapter
import com.example.heptotech.adapters.PlugsSegmentAdapter
import com.example.heptotech.adapters.TimeAdapter
import com.example.heptotech.bean_dataclass.CarInfo
import com.example.heptotech.bean_dataclass.Plugs
import com.example.heptotech.bean_dataclass.PlugsSegmentClass
import com.example.heptotech.customclass.BatteryViewHorizontal
import com.example.heptotech.customclass.GradientView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class VehicleSpecificOpenBooking : AppCompatActivity() {

    private lateinit var popupWindow: PopupWindow
    private lateinit var batteryViewHorizontal: BatteryViewHorizontal
    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var monthTextView: TextView
    private var currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var currentYear = Calendar.getInstance().get(Calendar.YEAR)

    private lateinit var dateAdapter: DateAdapter
    private lateinit var carAdapter: CarInfoAdapter
    private lateinit var plugsSegmentAdapter: PlugsSegmentAdapter
    private lateinit var plugAdapter: PlugAdapter
    private lateinit var timeAdapter: TimeAdapter
    private lateinit var segment1:LinearLayout
    private lateinit var segment2:LinearLayout
    private lateinit var segment3:LinearLayout
    private lateinit var segment4:LinearLayout
    var currentPosition = 0
    private var receivedValue=""

    val timeIntervals = arrayOf(
        "12:00 AM", "12:30 AM", "01:00 AM", "01:30 AM", "02:00 AM", "02:30 AM", "11:30 PM"
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_specific_open_booking)

        segment1 = findViewById(R.id.segment1)
        segment2 = findViewById(R.id.segment2)
        segment3 = findViewById(R.id.segment3)
        segment4 = findViewById(R.id.segment4)

         receivedValue = intent.getStringExtra("KEY").toString()

        // Inflate layouts based on the received value
        if (receivedValue.equals("open")) {
            inflateSegmentLayoutsOpen()

        } else {
            inflateSegmentLayoutsClosed()
        }


//        batteryViewHorizontal = findViewById(R.id.battery_view)
//        batteryViewHorizontal.batteryLevel = 0.75f
//        dateRecyclerView = findViewById(R.id.dateRecyclerView)
//        monthTextView = findViewById(R.id.monthTextView)









        // Initialize RecyclerView
      //  dateAdapter = DateAdapter(DateUtil.getDatesForMonth(currentMonth, currentYear))

//        { selectedDate ->
//            //Toast.makeText(this, "Selected: ${selectedDate.date} ${selectedDate.month} ${selectedDate.year}", Toast.LENGTH_SHORT).show()
//        }

//        dateRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        dateRecyclerView.adapter = dateAdapter
//
//        updateMonthYearText()
//
//        // Handle left and right arrow clicks for month navigation
//        findViewById<ImageView>(R.id.leftImageView).setOnClickListener {
//            goToPreviousMonth()
//        }
//        findViewById<ImageView>(R.id.rightImageView).setOnClickListener {
//            goToNextMonth()
//        }

        // Handle monthTextView click to show month selection dialog
//        monthTextView.setOnClickListener {
//            showMonthSelectionDialog()
//        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun inflateSegmentLayoutsOpen() {
        // Inflate segment3 above segment1
        inflateLayout(segment1, R.layout.segmentcharge_fixedmobile)
        inflateLayout(segment2, R.layout.segment_chargesingleimgcard_reccyler)
        inflateLayout(segment3, R.layout.segmencharge_batterystend)
        inflateLayout(segment4, R.layout.segment_plugs_recyclercard)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun inflateSegmentLayoutsClosed() {
        // Inflate segment2 above segment1
        inflateLayout(segment1, R.layout.segment_chargesingleimgcard_reccyler)
        inflateLayout(segment2, R.layout.segmentcharge_fixedmobile)
        inflateLayout(segment3, R.layout.segmencharge_batterystend)
        inflateLayout(segment4, R.layout.segment_plugs_recyclercard)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("LongLogTag")
    private fun inflateLayout(segment: LinearLayout, layoutResId: Int) {
        val inflater = LayoutInflater.from(this)
        val inflatedView: View = inflater.inflate(layoutResId, segment, false)

        val dateRecyclerView: RecyclerView? = inflatedView.findViewById(R.id.dateRecyclerView)
        val monthTextView: TextView? = inflatedView.findViewById(R.id.monthTextView)
        val stTime: AutoCompleteTextView? = inflatedView.findViewById(R.id.autoCompleteStTime)
        val etTime: AutoCompleteTextView? = inflatedView.findViewById(R.id.autoCompleteEtTime)
        val slotrec: RecyclerView? = inflatedView.findViewById(R.id.slotrec)
        val timeLay2: LinearLayout? = inflatedView.findViewById(R.id.timeLay2)
        val timeLay1: LinearLayout? = inflatedView.findViewById(R.id.timeLay1)
        val grad: GradientView? = inflatedView.findViewById(R.id.grad)
        grad?.animateGradient(3000L) // Set animation duration in milliseconds



       //single
        val recyclerViewsingle: RecyclerView? = inflatedView.findViewById(R.id.carrec)
        val leftImageView: ImageView? = inflatedView.findViewById(R.id.leftImageView)
        val rightImageView: ImageView? = inflatedView.findViewById(R.id.rightImageView)

        //dates
        val leftImageViewdate: ImageView? = inflatedView.findViewById(R.id.leftImageViewdate)
        val rightImageViewdate: ImageView? = inflatedView.findViewById(R.id.rightImageViewdate)

        //avilable
        val dynamicPlugsRecycler: RecyclerView? = inflatedView.findViewById(R.id.dynamicPlugs)
        val staticPlugs: LinearLayout? = inflatedView.findViewById(R.id.staticPlugs)

        val recyclerViewplugs: RecyclerView? = inflatedView.findViewById(R.id.plugrec)
        val leftImageViewplug: ImageView? = inflatedView.findViewById(R.id.leftImageViewplug)
        val rightImageViewplug: ImageView? = inflatedView.findViewById(R.id.rightImageViewplug)




        val fixedSt: LinearLayout? = inflatedView.findViewById(R.id.fixedSt)
        val mobileSt: LinearLayout? = inflatedView.findViewById(R.id.mobileSt)
        val imgfixedSt: ImageView? = inflatedView.findViewById(R.id.imgfixedSt)
        val imgmobileSt: ImageView? = inflatedView.findViewById(R.id.imgmobileSt)
        val txtfixedSt: TextView? = inflatedView.findViewById(R.id.txtfixedSt)
        val txtmobileSt: TextView? = inflatedView.findViewById(R.id.txtmobileSt)
        val fixparent: LinearLayout? = inflatedView.findViewById(R.id.fixparent)
        val mobparent: LinearLayout? = inflatedView.findViewById(R.id.mobparent)
        fixedSt?.setOnClickListener {
            imgfixedSt!!.isInvisible = false
            imgmobileSt!!.isInvisible = true
            fixparent!!.isVisible=true
            mobparent!!.isVisible=false
            txtfixedSt!!.setTextColor(resources.getColor(R.color.commontxtcolor, null))
            txtmobileSt!!.setTextColor(resources.getColor(R.color.whiteTextColor, null))
            fixedSt.setBackgroundResource(R.drawable.rectangle_34624318_ev)
            mobileSt?.setBackgroundResource(R.drawable.rectangle_34624320_ev)
            mobileSt?.setBackgroundResource(R.drawable.rectangle_34624320_ev)
        }

        mobileSt?.setOnClickListener {
            imgfixedSt!!.isInvisible = true
            imgmobileSt!!.isInvisible = false
            fixparent!!.isVisible=false
            mobparent!!.isVisible=true
            txtfixedSt!!.setTextColor(resources.getColor(R.color.whiteTextColor, null))
            txtmobileSt!!.setTextColor(resources.getColor(R.color.commontxtcolor, null))
            fixedSt?.setBackgroundResource(R.drawable.rectangle_34624320_ev)
            mobileSt.setBackgroundResource(R.drawable.rectangle_34624318_ev)
        }

        val pluglist = mutableListOf(
            PlugsSegmentClass("Kampala EV Charge Station"),
            PlugsSegmentClass("Moracco EV Charge Station"),
            PlugsSegmentClass("Uganda EV Charge Station")
        )

        plugsSegmentAdapter = PlugsSegmentAdapter(pluglist)
        recyclerViewplugs?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewplugs?.adapter = plugsSegmentAdapter

        updateArrowIconsPlugs(leftImageViewplug, rightImageViewplug)
        leftImageViewplug?.setOnClickListener {
            scrollLeftPlugs(recyclerViewplugs,leftImageViewplug,rightImageViewplug)
        }
        rightImageViewplug?.setOnClickListener {
            scrollRightPlugs(recyclerViewplugs,leftImageViewplug,rightImageViewplug)
        }


        //



        val plugss = mutableListOf(
            Plugs("4","ChadeMO",R.drawable.vector__5_ev),
            Plugs("2","CCS",R.drawable.group_427318831),
            Plugs("2","Tesla",R.drawable.ccs1_ev),
            Plugs("0","Type 2",R.drawable.group_427318864_ev))

        plugAdapter = PlugAdapter(plugss)
        dynamicPlugsRecycler?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        dynamicPlugsRecycler?.adapter = plugAdapter


//        lifecycleScope.launch(Dispatchers.Main) {
//            delay(6000)
//            staticPlugs?.isVisible=false
//            dynamicPlugsRecycler?.isVisible=true
//        }

        val carList = mutableListOf(
            CarInfo("Tesla Model X", "12, Kampala, Uganda", "75%", R.drawable.pngwingnew_ev),
            CarInfo("Tesla Model Y", "12, Kampala, Uganda", "90%", R.drawable.pngwingnew_ev),
            CarInfo("Tesla Model Z", "12, Kampala, Uganda", "100%", R.drawable.pngwingnew_ev),
            CarInfo("Tesla Model X", "12, Kampala, Uganda", "75%", R.drawable.pngwingnew_ev),
            CarInfo("BMW i8", "3, Nairobi, Kenya", "80%", R.drawable.pngwingnew_ev)
        )

            carAdapter = CarInfoAdapter(carList)
            recyclerViewsingle?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewsingle?.adapter = carAdapter

            updateArrowIcons(leftImageView, rightImageView)


        leftImageView?.setOnClickListener {
            scrollLeft(recyclerViewsingle,leftImageView,rightImageView)
        }

        rightImageView?.setOnClickListener {
            scrollRight(recyclerViewsingle,leftImageView,rightImageView)
        }




        // Check if views are null before proceeding
       // if (stTime != null && etTime != null) {







        if (stTime != null && etTime != null) {
            // Set the background color to transparent
            stTime!!.setOnClickListener {

                  //  val bottomSheetDialog = BottomSheetDialog(this)
                val bottomSheetDialog = BottomSheetDialog(this,R.style.ShoppingList_BottomSheetDialog)

                val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_sttime_openbook, null)
                    bottomSheetDialog.setContentView(view)
                    // Initialize NumberPickers for hours, minutes, and AM/PM

                    val time_pick = view.findViewById<ImageView>(R.id.time_pick)
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



                    time_pick.setOnClickListener {
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

                    // Convert selected time to a Calendar object
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR, selectedHour)
                    calendar.set(Calendar.MINUTE, selectedMinute)
                    calendar.set(Calendar.AM_PM, if (selectedAmPm == "AM") Calendar.AM else Calendar.PM)

                    // Format the selected start time and set it to stTime
                    val formattedStartTime = String.format("%02d:%02d %s", selectedHour, selectedMinute, selectedAmPm)
                    stTime.setText(formattedStartTime)

                    // Add 2 hours to the calendar for end time
                    calendar.add(Calendar.HOUR, 2)

                    // Format the new end time and set it to etTime
                    val endHour = calendar.get(Calendar.HOUR)
                    val endMinute = calendar.get(Calendar.MINUTE)
                    val endAmPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
                    val formattedEndTime = String.format("%02d:%02d %s", endHour, endMinute, endAmPm)

                    etTime.setText(formattedEndTime) // Display the calculated end time

                    bottomSheetDialog.dismiss()
                }

                    bottomSheetDialog.show()


                // showPopupWindow(it,stTime)


            }
            etTime!!.setOnClickListener {
               // showPopupWindow(it,etTime)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, timeIntervals)

            // Set adapter for both start and end time
            stTime.setAdapter(adapter)
            etTime.setAdapter(adapter)

            // Handle start time selection
            stTime.setOnItemClickListener { parent, _, position, _ ->
                val selectedStartTime = parent.getItemAtPosition(position).toString()
                // Do something with the selected start time
            }

            // Handle end time selection
            etTime.setOnItemClickListener { parent, _, position, _ ->
                val selectedEndTime = parent.getItemAtPosition(position).toString()
                // Do something with the selected end time
            }



            //SlotRec


            val timeList = listOf(
                "09:00 AM", "09:15 AM", "09:30 AM", "09:45 AM",
                "10:00 AM", "10:15 AM", "10:30 AM", "10:45 AM",
                "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM",
                "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM",
                "01:00 PM", "01:15 PM", "01:30 PM", "01:45 PM",
                "02:00 PM"
            )


           timeAdapter = TimeAdapter(timeList) { selectedTime ->
                // Handle the selected time here
             //   Toast.makeText(this, "Selected Time: $selectedTime", Toast.LENGTH_SHORT).show()
            }



//            slotrec!!.layoutManager = LinearLayoutManager(this)
//            slotrec!!.adapter = timeAdapter
            slotrec!!.layoutManager = GridLayoutManager(this, 3) // 2 is the number of columns
            slotrec!!.adapter = timeAdapter

//            GlobalScope.launch(Dispatchers.Main) {
//                delay(6000)
//                timeLay1!!.isVisible=false
//                timeLay2!!.isVisible=true
//
//            }

            // Inflate layouts based on the received value
            if (receivedValue.equals("open")) {

                timeLay1!!.isVisible=true
                timeLay2!!.isVisible=false

            } else {
                timeLay1!!.isVisible=false
                timeLay2!!.isVisible=true

            }
        }

        else {
            // Log an error if the views were not found
            Log.e("VehicleSpecificOpenBooking", "stTime or etTime not found in inflated layout.")
        }


      //  }

        // Setup the RecyclerView and monthTextView if they are found
        if (dateRecyclerView != null && monthTextView != null) {
            dateAdapter = DateAdapter(DateUtil.getDatesForMonth(currentMonth, currentYear)) {

            }
            dateRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            dateRecyclerView.adapter = dateAdapter
            updateArrowIconsdate(leftImageViewdate, rightImageViewdate)
            leftImageViewdate?.setOnClickListener {
                scrollLeftdate(dateRecyclerView,leftImageViewdate,rightImageViewdate)
            }

            rightImageViewdate?.setOnClickListener {
                scrollRightdate(dateRecyclerView,leftImageViewdate,rightImageViewdate)
            }
            monthTextView.setOnClickListener {
                showMonthSelectionDialog(dateAdapter, monthTextView)
            }
        }

        // Add the inflated view to the parent segment
        segment.addView(inflatedView)
    }

    private fun scrollRightPlugs(recyclerViewplugs: RecyclerView?, leftImageViewplug: ImageView?, rightImageViewplug: ImageView) {
        if (currentPosition < carAdapter.itemCount - 1) {
            currentPosition++
            recyclerViewplugs?.smoothScrollToPosition(currentPosition)
            updateArrowIconsPlugs(leftImageViewplug, rightImageViewplug)
        } else {
            // Optionally log or handle when at the last item
            Log.d("RecyclerView", "Already at the last item")
        }
    }

    private fun scrollLeftPlugs(recyclerViewplugs: RecyclerView?, leftImageViewplug: ImageView, rightImageViewplug: ImageView?) {
        if (currentPosition > 0) {
            currentPosition--
            recyclerViewplugs?.smoothScrollToPosition(currentPosition)
            updateArrowIconsPlugs(leftImageViewplug, rightImageViewplug)
        } else {
            // Optionally log or handle when at the first item
            Log.d("RecyclerView", "Already at the first item")
        }
    }

    private fun updateArrowIconsPlugs(leftImageView: ImageView?, rightImageView: ImageView?) {
        // Disable or enable left arrow if we are at the first position
        if (currentPosition == 0) {
            leftImageView?.isEnabled = false
            leftImageView?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            leftImageView?.isEnabled = true
            leftImageView?.alpha = 1.0f // Fully visible
        }

        // Disable or enable right arrow if we are at the last position
        if (currentPosition == plugsSegmentAdapter.itemCount - 1) {
            rightImageView?.isEnabled = false
            rightImageView?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            rightImageView?.isEnabled = true
            rightImageView?.alpha = 1.0f // Fully visible
        }
    }

    private fun updateArrowIconsdate(leftImageView: ImageView?, rightImageView: ImageView?) {
        val scrollAmount = 7 // Number of days to scroll at a time
        // Disable or enable left arrow if we are at the first position
        if (currentPosition <= 0) {
            leftImageView?.isEnabled = false
            leftImageView?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            leftImageView?.isEnabled = true
            leftImageView?.alpha = 1.0f // Fully visible
        }

        // Disable or enable right arrow if we are at the last position
        if (currentPosition + scrollAmount >= dateAdapter.itemCount) {
            rightImageView?.isEnabled = false
            rightImageView?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            rightImageView?.isEnabled = true
            rightImageView?.alpha = 1.0f // Fully visible
        }
    }


    private fun scrollRightdate(dateRecyclerView: RecyclerView?, leftImageViewdate: ImageView?, rightImageViewdate: ImageView) {
        val scrollAmount = 7 // Number of days to scroll at a time
        if (currentPosition + scrollAmount < dateAdapter.itemCount) {
            currentPosition += scrollAmount
            dateRecyclerView?.smoothScrollToPosition(currentPosition)
            updateArrowIconsdate(leftImageViewdate, rightImageViewdate)
        } else {
            // Optionally, handle if the scroll exceeds the item count
            Log.d("RecyclerView", "Reached the end of the list")
        }
    }

    private fun scrollLeftdate(dateRecyclerView: RecyclerView?, leftImageViewdate: ImageView, rightImageViewdate: ImageView?) {
        val scrollAmount = 7 // Number of days to scroll at a time
        if (currentPosition - scrollAmount >= 0) {
            currentPosition -= scrollAmount
            dateRecyclerView?.smoothScrollToPosition(currentPosition)
            updateArrowIconsdate(leftImageViewdate, rightImageViewdate)
        } else {
            // Optionally, handle if the scroll exceeds the start of the list
            Log.d("RecyclerView", "Reached the beginning of the list")
        }
    }

    private fun scrollRight(
        recyclerViewsingle: RecyclerView?,
        leftImageView: ImageView?,
        rightImageView: ImageView
    ) {
        if (currentPosition < carAdapter.itemCount - 1) {
            currentPosition++
            recyclerViewsingle?.smoothScrollToPosition(currentPosition)
            updateArrowIcons(leftImageView, rightImageView)
        } else {
            // Optionally log or handle when at the last item
            Log.d("RecyclerView", "Already at the last item")
        }

    }

    private fun updateArrowIcons(leftImageView: ImageView?, rightImageView: ImageView?) {
        // Disable or enable left arrow if we are at the first position
        if (currentPosition == 0) {
            leftImageView?.isEnabled = false
            leftImageView?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            leftImageView?.isEnabled = true
            leftImageView?.alpha = 1.0f // Fully visible
        }

        // Disable or enable right arrow if we are at the last position
        if (currentPosition == carAdapter.itemCount - 1) {
            rightImageView?.isEnabled = false
            rightImageView?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            rightImageView?.isEnabled = true
            rightImageView?.alpha = 1.0f // Fully visible
        }
    }


    private fun scrollLeft(
        recyclerViewsingle: RecyclerView?,
        leftImageView: ImageView,
        rightImageView: ImageView?
    ) {

        if (currentPosition > 0) {
            currentPosition--
            recyclerViewsingle?.smoothScrollToPosition(currentPosition)
            updateArrowIcons(leftImageView, rightImageView)
        } else {
            // Optionally log or handle when at the first item
            Log.d("RecyclerView", "Already at the first item")
        }
    }

    private fun showPopupWindow(anchor: View?, stTime: AutoCompleteTextView) {
        val popupView = layoutInflater.inflate(R.layout.popup_menu_time, null)
        val popupWindow = PopupWindow(popupView, anchor!!.width, LinearLayout.LayoutParams.WRAP_CONTENT)

        // Define a method to reset background color
        fun resetBackgroundColor() {
            stTime.setBackgroundResource(android.R.color.transparent) // Change this if you have a default color
        }

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        popupView.findViewById<TextView>(R.id.item_family).setOnClickListener {
            stTime.setText("09:30 AM")
            popupWindow.dismiss()
        }

        popupView.findViewById<TextView>(R.id.item_friend).setOnClickListener {
            stTime.setText("09:45 AM")
            popupWindow.dismiss()
        }

        popupView.findViewById<TextView>(R.id.item_colleague).setOnClickListener {
            stTime.setText("10:00 AM")
            popupWindow.dismiss()
        }

        popupView.findViewById<TextView>(R.id.item_other).setOnClickListener {
            stTime.setText("10:15 AM")
            popupWindow.dismiss()
        }

        // Function to convert dp to pixels
        fun dpToPx(dp: Float): Int {
            return (dp * this.resources.displayMetrics.density).toInt()
        }

        // Set the popup window's position with a bottom margin reduced by 2dp
        val location = IntArray(2)
        anchor.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1] + anchor.height + dpToPx(14f) // Change to 14f for a 2dp reduction from 16dp

        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, x, y)
    }




    private fun updateMonthYearText(monthTextView: TextView) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, currentMonth)
            set(Calendar.YEAR, currentYear)
        }
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""
        monthTextView.text = "$monthName $currentYear"
    }



    private fun updateMonthYearText() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, currentMonth)
            set(Calendar.YEAR, currentYear)
        }
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""
        monthTextView.text = "$monthName $currentYear"
    }

    private fun goToNextMonth() {
        val (nextMonth, nextYear) = DateUtil.getNextMonth(currentMonth, currentYear)
        currentMonth = nextMonth
        currentYear = nextYear
        dateAdapter.updateDates(DateUtil.getDatesForMonth(currentMonth, currentYear))
        updateMonthYearText()
    }

    private fun goToPreviousMonth() {
        val (prevMonth, prevYear) = DateUtil.getPreviousMonth(currentMonth, currentYear)
        currentMonth = prevMonth
        currentYear = prevYear
        dateAdapter.updateDates(DateUtil.getDatesForMonth(currentMonth, currentYear))
        updateMonthYearText()
    }

    // Function to show the month selection dialog
    private fun showMonthSelectionDialog(dateAdapter: DateAdapter, monthTextView: TextView) {
        val monthsArray = resources.getStringArray(R.array.months_array) // You can define months in strings.xml
        AlertDialog.Builder(this).apply {
            setTitle("Select Month")
            setItems(monthsArray) { _, which ->
                currentMonth = which
                dateAdapter.updateDates(DateUtil.getDatesForMonth(currentMonth, currentYear))
                updateMonthYearText(monthTextView)
            }
            show()
        }
    }
}
