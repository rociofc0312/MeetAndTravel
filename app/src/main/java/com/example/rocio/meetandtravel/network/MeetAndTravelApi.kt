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
        val allProviders = "$baseUrl/events/{event_id}/providers"
        val userLogin = "$baseUrl/users/auth"
        val userRegister = "$baseUrl/users"
        val eventRegister = "$baseUrl/users/1/events/"
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
                    .addHeaders("token", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZmlyc3RuYW1lIjoiUm9jw61vIERhbmllbGEiLCJsYXN0bmFtZSI6IkZlcm7DoW5kZXogQ2FuYWxlcyIsImVtYWlsIjoicm9jaW9mYzAzMTJAZ21haWwuY29tIiwidGVsZXBob25lIjoiOTkxNzkwNjI0IiwiZG5pIjoiNzE5Njk4MjMiLCJiaXJ0aGRhdGUiOiIxOTk3LTAzLTEyIiwiY3JlYXRlZF9hdCI6IjIwMTgtMTEtMDJUMjM6NDI6MjkuMDAwWiIsInVwZGF0ZWRfYXQiOiIyMDE4LTExLTA3VDA2OjU0OjIwLjAwMFoiLCJpYXQiOjE1NDE2MDI5NjksImV4cCI6MTU0MTYzMTc2OX0.mRYeB3Llr9gyhGeupECkSE0_PDb2-PrzkbHT0BE5qc8")
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
        fun requestEventRegister(event: JSONObject, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.post(MeetAndTravelApi.eventRegister)
                    .addJSONObjectBody(event)
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
        fun requestAllProviders(event_id: String, responseHandler: (NetworkResponse?)-> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.get(MeetAndTravelApi.allProviders)
                    .addPathParameter("event_id", event_id)
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