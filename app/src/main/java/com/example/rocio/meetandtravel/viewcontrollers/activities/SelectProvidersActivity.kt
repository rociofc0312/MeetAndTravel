package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.adapters.MyProvidersAdapter
import kotlinx.android.synthetic.main.activity_select_providers.*
import org.json.JSONArray
import org.json.JSONObject

class SelectProvidersActivity : AppCompatActivity() {

    private lateinit var myProvidersRecyclerView: RecyclerView
    private lateinit var myProvidersAdapter: MyProvidersAdapter
    private lateinit var myProvidersLayoutManager: RecyclerView.LayoutManager
    private var providersRelated = JSONArray()
    var prefs: Preferences? = null
    var event: Event? = null

    private var providers = ArrayList<com.example.rocio.meetandtravel.models.Provider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_providers)

        prefs = Preferences(this)

        val intent = intent?: return
        event =  Event.from(intent.extras)

        setRecyclerView()
        requestMyProviders()

        addProvidersButton.setOnClickListener {
            val myProvidersToSend = castArray(MyProvidersAdapter.checkedProviders)
            Log.d(MeetAndTravelApi.tag, "Providers related: ${providersRelated}")
            MeetAndTravelApi.requestMyProvidersWithMyEvent(myProvidersToSend, prefs!!.userToken!!,event!!.id.toString(), { response -> handlePostResponse(response) }, { error -> handlePostError(error)})
        }
    }

    private fun handlePostResponse(response: NetworkResponse?) {
        Toast.makeText(this, "Proveedores relacionados correctamente", Toast.LENGTH_SHORT).show()
        startMainActivity()
    }

    private fun handlePostError(anError: ANError?) {
        Log.d(MeetAndTravelApi.tag, anError!!.message)
    }

    private fun handleGetResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        providers = response.providers!!
        Log.d(MeetAndTravelApi.tag, "Found ${providers.size} providers")
        myProvidersAdapter.providers = providers
        myProvidersAdapter.notifyDataSetChanged()
    }

    private fun handleGetError(anError: ANError?) {
        Log.d(MeetAndTravelApi.tag, anError!!.message)
    }

    private fun setRecyclerView(){
        myProvidersRecyclerView = this.selectProvidersRecyclerView
        myProvidersAdapter = MyProvidersAdapter(providers, this)
        myProvidersLayoutManager = GridLayoutManager(this, 1)

        myProvidersRecyclerView.adapter = myProvidersAdapter
        myProvidersRecyclerView.layoutManager = myProvidersLayoutManager
    }

    private fun requestMyProviders(){
        MeetAndTravelApi.requestMyProviders(prefs!!.userToken!!,prefs!!.userId.toString(), { response -> handleGetResponse(response) }, { error -> handleGetError(error)})
    }

    private fun buildProviderEvent(id: Int): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

    private fun castArray(ids: ArrayList<Int>): JSONArray{
        for (item in ids){
            providersRelated.put(buildProviderEvent(item))
        }
        return  providersRelated
    }

    private fun startMainActivity(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java).putExtras(event!!.toBundle()))
            finish()
        },2000)
    }
}
