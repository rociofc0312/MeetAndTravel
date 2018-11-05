package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.models.User
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.MeetAndTravelApi.Companion.tag
import com.example.rocio.meetandtravel.network.NetworkResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val jsonObject = JSONObject()
    val message = "Debe completar los campos solicitados."
    private lateinit var user: User
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{
            if(emailEditText.text.toString() == "" || passwordEditText.text.toString() == ""){
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            } else{
                jsonObject.put("email", emailEditText.text)
                jsonObject.put("password", passwordEditText.text)
                MeetAndTravelApi.requestLogin(jsonObject, { response -> handleResponse(response) }, { error -> handleError(error)})
            }

        }

        skipButton.setOnClickListener {
            view -> startActivity(Intent(view.context, MainActivity::class.java))
        }
    }

    private fun handleResponse(response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
            return
        }
            user = response.user!!
            token = response.token!!
            val prefs = Preferences(this)
            prefs.userToken = token
            prefs.userId = user.id
            Log.d(MeetAndTravelApi.tag, "Parsed: Found ${prefs.userToken} token and ${prefs.userId} id.")
            startActivity(Intent(this, MainActivity::class.java))
    }

    private fun handleError(anError: ANError?) {
        Log.d(tag, anError!!.message)
        //Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
    }


}
