package com.example.heptotech.fragment
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.heptotech.customclass.EndSwipeButton
import com.example.heptotech.R
import com.example.heptotech.actvity_view.OrderDetails
import com.example.heptotech.actvity_view.ScanActivity
import com.example.heptotech.customclass.SwipeButton
import com.example.heptotech.actvity_view.ViewVehicles
import com.example.heptotech.customclass.CircularProgressView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChargingFragment : Fragment(R.layout.charging_fragment) {

    var num: Float? = 0f

    // Move all val declarations to be var and make them properties of the class
    private var circularProgressView: CircularProgressView? = null
    private var circularProgressViewzero: CircularProgressView? = null
    private var startparent: LinearLayout? = null
    private var nextparent: LinearLayout? = null
    private var ugx: TextView? = null
    private var duration: TextView? = null
    private var Complete: TextView? = null
    private var energy: TextView? = null
    private var swipeButton: SwipeButton? = null
    private var swipeButtonend: EndSwipeButton? = null

    private val REQUEST_CODE = 1
    private var chargeType: String? = null

    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize all views here
        circularProgressView = view.findViewById(R.id.circularProgressView)
        circularProgressViewzero = view.findViewById(R.id.circularProgressViewzero)
        startparent = view.findViewById(R.id.startparent)
        nextparent = view.findViewById(R.id.nextparent)
        ugx = view.findViewById(R.id.ugx)
        duration = view.findViewById(R.id.duration)
        Complete = view.findViewById(R.id.Complete)
        energy = view.findViewById(R.id.energy)
        swipeButton = view.findViewById(R.id.swipeButton)
        swipeButtonend = view.findViewById(R.id.swipeButton2)
        circularProgressViewzero?.setProgress(0f)


        num = 45f
        changeFn(num!!)
        Complete!!.setOnClickListener {
            num = 45f
            changeFn(num!!)
            circularProgressView?.setProgress(num!!)
            // Reset visibility of layouts
            startparent?.isVisible = true
            nextparent?.isVisible = false
            // Reset any other UI elements as needed
            swipeButton?.reset()
            swipeButtonend?.reset()
            val intent = Intent(requireContext(), OrderDetails::class.java)
            startActivity(intent)
        }



        circularProgressView?.setProgress(num!!)
        swipeButton?.setOnTouchListener { _, event ->
            swipeButton?.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    if (swipeButton?.isSwiped() == true) {
                        nextparent?.isVisible = true
                        startparent?.isVisible = false
                    } else {
                        swipeButton?.reset()
                    }
                }
            }
            true
        }

        swipeButtonend?.setOnTouchListener { _, event ->
            swipeButtonend?.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    if (swipeButtonend?.isSwiped() == true) {
                        nextparent?.isVisible = true
                        val bottomSheetDialog = BottomSheetDialog(requireContext())
                        val sheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_charge, null)
                        val stopcharging = sheetView.findViewById<TextView>(R.id.stopcharging)
                        val keepcharging = sheetView.findViewById<TextView>(R.id.keepcharging)

                        stopcharging.setOnClickListener {
                            bottomSheetDialog.dismiss()
                           // swipeButtonend?.reset()
                            num=100f
                            changeFn(num!!)
                            circularProgressView?.setProgress(num!!)
                        }

                        keepcharging.setOnClickListener {
                            bottomSheetDialog.dismiss()
                            swipeButtonend?.reset()


//                         GlobalScope.launch(Dispatchers.Main) {
//                            delay(1000)
//                             num=100f
//                             changeFn(num!!)
//                             circularProgressView?.setProgress(num!!)
//                         }

                        }

                        bottomSheetDialog.setContentView(sheetView)
                        bottomSheetDialog.show()
                    } else {
                        swipeButtonend?.reset()
                    }
                }
            }
            true
        }
    }

    private fun changeFn(num: Float) {


//                GlobalScope.launch(Dispatchers.Main) {
//               delay(500)
//
//
//        }


        if (num == 100f) {
            energy?.setText("100 kWh")
            Complete?.isVisible = true
            swipeButtonend?.isVisible=false
            ugx?.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_common))
            duration?.setTextColor(ContextCompat.getColor(requireContext(), R.color.greentxt))
        } else {
            swipeButtonend?.isVisible=true
            Complete?.isVisible = false
            energy?.setText("45 kWh")
            ugx?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            duration?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }


    fun updateWithScanResult(scanResult: String?) {
        scanResult?.let {
            Log.d("ScanResultss", "updateWithScanResult: "+it)
            num = 0f
            changeFn(num!!)
            circularProgressView?.setProgress(45f)
            startparent?.isVisible = true
            nextparent?.isVisible = false
            swipeButton?.reset()
            swipeButtonend?.reset()

        }
    }
}
