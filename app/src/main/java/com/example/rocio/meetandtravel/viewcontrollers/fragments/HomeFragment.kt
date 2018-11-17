package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.viewcontrollers.activities.CreateEvent
import android.support.v7.widget.SearchView
import com.example.rocio.meetandtravel.viewcontrollers.adapters.EventsAdapter



class HomeFragment : Fragment(), EventsAdapter.OnEventClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    lateinit var on : EventsAdapter.OnEventClickListener
    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        allEventsAdapter.filter.filter(p0)
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 == null || p0.trim().isEmpty()) {
            resetSearch()
            return false
        }
        return false
    }

    private fun resetSearch() {
        allEventsAdapter.events = events
        allEventsAdapter.notifyDataSetChanged()
        allEventsRecyclerView.setAdapter(allEventsAdapter)
        allEventsRecyclerView.setLayoutManager(allEventsLayoutManager)
    }


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
    private lateinit var allEventsAdapter: EventsAdapter
    private lateinit var allEventsLayoutManager: RecyclerView.LayoutManager

    private var events = ArrayList<Event>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Eventos"
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        allEventsAdapter = EventsAdapter(this, events, view.context)
        allEventsLayoutManager = GridLayoutManager(view.context, 1) as RecyclerView.LayoutManager

        allEventsRecyclerView.adapter = allEventsAdapter
        allEventsRecyclerView.layoutManager = allEventsLayoutManager

        MeetAndTravelApi.requestAllEvents({response -> handleResponse(response)},{error ->handleError(error)})
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setQueryHint("Buscar...")
        super.onCreateOptionsMenu(menu, inflater)

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
