package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AllEventsAdapter(var events: List<Event>, val context: Context):
        RecyclerView.Adapter<AllEventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, p0, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events.get(position)
        holder.updateFrom(event)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
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
            /*eventLayout.setOnClickListener {
                view ->
                val context = view.context
                context.startActivity(Intent(view.context, EventActivity::class.java).putExtras(event.toBundle()))
            }*/
        }

        private fun toSimpleString(date: String?) : String {
            val format = SimpleDateFormat("yyyy-MM-dd")
            val dateF = format.parse(date)
            return dateF.toString()
        }

    }
}