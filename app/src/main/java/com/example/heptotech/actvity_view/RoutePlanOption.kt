package com.example.heptotech.actvity_view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.CarInfoAdapter
import com.example.heptotech.adapters.RouteOptionAdapter
import com.example.heptotech.bean_dataclass.RouteOption

class RoutePlanOption : AppCompatActivity() {

    private lateinit var seekBars: Array<SeekBar>
    private lateinit var tvSeekBarProgress: Array<TextView>
    private lateinit var milesSeekBars: Array<SeekBar>
    private lateinit var tvMilesSeekBarProgress: Array<TextView>
    private lateinit var more_option: TextView
    private lateinit var Batery_charge: TextView
    private lateinit var seekBarExtra: SeekBar
    private lateinit var tvSeekBarExtra: TextView
    private lateinit var charge_stop:TextView
    private lateinit var charge_text:TextView
    private lateinit var charge_texts:TextView
    private lateinit var linear_change:LinearLayout
    private lateinit var linear_stop:LinearLayout
    private lateinit var linear_stops:LinearLayout
    private lateinit var search_text:TextView
    private lateinit var distance_text:TextView
    private lateinit var renge_text:TextView
    private lateinit var renge_texts:TextView
    private lateinit var left_image: ImageView
    private lateinit var right_image: ImageView
    private lateinit var car_recycle: RecyclerView
    private lateinit var carAdapter: CarInfoAdapter
    private lateinit var route_option: RouteOptionAdapter
    private lateinit var textView_con: TextView
    private var areViewsVisible = false
    var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_routeplan_option)

        more_option = findViewById(R.id.more_text)
        Batery_charge = findViewById(R.id.startourney_text)
        charge_text = findViewById(R.id .charge_text)
        linear_change=findViewById(R.id.chage)
        linear_stop=findViewById(R.id.numbers)
        charge_stop = findViewById(R.id .number)
        charge_texts = findViewById(R.id .chagess)
        linear_stops=findViewById(R.id.numberss)
        search_text = findViewById(R.id .search_text)
        distance_text=findViewById(R.id.Distance_text)
        renge_text=findViewById(R.id.range_text)
        renge_texts=findViewById(R.id.range_textss)
        left_image=findViewById(R.id.left_angle)
        right_image=findViewById(R.id.right_angle)
        car_recycle=findViewById(R.id.carrec)
        textView_con=findViewById(R.id.con1_text)
        val autoroute = findViewById<LinearLayout>(R.id.auto_route)
        val autoImg = autoroute.findViewById<ImageView>(R.id.auto_img)

        val suggestCharger = findViewById<LinearLayout>(R.id.suggest_charger)
        val sugestImg = suggestCharger.findViewById<ImageView>(R.id.suggest_img)
        val showAll = findViewById<LinearLayout>(R.id.show_all)
        val showImg  = showAll.findViewById<ImageView>(R.id.show_img)
        val normalImage = R.drawable.check_circle__2_ev    // Normal image
        val selectedImage = R.drawable.check_circle__3_ev
        val normalBackground = R.drawable.rectangle_379_ev // Normal background
        val selectedBackground = R.drawable.rectangle_380_ev
        var isSelected = false
        autoroute.setOnClickListener {
            if (isSelected) {
                // Set the normal image and background
                autoImg.setImageResource(selectedImage)
                autoroute.setBackgroundResource(selectedBackground)
            } else {
                // Set the selected image and background
                autoImg.setImageResource(normalImage)
                autoroute.setBackgroundResource(normalBackground)
            }
            isSelected = !isSelected  // Toggle the state
        }
        suggestCharger.setOnClickListener {
            if (isSelected) {
                // Set the normal image and background
                sugestImg.setImageResource(selectedImage)
                suggestCharger.setBackgroundResource(selectedBackground)
            } else {
                // Set the selected image and background
                sugestImg.setImageResource(normalImage)
                suggestCharger.setBackgroundResource(normalBackground)
            }
            isSelected = !isSelected  // Toggle the state
        }
        showAll.setOnClickListener {
            if (isSelected) {
                // Set the normal image and background
                showImg.setImageResource(selectedImage)
                showAll.setBackgroundResource(selectedBackground)
            } else {
                // Set the selected image and background
                showImg.setImageResource(normalImage)
                showAll.setBackgroundResource(normalBackground)
            }
            isSelected = !isSelected  // Toggle the state
        }
        textView_con.setOnClickListener()
        {
            val intent = Intent(this, RoutePlanCalculateRoot::class.java)
            startActivity(intent)
        }

        val carLists = mutableListOf(
            RouteOption("Tesla Model X", "12, Kampala, Uganda", "75%", R.drawable.pngwingnew_ev),
            RouteOption("Tesla Model Y", "12, Kampala, Uganda", "90%", R.drawable.pngwingnew_ev),
            RouteOption("Tesla Model Z", "12, Kampala, Uganda", "100%", R.drawable.pngwingnew_ev),
            RouteOption("Tesla Model X", "12, Kampala, Uganda", "75%", R.drawable.pngwingnew_ev),
            RouteOption("BMW i8", "3, Nairobi, Kenya", "80%", R.drawable.pngwingnew_ev)
        )
        val snapHelpers = PagerSnapHelper()
        snapHelpers.attachToRecyclerView(car_recycle)
        route_option = RouteOptionAdapter(carLists)
        car_recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        car_recycle.adapter = route_option
        updateArrowIcons(left_image, right_image)


        left_image.setOnClickListener {
            scrollLeft(car_recycle,left_image,right_image)
        }

        right_image.setOnClickListener {
            scrollRight(car_recycle,left_image,right_image)
        }


        initializeSeekBars()

        more_option.setOnClickListener {
            toggleSeekBarsVisibility()

        }
    }

    private fun updateArrowIcons(left_image: ImageView?, right_image: ImageView?)
    {
        if (currentPosition == 0) {
            left_image?.isEnabled = false
            left_image?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            left_image?.isEnabled = true
            left_image?.alpha = 1.0f // Fully visible
        }

        // Disable or enable right arrow if we are at the last position
        if (currentPosition == route_option.itemCount - 1) {
            right_image?.isEnabled = false
            right_image?.alpha = 0.5f // Make it semi-transparent or disabled
        } else {
            right_image?.isEnabled = true
            right_image?.alpha = 1.0f // Fully visible
        }

    }

    private fun scrollLeft(car_recycle: RecyclerView?, left_image: ImageView?, right_image: ImageView?)
    {
        if (currentPosition > 0) {
            currentPosition--
            if (car_recycle != null) {
                car_recycle.smoothScrollToPosition(currentPosition)
            }
            updateArrowIcons(left_image, right_image)
        } else {
            // Optionally log or handle when at the first item
            Log.d("RecyclerView", "Already at the first item")
        }

    }

    private fun scrollRight(car_recycle: RecyclerView?, left_image: ImageView?, right_image: ImageView?)
    {
        if (currentPosition < route_option.itemCount - 1) {
            currentPosition++
            if (car_recycle != null) {
                car_recycle.smoothScrollToPosition(currentPosition)
            }
            updateArrowIcons(left_image, right_image)
        } else {
            // Optionally log or handle when at the last item
            Log.d("RecyclerView", "Already at the last item")
        }


    }

    private fun initializeSeekBars() {
        seekBars = arrayOf(
            findViewById(R.id.seekBar),
            findViewById(R.id.seekBar1),
            findViewById(R.id.seekBar2)
        )

        tvSeekBarProgress = arrayOf(
            findViewById(R.id.tect),
            findViewById(R.id.tectss),
            findViewById(R.id.tectsss)
        )

        milesSeekBars = arrayOf(
            findViewById(R.id.seekBar4),
            findViewById(R.id.seekBar5)
        )

        tvMilesSeekBarProgress = arrayOf(
            findViewById(R.id.tect_miles),
            findViewById(R.id.tect_max)
        )

        seekBarExtra = findViewById(R.id.seekBar6)
        tvSeekBarExtra = findViewById(R.id.tect_maxs)

        for (i in seekBars.indices) {
            setupSeekBarListener(seekBars[i], tvSeekBarProgress[i])
        }

        for (i in milesSeekBars.indices) {
            setupMilesSeekBarListener(milesSeekBars[i], tvMilesSeekBarProgress[i])
        }

        setupExtraSeekBarListener(seekBarExtra, tvSeekBarExtra)
    }

    private fun toggleSeekBarsVisibility() {
        areViewsVisible = !areViewsVisible
        val visibility = if (areViewsVisible) View.VISIBLE else View.GONE

        // Toggle visibility for SeekBars and related TextViews
        for (seekBar in seekBars) {
            seekBar.visibility = visibility
        }
        for (textView in tvSeekBarProgress) {
            textView.visibility = visibility
        }

        // Toggle visibility for Miles SeekBars and their TextViews
        for (seekBar in milesSeekBars) {
            seekBar.visibility = visibility
        }
        for (textView in tvMilesSeekBarProgress) {
            textView.visibility = visibility
        }

        // Toggle visibility for the extra SeekBar and its TextView
        seekBarExtra.visibility = visibility
        tvSeekBarExtra.visibility = visibility

        // Toggle visibility for additional views
        linear_change.visibility = visibility
        linear_stop.visibility = visibility
        charge_stop.visibility = visibility
        Batery_charge.visibility = visibility
        charge_text.visibility = visibility
        charge_texts.visibility = visibility
        linear_stops.visibility = visibility
        search_text.visibility = visibility
        distance_text.visibility = visibility
        renge_text.visibility = visibility
        renge_texts.visibility = visibility
    }

    private fun setupSeekBarListener(seekBar: SeekBar, textView: TextView) {
        textView.text = "${seekBar.progress}"

        seekBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                seekBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateTextViewPosition(seekBar, textView, seekBar.progress)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = progress.toString()
                seekBar?.let { updateTextViewPosition(it, textView, progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupMilesSeekBarListener(seekBar: SeekBar, textView: TextView) {
        textView.text = "${seekBar.progress} miles"

        seekBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                seekBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateTextViewPosition(seekBar, textView, seekBar.progress)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "$progress miles"
                seekBar?.let { updateTextViewPosition(it, textView, progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupExtraSeekBarListener(seekBar: SeekBar, textView: TextView) {
        textView.text = "${seekBar.progress} locations"

        seekBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                seekBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateTextViewPosition(seekBar, textView, seekBar.progress)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "$progress locations"
                seekBar?.let { updateTextViewPosition(it, textView, progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTextViewPosition(seekBar: SeekBar, textView: TextView, progress: Int) {
        val seekBarWidth = seekBar.width - seekBar.paddingStart - seekBar.paddingEnd
        val thumbPosX = (progress / seekBar.max.toDouble() * seekBarWidth).toInt() + seekBar.paddingStart
        val tvWidth = textView.width
        val newX = thumbPosX - (tvWidth / 2)

        textView.x = newX.toFloat()
        // Set Y position above the thumb
        val thumbPosY = seekBar.y - textView.height - 16 // Adjust as needed
        textView.y = thumbPosY
    }
}
