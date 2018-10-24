package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.viewcontrollers.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener navigateTo(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    private fun fragmentFor(item: MenuItem): Fragment{
        when(item.itemId){
            R.id.navigation_home-> {
                return HomeFragment()
            }
            R.id.navigation_myevents-> {
                return EventsFragment()
            }
            R.id.navigation_tickets -> {
                return TicketsFragment()
            }
            R.id.navigation_providers -> {
                return ProvidersFragment()
            }
            R.id.navigation_reservations -> {
                return ReservationFragment()
            }
        }
        return HomeFragment()
    }

    private fun navigateTo(item: MenuItem): Boolean {
        item.setChecked(true)
        return supportFragmentManager
                .beginTransaction()
                .replace(R.id.attendeeContent, fragmentFor(item))
                .commit() > 0
    }
}
