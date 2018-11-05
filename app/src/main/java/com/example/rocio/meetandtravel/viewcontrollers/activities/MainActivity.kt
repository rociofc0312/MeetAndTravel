package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.R.id.skipButton
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.viewcontrollers.fragments.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener navigateTo(item)
    }
    var prefs: Preferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = Preferences(this)

        fab.setOnClickListener{
            view -> startActivity(Intent(view.context, LoginActivity::class.java))
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    private fun fragmentFor(item: MenuItem): Fragment{
        when(item.itemId){
            R.id.navigation_home-> {
                return HomeFragment()
            }
            R.id.navigation_myevents-> {
                if(prefs!!.userToken == null || prefs!!.userToken == ""){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else{
                    return EventsFragment()
                }
            }
            R.id.navigation_tickets -> {
                if(prefs!!.userToken == null || prefs!!.userToken == ""){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else{
                    return TicketsFragment()
                }
            }
            R.id.navigation_providers -> {
                if(prefs!!.userToken == null || prefs!!.userToken == ""){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else{
                    return ProvidersFragment()
                }
            }
            R.id.navigation_reservations -> {
                if(prefs!!.userToken == null || prefs!!.userToken == ""){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else{
                    return ReservationFragment()
                }
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

    private fun showView(token: String){

    }
}
