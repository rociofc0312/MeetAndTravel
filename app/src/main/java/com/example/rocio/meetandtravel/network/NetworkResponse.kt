package com.example.rocio.meetandtravel.network

import com.example.rocio.meetandtravel.models.Event

class NetworkResponse{
    val status: String? = null
    val code: String? = null
    val message: String? = null
    val events: ArrayList<Event>? = ArrayList()
}