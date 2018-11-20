package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.rocio.meetandtravel.viewcontrollers.fragments.MyEventsFragment
import com.example.rocio.meetandtravel.viewcontrollers.fragments.MyReservationsFragment
import com.example.rocio.meetandtravel.viewcontrollers.fragments.MyTicketsFragment

class PagePurchasesAdapter(fragmentManager: FragmentManager, private val numberTabs: Int) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment {
        when (p0){
            0 -> {
                return MyTicketsFragment()
            }
            else -> {
                return MyReservationsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return numberTabs
    }

}