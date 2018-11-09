package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.viewcontrollers.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener navigateTo(item)
    }
    var prefs: Preferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefs = Preferences(this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    private fun fragmentFor(item: MenuItem): Fragment {
        when (item.itemId) {
            R.id.navigation_home -> {
                return HomeFragment()
            }
            R.id.navigation_myevents -> {
                if (prefs!!.userToken == null || prefs!!.userToken == "") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return EventsFragment()
                }
            }
            R.id.navigation_tickets -> {
                if (prefs!!.userToken == null || prefs!!.userToken == "") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return TicketsFragment()
                }
            }
            R.id.navigation_profile -> {
                if (prefs!!.userToken == null || prefs!!.userToken == "") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return MyProvidersFragment()
                }
            }
            R.id.navigation_reservations -> {
                if (prefs!!.userToken == null || prefs!!.userToken == "") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return ReservationFragment()
                }
            }
        }
        return HomeFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount >= 1){
                    supportFragmentManager.popBackStack()
                }
            }
        }
        return true
    }

    private fun navigateTo(item: MenuItem): Boolean {
        item.setChecked(true)
        return supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContent, fragmentFor(item))
                .commit() > 0
    }

    private fun showView(token: String) {

    }
}
