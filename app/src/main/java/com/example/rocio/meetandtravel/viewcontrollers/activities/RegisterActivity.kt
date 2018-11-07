package com.example.rocio.meetandtravel.viewcontrollers.activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rocio.meetandtravel.R
import kotlinx.android.synthetic.main.content_register.*
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.models.User
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {

    private val user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener{
            MeetAndTravelApi.requestUserRegister(buildUser(), { response -> handleResponse(response) }, { error -> handleError(error)})
        }
    }
    private fun uiNullValidation(){
        if(firstnameEditText.text.toString() == "" || lastnameEdiText.text.toString() == "" || emailEditText.text.toString() == ""
                || phoneEditText.text.toString() == "" || dniEditText.text.toString() == "" || birthdateEditText.text.toString() == ""
                || passwordEditText.text.toString() == "" || passwordConfirmEditText.text.toString() == ""){

        }
    }

    private fun buildUser(): JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("firstname", firstnameEditText.text)
        jsonObject.put("lastname", lastnameEdiText.text)
        jsonObject.put("email", emailEditText.text)
        jsonObject.put("telephone", phoneEditText.text)
        jsonObject.put("dni",  dniEditText.text)
        jsonObject.put("birthdate", birthdateEditText.text)
        jsonObject.put("password", passwordEditText.text)
        jsonObject.put("confirm_password", passwordConfirmEditText.text)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

    private fun handleResponse(response: NetworkResponse?) {
        Toast.makeText(this, "Usuario creado satisfactoriamente", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun handleError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(this, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }
}
