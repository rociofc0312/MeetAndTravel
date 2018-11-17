package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.viewcontrollers.fragments.EventFragment
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AllEventsAdapter(private val onEventClickListener: OnEventClickListener, var events: List<Event>, val context: Context):
        RecyclerView.Adapter<AllEventsAdapter.ViewHolder>(), Filterable {
    lateinit var eventFilter : FilterEvents
    var eventsFiltered: List<Event> = events
    override fun getFilter(): Filter {

            eventFilter = FilterEvents()

        return eventFilter
    }

    interface OnEventClickListener {
        fun onClick(event: Event)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, p0, false))
    }

    override fun getItemCount(): Int {
        return eventsFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventsFiltered.get(position)
        holder.updateFrom(event)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val eventImageView = view.eventImageView
        val nameEventTextView = view.nameEventTextView
        val dateEventTextView = view.dateEventTextView
        val locationEventTextView = view.locationEventTextView
        val eventLayout = view.eventLayout

        fun updateFrom(event: Event){
            eventImageView.setDefaultImageResId(R.mipmap.ic_launcher)
            eventImageView.setErrorImageResId(R.mipmap.ic_launcher)
            eventImageView.setImageUrl(event.eventImage)
            nameEventTextView.text = event.name

            dateEventTextView.text = event.startDate
            locationEventTextView.text = event.location
            eventLayout.setOnClickListener {
                onEventClickListener.onClick(event)
            }
        }
    }

    inner class FilterEvents: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = Filter.FilterResults()
            if (constraint != null && constraint.isNotEmpty()) {
                val temp = ArrayList<Event>()
                for (event in events) {
                    if (event.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        temp.add(event)
                        Log.d("Filtrado", event.name)
                    }
                }
                Log.d("Filtro", constraint.toString())

                eventsFiltered = temp
            } else {

                eventsFiltered = events
            }
            filterResults.count = eventsFiltered.size
            filterResults.values = eventsFiltered
            return filterResults

        }

        @SuppressWarnings("unchecked")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            eventsFiltered = results!!.values as List<Event>
            notifyDataSetChanged()
        }

    }
}