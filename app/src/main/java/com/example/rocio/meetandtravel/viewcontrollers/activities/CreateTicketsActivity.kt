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
import android.widget.EditText
import android.widget.SeekBar
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.models.Ticket
import com.example.rocio.meetandtravel.network.MeetAndTravelApi.Companion.tag
import org.json.JSONObject
import android.widget.TextView
import com.example.rocio.meetandtravel.network.MeetAndTravelApi


class CreateTicketsActivity : AppCompatActivity() {

    var event: Event? = null
    private var tickets = ArrayList<JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tickets)
        setupActionBar()
        val intent = intent?: return
        event =  Event.from(intent.extras)
        Log.d(tag, "From event: " + event)

        quantitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                quantityTextView.text = getString(R.string.number_of_tickets, i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        addButton.setOnClickListener{
            addTickets()
        }
        createTicketsButton.setOnClickListener {
            val ticketsCount = parentNewTicketLayout.childCount-1

            for(i in 0..ticketsCount-1){
                val row = parentNewTicketLayout.getChildAt(i)
                val nameTicket = row.findViewById(R.id.nameTicketEditText) as EditText
                val priceTicket = row.findViewById(R.id.priceTicketEditText) as EditText
                val stockTickets = row.findViewById(R.id.quantitySeekBar) as SeekBar

                Log.d(tag, "Is: " + nameTicket.text)
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Crear tickets"
    }

    private fun addTickets(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.layout_ticket, null)
        // Add the new row before the add field button.
        parentNewTicketLayout.addView(rowView, parentNewTicketLayout.getChildCount() - 1);
        Log.d(tag, "Childs: " + (parentNewTicketLayout.getChildCount()-1))
    }

    private fun buildUser(name: String, cost: Float, stock: Int): JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("cost", cost)
        jsonObject.put("stock", stock)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

}
