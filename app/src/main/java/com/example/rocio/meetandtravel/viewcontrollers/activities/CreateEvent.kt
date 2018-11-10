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
import com.example.rocio.meetandtravel.network.TestResponse
import com.example.rocio.meetandtravel.viewcontrollers.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_event.*
import kotlinx.android.synthetic.main.content_register.*
import org.json.JSONObject
import android.graphics.Bitmap
import android.widget.EditText
import android.provider.MediaStore
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.annotation.NonNull
import android.widget.Button
import android.widget.ImageView
import java.io.File
import android.net.Uri
import android.Manifest
import java.net.URI

class CreateEvent : AppCompatActivity() {
    //Image request code
    private val PICK_IMAGE_REQUEST = 1

    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123

    //Bitmap to get image from gallery
    private lateinit var bitmap: Bitmap

    //Uri to store the image uri
    private lateinit var filePath: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        setupActionBar()

        guardarButton.setOnClickListener{
            val path = getPath(filePath)
            val file = File(path)
            MeetAndTravelApi.requestEventRegister(file, { response -> handleResponse(response) }, { error -> handleError(error)})
        }
        Log.d("Debug","Evento Creado")

        //Requesting storage permission
        requestStoragePermission()


        //Setting clicklistener
        buttonChoose.setOnClickListener{
            showFileChooser()
        }

    }

    fun setupActionBar() {
        supportActionBar?.title = "Registrar Evento X"
    }

    private fun buildEvent(): JSONObject {

        val path = getPath(filePath)
        //var url: String = "C:\\Users\\Miguel Angel\\Pictures\\fin-de-año-1-750x400.jpg"
        val jsonObject = JSONObject()
        jsonObject.put("name", nombreEditText.text)
        jsonObject.put("description", descripcionEditText.text)
        jsonObject.put("start_date", fechaInicioEditText.text)
        jsonObject.put("end_date",  fechaFinEditText.text)
        jsonObject.put("start_hour", horaEditText.text)
        jsonObject.put("end_hour", horaEditText.text)
        jsonObject.put("location", lugarEditText.text)
        jsonObject.put("organized_by", empresaEditText.text)
        jsonObject.put("file", path)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }

    private fun handleResponse(response: TestResponse?) {
        Toast.makeText(this, "Evento creado satisfactoriamente", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun handleError(anError: ANError?) {
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

    }

    fun getPath(uri: Uri): String {
        var cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()

        cursor = contentResolver.query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
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


}
