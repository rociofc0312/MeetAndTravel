package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.EventLog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.MyEventsAdapter
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment : Fragment(){
    private lateinit var myEventsRecyclerView: RecyclerView
    private lateinit var myEventsAdapter: MyEventsAdapter
    private lateinit var myEventsLayoutManager: RecyclerView.LayoutManager
    private var events = ArrayList<Event>()
    var prefs: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_events, container, false)
        myEventsRecyclerView = view.myEventsRecyclerView
        prefs = Preferences(view.context)
        myEventsAdapter = MyEventsAdapter(events, view.context)
        myEventsLayoutManager = GridLayoutManager(view.context, 1)

        myEventsRecyclerView.adapter = myEventsAdapter
        myEventsRecyclerView.layoutManager = myEventsLayoutManager

        MeetAndTravelApi.requestMyEvents(prefs!!.userToken!!, prefs!!.userId.toString(), {response -> handleResponse(response)},{error ->handleError(error)})
        return view
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        events = response.events!!
        Log.d(MeetAndTravelApi.tag, "Found ${events.size} Events")
        myEventsAdapter.events = events
        myEventsAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        Log.d(tag, anError!!.message)
    }

}
