package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import kotlinx.android.synthetic.main.item_my_events.view.*
import android.content.Intent
import com.example.rocio.meetandtravel.viewcontrollers.activities.CreateProvidersActivity
import com.example.rocio.meetandtravel.viewcontrollers.activities.CreateTicketsActivity
import com.example.rocio.meetandtravel.viewcontrollers.activities.UpdateEventActivity


class MyEventsAdapter(var events: List<Event>, val context: Context): RecyclerView.Adapter<MyEventsAdapter.ViewHolder>(){
    private var ctx: Context? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        this.ctx = context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_events, p0, false))
    }


    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val events = events.get(p1)
        holder.updateFrom(events)

        holder.buttonViewOption.setOnClickListener {
            val popup = PopupMenu(ctx, holder.buttonViewOption)
            popup.inflate(R.menu.options_menu)
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.menu1 -> {
                            val intent = Intent(ctx, UpdateEventActivity::class.java)
                            intent.putExtra("IdEvent", events.id.toString())
                            ctx?.startActivity(intent)
                        }
                        R.id.menu2 -> {
                            val intent = Intent(ctx, CreateTicketsActivity::class.java)
                            intent.putExtra("IdEvent", events.id.toString())
                            ctx?.startActivity(intent)
                        }
                        R.id.menu3 -> {//Falta Provedores por Evento...
                            val intent = Intent(ctx, CreateProvidersActivity::class.java)
                            intent.putExtra("IdEvent", events.id.toString())
                            ctx?.startActivity(intent)
                        }
                    }
                    return false
                }
            })
            popup.show()
        }

    }



    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val EventImageView =  view.eventImageView
        val EventNameTextView = view.nameTextView
        val EventDescriptionTextView = view.descriptionTextView
        var buttonViewOption = view.textViewOptions

        fun updateFrom(event: Event){

            EventImageView.setImageUrl(event.eventImage)
            EventNameTextView.text = event.name
            EventDescriptionTextView.text = event.description
        }
    }
}