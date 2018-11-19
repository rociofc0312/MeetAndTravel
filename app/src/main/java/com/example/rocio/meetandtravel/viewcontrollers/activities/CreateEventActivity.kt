package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.TestResponse
import kotlinx.android.synthetic.main.content_event.*

import org.json.JSONObject
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import java.io.File
import android.net.Uri
import android.Manifest
import android.os.Handler
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.network.NetworkResponse
import android.support.v4.os.HandlerCompat.postDelayed
import com.example.rocio.meetandtravel.models.Event


class CreateEventActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private val STORAGE_PERMISSION_CODE = 123
    private lateinit var filePath: Uri
    var prefs: Preferences? = null
    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        setupActionBar()

        prefs = Preferences(this)

        requestStoragePermission()

        guardarButton.setOnClickListener{
            val path = getPath(filePath)
            val file = File(path)
            MeetAndTravelApi.requestEventRegister(prefs!!.userToken!!, prefs!!.userId.toString(), file, buildEvent(), { response -> handleEventResponse(response) }, { error -> handleEventError(error)})
        }

        buttonChoose.setOnClickListener{
            showFileChooser()
        }

        Log.d("Debug","Evento Creado")
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Registrar Evento"
    }

    private fun buildEvent(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name", nameEditText.text)
        jsonObject.put("description", descriptionEditText.text)
        jsonObject.put("start_date", startDateEditText.text)
        jsonObject.put("end_date",  endDateEditText.text)
        jsonObject.put("start_hour", startHourEditText.text)
        jsonObject.put("end_hour", endHourEditText.text)
        jsonObject.put("location", locationEditText.text)
        jsonObject.put("organized_by", companyEditText.text)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

    private fun handleEventResponse(response: NetworkResponse?) {
        Toast.makeText(this, "Evento creado satisfactoriamente", Toast.LENGTH_SHORT).show()
        event = response!!.event
        startCreateTickets()
    }

    private fun handleEventError(anError: ANError?) {
        val jsonError = JSONObject(anError!!.errorBody)
        Log.d(MeetAndTravelApi.tag, jsonError.getString("message"))
        Toast.makeText(this, jsonError.getString("message"), Toast.LENGTH_SHORT).show()
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    public override fun onActivityResult(requestCode: Int, result: Int, data: Intent?) {
        super.onActivityResult(requestCode, result, data)
        filePath = data!!.data
        imageNameEditText.setText(File(getPath(filePath)).name)
    }

    private fun getPath(uri: Uri): String {
        var cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()
        cursor = contentResolver.query(
                android
                    .provider
                    .MediaStore
                    .Images
                    .Media
                    .EXTERNAL_CONTENT_URI, null, MediaStore
                    .Images
                    .Media
                    ._ID + " = ? ", arrayOf(document_id), null)
        cursor!!.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCreateTickets(){
        Handler().postDelayed({
            startActivity(Intent(this, CreateTicketsActivity::class.java).putExtras(event!!.toBundle()))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        },2000)
    }

}
