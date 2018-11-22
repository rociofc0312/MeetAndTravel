package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.rocio.meetandtravel.R
import kotlinx.android.synthetic.main.content_tickets.*
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.*
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.network.MeetAndTravelApi.Companion.tag
import org.json.JSONObject
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import kotlinx.android.synthetic.main.layout_next_step.*
import kotlinx.android.synthetic.main.layout_ticket.view.*
import org.json.JSONArray
import java.text.NumberFormat


class CreateTicketsActivity : AppCompatActivity() {

    var event: Event? = null
    private var tickets = JSONArray()
    var prefs: Preferences? = null
    lateinit var idEvent: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tickets)

        setupActionBar()
        prefs = Preferences(this)

        val intent = intent?: return
        idEvent = intent.getStringExtra("id")
//        var bundle :Bundle ?=intent.extras
//        var message = bundle!!.getString("id")
//        event =  Event.from(intent.extras)
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
            createTickets(this)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Crear tickets"
    }

    private fun handleResponse(context: Context, response: NetworkResponse?) {
        //Toast.makeText(this, "Tickets creados satisfactoriamente", Toast.LENGTH_SHORT).show()
        val dialog = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.layout_next_step, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)

        val customDialog = dialog.create()
        customDialog.show()

        customDialog.backButton.setOnClickListener{
            customDialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        customDialog.continueButton.setOnClickListener {
            customDialog.dismiss()
//            startActivity(Intent(this, SelectProvidersActivity::class.java).putExtras(event!!.toBundle()))
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(this, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }

    private fun addTickets(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.layout_ticket, null)

        rowView.quantitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                rowView.quantityTextView.text = getString(R.string.number_of_tickets, i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Add the new row before the add field button.
        parentNewTicketLayout.addView(rowView, parentNewTicketLayout.childCount - 1)
        Log.d(tag, "Childs: " + (parentNewTicketLayout.childCount-1))
    }

    private fun buildTicket(name: String, cost: Float, stock: Int): JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("cost", cost)
        jsonObject.put("stock", stock)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

    private fun createTickets(context: Context){
        val ticketsCount = parentNewTicketLayout.childCount-1

        for(i in 0..ticketsCount-1){
            val row = parentNewTicketLayout.getChildAt(i)
            val nameTicket = row.findViewById(R.id.nameTicketEditText) as EditText
            val priceTicket = row.findViewById(R.id.priceTicketEditText) as EditText
            val stockTickets = row.findViewById(R.id.quantitySeekBar) as SeekBar

            val ticket = buildTicket(nameTicket.text.toString(), NumberFormat.getInstance().parse(priceTicket.text.toString()).toFloat(), stockTickets.progress)
            tickets.put(ticket)
        }
        Log.d(tag, "Tickets to send: ${tickets}")

        MeetAndTravelApi.requestCreateTickets(tickets, prefs!!.userToken!!, idEvent, { response -> handleResponse(context, response) }, { error -> handleError(error)})
    }
}
