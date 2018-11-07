package com.example.rocio.meetandtravel.network

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.rocio.meetandtravel.models.User
import org.json.JSONObject

class MeetAndTravelApi{
    companion object {
        private val baseUrl = "https://movilesapp-220219.appspot.com/api"
        val allEvents = "$baseUrl/events"
        val userLogin = "$baseUrl/users/auth"
        val userRegister = "$baseUrl/users"
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

        fun requestLogin(user:  JSONObject, responseHandler: (NetworkResponse?)-> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.post(MeetAndTravelApi.userLogin)
                    .addJSONObjectBody(user)
                    .setTag(tag)
                    .setPriority(Priority.LOW)
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

        fun requestUserRegister(user: JSONObject, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.post(MeetAndTravelApi.userRegister)
                    .addJSONObjectBody(user)
                    .setTag(tag)
                    .setPriority(Priority.LOW)
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