package com.example.rocio.meetandtravel.network

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener

class MeetAndTravelApi{
    companion object {
        private val baseUrl = "https://movilesapp-220219.appspot.com/api"
        val allEvents = "$baseUrl/events"
        val tag = "MeetAndTravel"

        fun requestAllEvents(responseHandler: (NetworkResponse?)-> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.get(MeetAndTravelApi.allEvents)
                    .setPriority(Priority.LOW)
                    .setTag(tag)
                    .build()
                    .getAsObject(NetworkResponse::class.java, object : ParsedRequestListener<NetworkResponse>{
                        override fun onResponse(response: NetworkResponse?) {
                            responseHandler(response)
                        }

                        override fun onError(anError: ANError?) {
                            errorHandler(anError)
                        }
                    })
        }
    }
}