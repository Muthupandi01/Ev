package com.example.heptotech.fragment



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.heptotech.R
import com.example.heptotech.actvity_view.ViewVehicles
import com.example.heptotech.customclass.BatteryView

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val battery_view = view.findViewById<BatteryView>(R.id.battery_view)
        val copied = view.findViewById<LinearLayout>(R.id.copied)
        val showmore_content_lnr = view.findViewById<LinearLayout>(R.id.showmore_content_lnr)
        val toggle_switch = view.findViewById<Switch>(R.id.toggle_switch)


        toggle_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                showmore_content_lnr.isVisible=true
            } else {
                showmore_content_lnr.isVisible=false
            }
        }
        copied.setOnClickListener {
            //Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
        }

        battery_view.batteryLevel = 0.75f
    }

}
