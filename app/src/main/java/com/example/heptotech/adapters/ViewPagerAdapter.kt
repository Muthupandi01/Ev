package com.example.heptotech.adapters// com.example.heptotech.adapters.ViewPagerAdapter for ViewPager2
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.heptotech.fragment.BookingsFragment
import com.example.heptotech.fragment.ChargingFragment
import com.example.heptotech.fragment.OverviewFragment
import com.example.heptotech.fragment.Tripsfragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewFragment()
            1 -> BookingsFragment()
            2 -> ChargingFragment()
            else -> Tripsfragment()
        }
    }

    override fun getItemCount(): Int {
        return 4 // Number of pages
    }
}
