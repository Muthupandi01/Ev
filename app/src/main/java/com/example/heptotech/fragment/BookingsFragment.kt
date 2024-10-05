package com.example.heptotech.fragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.heptotech.customclass.PillView
import com.example.heptotech.R
import com.example.heptotech.adapters.ShedduledAdapter
import com.example.heptotech.bean_dataclass.SheduledItemcarddata

class BookingsFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var pillView: PillView
    private lateinit var adapter: ShedduledAdapter
    private lateinit var anim2: LinearLayout
    private lateinit var parentgrad: LinearLayout
    private lateinit var moving_gradient: View

    private lateinit var pauseresumelnr: LinearLayout
    private lateinit var pauseresumeimg: ImageView
    private lateinit var pauseresumetxt: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewPager.currentItem
            val nextItem = (currentItem + 1) % adapter.itemCount
            viewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(this, AUTO_SCROLL_DELAY)
        }
    }

    companion object {
        private const val AUTO_SCROLL_DELAY = 3000L // 3 seconds
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bookings_fragment, container, false)
    }

    @SuppressLint("ResourceType", "SetTextI18n", "ObjectAnimatorBinding")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewPager2 and PillView
        viewPager = view.findViewById(R.id.viewPager)
        pillView = view.findViewById(R.id.pillView)
        anim2 = view.findViewById(R.id.anim2)
        parentgrad = view.findViewById(R.id.parentgrad)


//
//        val drawable = parentgrad.drawable as VectorDrawable
//        val animator1 = ObjectAnimator.ofFloat(drawable, "strokeWidth", 1.5f, 3f)
//        animator1.duration = 1000
//        animator1.repeatCount = ObjectAnimator.INFINITE
//        animator1.repeatMode = ObjectAnimator.REVERSE
//        animator1.start()

        // Create a vertical gradient drawable


        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TR_BL,
            intArrayOf(0xFFF64C35.toInt(), 0xFFF77F00.toInt(), 0xFFFDCA17.toInt(), 0xFFF64C35.toInt())
        ).apply {
            cornerRadii = floatArrayOf(20f, 20f, 20f, 20f, 0f, 0f, 0f, 0f)
        }
        anim2.background = gradientDrawable



        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000L // Adjust the speed of the animation
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { valueAnimator ->
                val fraction = valueAnimator.animatedFraction
                val startColor = interpolateColor(0xFFF64C35.toInt(), 0xFFF77F00.toInt(), fraction)
                val midColor = interpolateColor(0xFFF77F00.toInt(), 0xFFFDCA17.toInt(), fraction)
                val endColor = interpolateColor(0xFFFDCA17.toInt(), 0xFFF64C35.toInt(), fraction)

                gradientDrawable.colors = intArrayOf(startColor, midColor, endColor)
            }
        }

        animator.start()


//
//        anim1.setBackgroundResource(R.drawable.rectangle_34624408__1_)
//        // Apply the moving gradient animation
//        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.gradient_animation)
//        anim1.startAnimation(animation)


        pauseresumelnr = view.findViewById(R.id.pauseresumelnr)
        pauseresumeimg = view.findViewById(R.id.pauseresumeimg)
        pauseresumetxt = view.findViewById(R.id.pauseresumetxt)

        pauseresumelnr.setOnClickListener {
            if (pauseresumetxt.text == "Resume") {
                pauseresumetxt.text = "Pause"
                pauseresumeimg.setImageResource(R.drawable.pause_ev)
                pauseresumelnr.setBackgroundResource(R.drawable.rectangle_34624412_reschdeule_ev)
            } else {
                pauseresumetxt.text = "Resume"
                pauseresumeimg.setImageResource(R.drawable.baseline_play_circle_outline_24_ev)
                pauseresumelnr.setBackgroundResource(R.drawable.rectangle_34624412_exit_ev)
            }
        }

      //  shineAnimation(anim1)
      //  shineAnimation(anim2)

      //  startBlinkAnimation()



        // Prepare the list of SheduledItemcarddata
        val sampleData = listOf(
            SheduledItemcarddata(R.drawable.ev_booking_img_ev, "Kampala EV Charge Station", "August 15, 2024", "Kampla", "100", "1 hr", "UGX 50,000"),
            SheduledItemcarddata(R.drawable.ev_booking_img_ev, "Kampala EV Charge Station", "August 15, 2024", "Kampla", "100", "1 hr", "UGX 50,000"),
            SheduledItemcarddata(R.drawable.ev_booking_img_ev, "Kampala EV Charge Station", "August 15, 2024", "Kampla", "100", "1 hr", "UGX 50,000"),
            SheduledItemcarddata(R.drawable.ev_booking_img_ev, "Kampala EV Charge Station", "August 15, 2024", "Kampla", "200", "2 hrs", "UGX 50,000")
            // Add more items as needed
        )

        adapter = ShedduledAdapter(sampleData) { item ->
            // Handle item click here
        }
        viewPager.adapter = adapter

        // Set the number of pages and the current page for the PillView
        pillView.pageCount = adapter.itemCount
        pillView.currentPage = 0

        // Set up a page change callback for ViewPager2 to update the PillView
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pillView.currentPage = position
            }
        })

        startAutoScroll()
    }
    private fun interpolateColor(startColor: Int, endColor: Int, fraction: Float): Int {
        val startR = (startColor shr 16) and 0xFF
        val startG = (startColor shr 8) and 0xFF
        val startB = startColor and 0xFF

        val endR = (endColor shr 16) and 0xFF
        val endG = (endColor shr 8) and 0xFF
        val endB = endColor and 0xFF

        val r = (startR + fraction * (endR - startR)).toInt()
        val g = (startG + fraction * (endG - startG)).toInt()
        val b = (startB + fraction * (endB - startB)).toInt()

        return (0xFF shl 24) or (r shl 16) or (g shl 8) or b
    }


    private fun startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll() // Stop auto-scrolling when the view is destroyed
    }
}
