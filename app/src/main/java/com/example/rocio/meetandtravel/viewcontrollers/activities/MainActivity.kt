package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.viewcontrollers.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener navigateTo(item)
    }
    var prefs: Preferences? = null
    private val INVALID_LOGIN = 28800000

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        if (supportActionBar?.isShowing!!){
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
        prefs = Preferences(this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    private fun fragmentFor(item: MenuItem): Fragment {
        when (item.itemId) {
            R.id.navigation_home -> {
                return HomeFragment()
            }
            R.id.navigation_logistic -> {
                if(validateTime(prefs!!.time)){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return LogisticsFragment()
                }
            }
            R.id.navigation_purchases -> {
                if(validateTime(prefs!!.time)){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return TicketsFragment()
                }
            }
            R.id.navigation_profile -> {
                if(validateTime(prefs!!.time)){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    return MyProvidersFragment()
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

    private fun validateTime(time: Long): Boolean{
        val currentTime = System.currentTimeMillis()
        Log.d(MeetAndTravelApi.tag, "Substract: ${currentTime - time}")
        return currentTime - time > INVALID_LOGIN
    }
}
