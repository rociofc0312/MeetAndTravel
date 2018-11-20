package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import kotlinx.android.synthetic.main.item_my_events.view.*

class MyEventsAdapter(var events: List<Event>, val context: Context): RecyclerView.Adapter<MyEventsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_events, p0, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val event = events.get(p1)
        holder.updateFrom(event)
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val EventImageView =  view.imageView2
        val EventTextView = view.video_title


        fun updateFrom(event: Event){
            EventTextView.text = event.name
        }
    }
}