package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.AllEventsAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.viewcontrollers.activities.CreateEvent

class HomeFragment : Fragment(), AllEventsAdapter.OnEventClickListener {
    var prefs: Preferences? = null
    override fun onClick(event: Event) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        val eventFragment = EventFragment()
        eventFragment.arguments = event.toBundle()
        fragmentTransaction.replace(R.id.mainContent, eventFragment)
        fragmentTransaction.addToBackStack("Home")
        fragmentTransaction.commit()
    }
    private lateinit var allEventsRecyclerView: RecyclerView
    private lateinit var allEventsAdapter: AllEventsAdapter
    private lateinit var allEventsLayoutManager: RecyclerView.LayoutManager

    private var events = ArrayList<Event>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Eventos"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        view.fab.setOnClickListener{
//            view -> startActivity(Intent(view.context, LoginActivity::class.java))
//        }
        view.fab.setOnClickListener {
//            if (prefs!!.userToken == null || prefs!!.userToken == "") {
//                view -> startActivity(Intent(view.context, LoginActivity::class.java))
            view -> startActivity(Intent(view.context, CreateEvent::class.java))
//            } else {

//            }
        }
        allEventsRecyclerView = view.eventsRecyclerView
        allEventsAdapter = AllEventsAdapter(this, events, view.context)
        allEventsLayoutManager = GridLayoutManager(view.context, 1)

        allEventsRecyclerView.adapter = allEventsAdapter
        allEventsRecyclerView.layoutManager = allEventsLayoutManager

        MeetAndTravelApi.requestAllEvents({response -> handleResponse(response)},{error ->handleError(error)})

        return view
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        events = response.events!!
        Log.d(MeetAndTravelApi.tag, "Found ${events.size} sources")
        allEventsAdapter.events = events
        allEventsAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        Log.d(tag, anError!!.message)
    }
}
