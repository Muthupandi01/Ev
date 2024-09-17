package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.heptotech.R
import com.example.heptotech.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewVehicles : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val REQUEST_CODE = 100 // Request code for ScanActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_vehicles)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Overview"
                1 -> "Bookings"
                2 -> "Charging"
                else -> "Trips"
            }
        }.attach()

        // Customize tab text size
        tabLayout.post {
            for (i in 0 until tabLayout.tabCount) {
                val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i) as? ViewGroup
                tab?.let {
                    val tabTextView = it.getChildAt(1) as? TextView
                    tabTextView?.textSize = 25f
                }
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    if (position == 2) { // Charging tab clicked
                        // Directly start ScanActivity without changing ViewPager item
                        startScanActivity()
                    } else {
                        viewPager.currentItem = position // Change the ViewPager current item for other tabs
                    }
                }
            }

            private fun startScanActivity() {
                val intent = Intent(this@ViewVehicles, ScanActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { /* Handle tab unselected if needed */ }
            override fun onTabReselected(tab: TabLayout.Tab?) { /* Handle tab reselected if needed */ }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val scanResult = data?.getStringExtra("EXTRA_SCAN_RESULT")
            // Switch to the Charging tab and handle the scan result
            viewPager.currentItem = 2 // Switch to the Charging tab
            // Optionally, handle the scan result here if needed
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ViewVehicles, VehicleRegistration::class.java)
        startActivity(intent)
    }
}
