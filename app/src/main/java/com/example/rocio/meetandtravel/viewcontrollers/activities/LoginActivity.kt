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
    private var time: Long = 0

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
        registerButton.setOnClickListener {
            view -> startActivity(Intent(view.context, RegisterActivity::class.java))
        }
    }

    private fun handleResponse(response: NetworkResponse?) {
        user = response!!.user!!
        token = response.token!!
        time = System.currentTimeMillis()
        val prefs = Preferences(this)
        prefs.userToken = token
        prefs.userId = user.id
        prefs.time = time
        Log.d(MeetAndTravelApi.tag, user.toString())
        Log.d(MeetAndTravelApi.tag, "Parsed: Found ${prefs.userToken} token and ${prefs.userId} id and time ${prefs.time}.")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(tag, jsonError.getString("message"))
        Toast.makeText(this, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }


}
