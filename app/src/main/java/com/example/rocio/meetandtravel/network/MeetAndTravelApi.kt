package com.example.rocio.meetandtravel.network

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.rocio.meetandtravel.models.User
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class MeetAndTravelApi{
    companion object {
        private val baseUrl = "https://movilesapp-220219.appspot.com/api"
        val allEvents = "$baseUrl/events"
        val allProviders = "$baseUrl/events/{event_id}/providers"
        val userLogin = "$baseUrl/users/auth"
        val userRegister = "$baseUrl/users"
        val eventRegister = "$baseUrl/users/{user_id}/events"
        val myTickets = "$baseUrl/tickets/purchases"
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
        fun requestEventRegister(file: File, responseHandler: (TestResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.upload(MeetAndTravelApi.eventRegister)
                    .addPathParameter("user_id", "1")
                    .addHeaders("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZmlyc3RuYW1lIjoiUm9jw61vIERhbmllbGEiLCJsYXN0bmFtZSI6IkZlcm7DoW5kZXogQ2FuYWxlcyIsImVtYWlsIjoicm9jaW9mYzAzMTJAZ21haWwuY29tIiwidGVsZXBob25lIjoiOTkxNzkwNjI0IiwiZG5pIjoiNzE5Njk4MjMiLCJiaXJ0aGRhdGUiOiIxOTk3LTAzLTEyIiwiY3JlYXRlZF9hdCI6IjIwMTgtMTEtMDJUMjM6NDI6MjkuMDAwWiIsInVwZGF0ZWRfYXQiOiIyMDE4LTExLTA3VDA2OjU0OjIwLjAwMFoiLCJpYXQiOjE1NDE4MDE5MDQsImV4cCI6MTU0MTgzMDcwNH0.rLvvnc-kaDivAI4a5jbn2PlzFyOmVQ5Ef4267crPLrQ")
                    //.addHeaders("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJtaWd1ZWxsQGdtYWlsLmNvbSIsInRpbWUiOjE1MzgwNDk3ODR9.k1eTWLSTSoNak0uX33I7GBddaoyS5d24ETgVuNYMi1s")
                    .addMultipartFile("file",file)
                    .addMultipartParameter("name", "Evento X")
                    .addMultipartParameter("description", "TEST")
                    .addMultipartParameter("start_date", "2018-11-09")
                    .addMultipartParameter("end_date",  "2018-11-09")
                    .addMultipartParameter("start_hour", "09:30")
                    .addMultipartParameter("end_hour", "09:30")
                    .addMultipartParameter("location", "UPCxD")
                    .addMultipartParameter("organized_by", "backus")
                    .setPriority(Priority.MEDIUM)
                    .setTag(tag)
                    .build()
                    .getAsObject(TestResponse::class.java, object : ParsedRequestListener<TestResponse>{
                        override fun onResponse(response: TestResponse?) {
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

        fun requestMyTickets(token: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.get(MeetAndTravelApi.myTickets)
                    .addHeaders("Authorization", token)
                    .setPriority(Priority.LOW)
                    .setTag(tag)
                    .build()
                    .getAsObject(NetworkResponse::class.java, object : ParsedRequestListener<NetworkResponse> {
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