package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_event.*
import kotlinx.android.synthetic.main.content_register.*
import org.json.JSONObject

class CreateEvent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        setupActionBar()
        guardarButton.setOnClickListener{
            MeetAndTravelApi.requestEventRegister(buildEvent(), { response -> handleResponse(response) }, { error -> handleError(error)})
        }
        Log.d("Debug","Evento Creado")
    }

    fun setupActionBar() {
        supportActionBar?.title = "Registrar Evento"
    }

    private fun buildEvent(): JSONObject {
        var url: String = "https://storage.googleapis.com/rociomovilesapp2/semanaEmprendimiento.jpg1541554615846"
        val jsonObject = JSONObject()
        jsonObject.put("name", nombreEditText.text)
        jsonObject.put("description", descripcionEditText.text)
        jsonObject.put("start_date", fechaInicioEditText.text)
        jsonObject.put("end_date",  fechaFinEditText.text)
        jsonObject.put("start_hour", horaEditText.text)
        jsonObject.put("end_hour", horaEditText.text)
        jsonObject.put("location", lugarEditText.text)
        jsonObject.put("organized_by", empresaEditText.text)
        jsonObject.put("file", url)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

    private fun handleResponse(response: NetworkResponse?) {
        Toast.makeText(this, "Evento creado satisfactoriamente", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, CreateEvent::class.java))
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(this, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }

}
