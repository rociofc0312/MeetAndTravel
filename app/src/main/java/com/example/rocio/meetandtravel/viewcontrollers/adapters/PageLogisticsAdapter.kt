package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.rocio.meetandtravel.viewcontrollers.fragments.MyEventsFragment
import com.example.rocio.meetandtravel.viewcontrollers.fragments.MyProvidersFragment

class PageLogisticsAdapter(fragmentManager: FragmentManager, private val numberTabs: Int) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        when (p0){
            0 -> {
                return MyEventsFragment()
            }
            1 -> {
                return MyProvidersFragment()
            }
            else -> {
                return MyEventsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return numberTabs
    }

}