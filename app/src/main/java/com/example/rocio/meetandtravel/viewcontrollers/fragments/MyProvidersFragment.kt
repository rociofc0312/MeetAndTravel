package com.example.rocio.meetandtravel.viewcontrollers.fragments

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
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.models.Provider
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.MyProvidersAdapter
import kotlinx.android.synthetic.main.fragment_my_providers.view.*

class MyProvidersFragment : Fragment() {
    private lateinit var myProvidersRecyclerView: RecyclerView
    private lateinit var myProvidersAdapter: MyProvidersAdapter
    private lateinit var myProvidersLayoutManager: RecyclerView.LayoutManager
    private var providers = ArrayList<Provider>()
    var prefs: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_providers, container, false)
        myProvidersRecyclerView = view.myProvidersRecyclerView
        prefs = Preferences(view.context)
        myProvidersAdapter = MyProvidersAdapter(providers, view.context)
        myProvidersLayoutManager = GridLayoutManager(view.context, 1) as RecyclerView.LayoutManager

        myProvidersRecyclerView.adapter = myProvidersAdapter
        myProvidersRecyclerView.layoutManager = myProvidersLayoutManager

        MeetAndTravelApi.requestMyProviders(prefs!!.userToken!!, prefs!!.userId.toString(), {response -> handleResponse(response)},{error ->handleError(error)})
        return view
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        providers = response.providers!!
        Log.d(MeetAndTravelApi.tag, "Found ${providers.size} Providers")
        myProvidersAdapter.providers = providers
        myProvidersAdapter.notifyDataSetChanged()
    }

    private fun handleError(anError: ANError?) {
        Log.d(tag, anError!!.message)
    }
}
