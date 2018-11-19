package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.rocio.meetandtravel.R
import kotlinx.android.synthetic.main.content_tickets.*
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.View
import com.example.rocio.meetandtravel.network.MeetAndTravelApi.Companion.tag


class CreateTicketsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tickets)

        setupActionBar()

        addButton.setOnClickListener{
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.layout_ticket, null)
            // Add the new row before the add field button.
            parentNewTicketLayout.addView(rowView, parentNewTicketLayout.getChildCount() - 1);
            Log.d(tag, "Childs: " + (parentNewTicketLayout.getChildCount()-1))
        }
    }
    private fun setupActionBar() {
        supportActionBar?.title = "Crear tickets"
    }

}
