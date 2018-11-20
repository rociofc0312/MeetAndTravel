package com.example.rocio.meetandtravel.network

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class MeetAndTravelApi {
    companion object {
        private val baseUrl = "https://movilesapp-220219.appspot.com/api"
        private val allEvents = "$baseUrl/events"
        private val allProviders = "$baseUrl/events/{event_id}/providers"
        private val userLogin = "$baseUrl/users/auth"
        private val userRegister = "$baseUrl/users"
        private val user = "$baseUrl/users/{user_id}"
        private val eventRegister = "$baseUrl/users/{user_id}/events"
        private val myTickets = "$baseUrl/tickets/purchases"
        private val ticketsRegister = "$baseUrl/events/{event_id}/tickets"
        private val myEvents = "$baseUrl/users/{user_id}/events"
        private val myProviders = "${baseUrl}/users/{user_id}/providers"
        private val myProvidersWithEvent = "${baseUrl}/events/{event_id}/providers"
        private val myAccommodations = "${baseUrl}/accommodations/reservations"
        val tag = "MeetAndTravel"

        //PUBLIC

        fun requestAllEvents(responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.get(MeetAndTravelApi.allEvents)
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

        fun requestLogin(user: JSONObject, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.post(MeetAndTravelApi.userLogin)
                    .addJSONObjectBody(user)
                    .setTag(tag)
                    .setPriority(Priority.LOW)
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

        fun requestUserRegister(user: JSONObject, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.post(MeetAndTravelApi.userRegister)
                    .addJSONObjectBody(user)
                    .setTag(tag)
                    .setPriority(Priority.LOW)
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

        fun requestUser(token: String, userId: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.get(MeetAndTravelApi.user)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
                    .addPathParameter("user_id", userId)
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

        fun requestAllProviders(eventId: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.get(MeetAndTravelApi.allProviders)
                    .addPathParameter("event_id", eventId)
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

        //PRIVATE

        fun requestEventRegister(token: String, userId: String, file: File, event: JSONObject, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.upload(MeetAndTravelApi.eventRegister)
                    .addPathParameter("user_id", userId)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
                    .addMultipartFile("file", file)
                    .addMultipartParameter("name", event.getString("name"))
                    .addMultipartParameter("description", event.getString("description"))
                    .addMultipartParameter("start_date", event.getString("start_date"))
                    .addMultipartParameter("end_date", event.getString("end_date"))
                    .addMultipartParameter("start_hour", event.getString("start_hour"))
                    .addMultipartParameter("end_hour", event.getString("end_hour"))
                    .addMultipartParameter("location", event.getString("location"))
                    .addMultipartParameter("organized_by", event.getString("organized_by"))
                    .setPriority(Priority.MEDIUM)
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

        fun requestMyTickets(token: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.get(MeetAndTravelApi.myTickets)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
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

        fun requestMyReservations(token: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.get(MeetAndTravelApi.myAccommodations)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
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

        fun requestMyEvents(token: String, userId: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.get(MeetAndTravelApi.myEvents)
                    .addPathParameter("user_id", userId)
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

        fun requestCreateTickets(tickets: JSONArray, token: String, eventId: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit) {
            AndroidNetworking.post(MeetAndTravelApi.ticketsRegister)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
                    .addPathParameter("event_id", eventId)
                    .addJSONArrayBody(tickets)
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

        fun requestMyProviders(token: String, userId: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.get(MeetAndTravelApi.myProviders)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
                    .addPathParameter("user_id", userId)
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

        fun requestMyProvidersWithMyEvent(myProviders: JSONArray, token: String, eventId: String, responseHandler: (NetworkResponse?) -> Unit, errorHandler: (ANError?) -> Unit){
            AndroidNetworking.post(MeetAndTravelApi.myProvidersWithEvent)
                    .addHeaders("Authorization", String.format("Bearer %s", token))
                    .addPathParameter("event_id", eventId)
                    .addJSONArrayBody(myProviders)
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