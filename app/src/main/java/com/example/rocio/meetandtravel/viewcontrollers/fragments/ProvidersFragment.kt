package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.error.ANError

import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.models.Provider
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.AllProvidersAdapter
import kotlinx.android.synthetic.main.fragment_providers.view.*

class ProvidersFragment : Fragment(), AllProvidersAdapter.OnProviderClickListener {

    var event: Event? = null

    override fun onClick(provider: Provider) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var allProvidersRecyclerView: RecyclerView
    private lateinit var allProvidersAdapter: AllProvidersAdapter
    private lateinit var allProvidersLayoutManager: RecyclerView.LayoutManager

    private var providers = ArrayList<Provider>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_providers, container, false)

        event = Event.from(arguments!!)

        allProvidersRecyclerView = view.allProvidersRecyclerView
        allProvidersAdapter = AllProvidersAdapter(this, providers, view.context)
        allProvidersLayoutManager = GridLayoutManager(view.context, 2)

        allProvidersRecyclerView.adapter = allProvidersAdapter
        allProvidersRecyclerView.layoutManager = allProvidersLayoutManager

        MeetAndTravelApi.requestAllProviders(event!!.id.toString(), {response -> handleResponse(response)},{error ->handleError(error)})

        return view
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        providers = response.providers!!
        Log.d(MeetAndTravelApi.tag, "Found ${providers.size} providers")
        allProvidersAdapter.providers = providers
        allProvidersAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        Log.d(tag, anError!!.message)
    }

}
