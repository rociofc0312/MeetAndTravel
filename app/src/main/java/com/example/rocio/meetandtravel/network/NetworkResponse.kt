package com.example.rocio.meetandtravel.network

import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.models.Provider
import com.example.rocio.meetandtravel.models.User

class NetworkResponse{
    val status: String? = null
    val code: String? = null
    val message: String? = null

    val events: ArrayList<Event>? = ArrayList()
    val providers: ArrayList<Provider>? = ArrayList()

    val user: User? = null
    val event: Event? = null
    val token: String? = null
}