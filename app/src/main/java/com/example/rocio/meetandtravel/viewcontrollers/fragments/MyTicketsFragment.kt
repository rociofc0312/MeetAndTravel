package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.models.Ticket
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.MyTicketsAdapter
import kotlinx.android.synthetic.main.fragment_my_tickets.view.*
import org.json.JSONObject

class MyTicketsFragment : Fragment() {

    private lateinit var myTicketsRecyclerView: RecyclerView
    private lateinit var myTicketsAdapter: MyTicketsAdapter
    private lateinit var myTicketsLayoutManager: RecyclerView.LayoutManager

    private var tickets = ArrayList<Ticket>()
    var prefs: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_tickets, container, false)

        myTicketsRecyclerView = view.myTicketsRecyclerView
        myTicketsAdapter = MyTicketsAdapter(tickets, view.context)
        myTicketsLayoutManager = GridLayoutManager(view.context, 1)

        myTicketsRecyclerView.adapter = myTicketsAdapter
        myTicketsRecyclerView.layoutManager = myTicketsLayoutManager

        prefs = Preferences(view.context)

        Log.d(tag, prefs!!.userToken)

        MeetAndTravelApi.requestMyTickets(prefs!!.userToken!!, { response -> handleResponse(response)},{ error ->handleError(error)})

        return view
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        tickets = response.tickets!!
        Log.d(MeetAndTravelApi.tag, "Found ${tickets.size} providers")
        myTicketsAdapter.tickets = tickets
        myTicketsAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(view!!.context, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }
}
