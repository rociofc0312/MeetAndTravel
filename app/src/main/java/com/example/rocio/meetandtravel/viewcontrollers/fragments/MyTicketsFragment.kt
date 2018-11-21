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
    private var newTickets = ArrayList<Ticket>()
    var nameTable: MutableMap<Int, Int> = mutableMapOf()
    var nameTable2: MutableMap<Int, Int> = mutableMapOf()
    var prefs: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_tickets, container, false)

        myTicketsRecyclerView = view.myTicketsRecyclerView
        myTicketsAdapter = MyTicketsAdapter(tickets, view.context)
        myTicketsLayoutManager = GridLayoutManager(view.context, 1) as RecyclerView.LayoutManager

        myTicketsRecyclerView.adapter = myTicketsAdapter
        myTicketsRecyclerView.layoutManager = myTicketsLayoutManager

        prefs = Preferences(view.context)

        Log.d(tag, prefs!!.userToken)

        MeetAndTravelApi.requestMyTickets(prefs!!.userToken!!, { response -> handleResponse(response)},{ error ->handleError(error)})

        return view
    }
    private fun getCantTicketEvent( idEvento:Int) : Int
    {
        var cant = 0
        tickets.forEach {
            if (it.ticketType.event!!.id == idEvento)
            {
                cant = cant++
            }
        }
        return cant
    }
    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        tickets = response.tickets!!
        tickets.forEach{
            var cant:Int

            var eval:Int = it.ticketType.event!!.id

            if (!nameTable.containsKey(eval))
            {
                cant = getCantTicketEvent(eval)
                nameTable.put(eval,cant)
                newTickets.add(it)
                Log.d(MeetAndTravelApi.tag,it.ticketType.event!!.id.toString())
            }
        }
        Log.d(MeetAndTravelApi.tag, "Found ${newTickets.size} Tickets")
        myTicketsAdapter.tickets = newTickets
        myTicketsAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(view!!.context, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }
}
