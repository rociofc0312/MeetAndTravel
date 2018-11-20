package com.example.rocio.meetandtravel.network

import com.example.rocio.meetandtravel.models.*

class NetworkResponse{
    val status: String? = null
    val code: String? = null
    val message: String? = null

    val events: ArrayList<Event>? = ArrayList()
    val providers: ArrayList<Provider>? = ArrayList()
    val tickets: ArrayList<Ticket>? = ArrayList()
    val reservations: ArrayList<Accommodation>? = ArrayList()

    val user: User? = null
    val event: Event? = null
    val token: String? = null
}