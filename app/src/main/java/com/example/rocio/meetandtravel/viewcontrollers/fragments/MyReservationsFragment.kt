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
import com.example.rocio.meetandtravel.models.Accommodation
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.MyReservationsAdapter
import kotlinx.android.synthetic.main.fragment_my_reservations.view.*
import org.json.JSONObject

class MyReservationsFragment : Fragment() {

    private lateinit var myReservationsRecyclerView: RecyclerView
    private lateinit var myReservationsAdapter: MyReservationsAdapter
    private lateinit var myReservationsLayoutManager: RecyclerView.LayoutManager

    private var reservations = ArrayList<Accommodation>()
    var prefs: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_reservations, container, false)

        myReservationsRecyclerView = view.myReservationsRecyclerView
        myReservationsAdapter = MyReservationsAdapter(reservations, view.context)
        myReservationsLayoutManager = GridLayoutManager(view.context, 1)

        myReservationsRecyclerView.adapter = myReservationsAdapter
        myReservationsRecyclerView.layoutManager = myReservationsLayoutManager

        prefs = Preferences(view.context)

        MeetAndTravelApi.requestMyReservations(prefs!!.userToken!!, { response -> handleResponse(response)},{ error ->handleError(error)})

        return view
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        reservations = response.reservations!!
        Log.d(MeetAndTravelApi.tag, "Found ${reservations.size} providers")
        myReservationsAdapter.accommodations = reservations
        myReservationsAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(view!!.context, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }

}
