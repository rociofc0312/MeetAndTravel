package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Accommodation
import kotlinx.android.synthetic.main.item_my_reservations.view.*

class MyReservationsAdapter( var accommodations: List<Accommodation>, val context: Context): RecyclerView.Adapter<MyReservationsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_reservations, p0, false))
    }

    override fun getItemCount(): Int {
        return accommodations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val ticket = accommodations.get(p1)
        holder.updateFrom(ticket)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ticketImageView =  view.myReservationImageView
        val nameAccommodationTextView = view.nameAccommodationTextView
        val nameProviderTextView = view.nameProviderTextView

        fun updateFrom(accommodation: Accommodation){
            nameAccommodationTextView.text = accommodation.accommodationType.name
            nameProviderTextView.text = accommodation.accommodationType.provider!!.name
        }
    }
}