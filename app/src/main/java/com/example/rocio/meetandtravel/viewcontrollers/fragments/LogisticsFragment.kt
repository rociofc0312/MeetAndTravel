package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.AllEventsAdapter
import com.example.rocio.meetandtravel.viewcontrollers.adapters.PageLogisticsAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_logistics.*
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager


class LogisticsFragment : Fragment() {

    private lateinit var pageLogisticsAdapter: PageLogisticsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_logistics, container, false)

        val logisticsViewPager = view.findViewById<View>(R.id.logisticsViewPager) as ViewPager
        val tabLayout = view.findViewById<TabLayout>(R.id.logisticsTabLayout)

        pageLogisticsAdapter = PageLogisticsAdapter(childFragmentManager, tabLayout.tabCount)

        logisticsViewPager.adapter = pageLogisticsAdapter

        logisticsViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                logisticsViewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return view
    }


}
