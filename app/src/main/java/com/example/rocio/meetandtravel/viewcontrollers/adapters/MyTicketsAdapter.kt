package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Ticket
import kotlinx.android.synthetic.main.item_my_tickets.view.*

class MyTicketsAdapter( var tickets: List<Ticket>, val context: Context): RecyclerView.Adapter<MyTicketsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_tickets, p0, false))
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val ticket = tickets.get(p1)
        holder.updateFrom(ticket)
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val ticketImageView =  view.myTicketImageView
        val dateEventTextView = view.dateEventTextView
        val nameEventTextView = view.nameEventTextView

        fun updateFrom(ticket: Ticket){
            dateEventTextView.text = ticket.ticketType.event!!.startDate
            nameEventTextView.text = ticket.ticketType.event!!.name

        }
    }
}